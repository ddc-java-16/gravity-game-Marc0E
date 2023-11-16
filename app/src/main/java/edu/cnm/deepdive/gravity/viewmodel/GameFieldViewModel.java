package edu.cnm.deepdive.gravity.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.gravity.model.GameField;
import edu.cnm.deepdive.gravity.service.PlayingFieldRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

@HiltViewModel
public class GameFieldViewModel extends ViewModel implements DefaultLifecycleObserver {

  private static final String TAG = GameFieldViewModel.class.getSimpleName();
  private final PlayingFieldRepository playingFieldRepository;
  private final MutableLiveData<GameField> gameField;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  @Inject
   GameFieldViewModel(@ApplicationContext Context context, PlayingFieldRepository playingFieldRepository) {
    this.playingFieldRepository = playingFieldRepository;
    this.gameField = new MutableLiveData<>();
    this.throwable = new MutableLiveData<>();
    this.pending = new CompositeDisposable();
    create();
  }

  public void create(){
    playingFieldRepository.create();
    gameField.postValue(playingFieldRepository.getGameField());
  }

  public void run(){
    playingFieldRepository
        .run()
        .subscribe(
            (running) -> {
              Log.d(TAG, "Refresh; Running = " + running);
              gameField.postValue(playingFieldRepository.getGameField());
            },
            this::postThrowable,
            () -> {
              // TODO: 11/16/23 Take appropriate action for the game paused.
            },
            pending
        );
  }

  public void paused(){
    playingFieldRepository.pause();
  }

  public MutableLiveData<GameField> getGameField() {
    return gameField;
  }

  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }

  @Override
  public void onPause(@NotNull LifecycleOwner owner) {
    playingFieldRepository.pause();
    DefaultLifecycleObserver.super.onPause(owner);
  }

  @Override
  public void onStop(@NotNull LifecycleOwner owner) {
    pending.clear();
    DefaultLifecycleObserver.super.onStop(owner);
  }

  private void postThrowable(Throwable throwable){
    Log.e(TAG, throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}
