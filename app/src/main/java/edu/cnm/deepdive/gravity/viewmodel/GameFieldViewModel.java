package edu.cnm.deepdive.gravity.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.gravity.model.GameField;
import edu.cnm.deepdive.gravity.service.PlayingFieldRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation used in conjunction with Hilt, a dependency injection library for Android.
 * Indicates that this class, GameFieldViewModel, is a ViewModel and should be managed
 * by Hilt's dependency injection mechanism.
 * Extends the Android ViewModel class and implements DefaultLifecycleObserver, which
 * allows for observation and handling of the Android lifecycle events.
 * Hilt, upon encountering this annotation, will take charge of creating and managing
 * instances of this ViewModel, simplifying the process of injecting dependencies and
 * ensuring proper lifecycle handling within an Android app.
 */
@HiltViewModel
public class GameFieldViewModel extends ViewModel implements DefaultLifecycleObserver {

  private static final String TAG = GameFieldViewModel.class.getSimpleName();
  private final PlayingFieldRepository playingFieldRepository;
  private final MutableLiveData<Boolean> running;
  private final MutableLiveData<Boolean> inProgress;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private double gravity;
  private double velocity;
  private int angle;

  @Inject
  GameFieldViewModel(@ApplicationContext Context context,
      PlayingFieldRepository playingFieldRepository) {
    this.playingFieldRepository = playingFieldRepository;
    running = new MutableLiveData<>();
    inProgress = new MutableLiveData<>();
    this.throwable = new MutableLiveData<>();
    this.pending = new CompositeDisposable();
    create();
  }

  public void create() {
    playingFieldRepository.create();
  }

  public void run() {
    playingFieldRepository
        .run()
        .subscribe(
            inProgress::postValue,
            this::postThrowable,
            () -> {
              // TODO: 11/16/23 Take appropriate action for the game paused.
            },
            pending
        );
  }

  public void paused() {
    playingFieldRepository.pause();
  }

  public LiveData<GameField> getGameField() {
    return playingFieldRepository.getLiveGameField();
  }

  public LiveData<Boolean> getRunning() {
    return running;
  }

  public LiveData<Boolean> getInProgress() {
    return inProgress;
  }

  public LiveData<Throwable> getThrowable() {
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

  private void postThrowable(Throwable throwable) {
    Log.e(TAG, throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }


  public boolean isGameOver() {
    return playingFieldRepository.isGameOver();
  }

  public void setGravity(double gravity) {
    playingFieldRepository.setGravity(gravity);
  }

  public void setVelocity(double velocity) {
    playingFieldRepository.setVelocity(velocity);
  }

  public void setAngle(int angle) {
    playingFieldRepository.setAngle(angle);
  }

  public void shipMoveUp() {
    playingFieldRepository.shipMoveUp();
  }

  public void shipMoveDown() {
    playingFieldRepository.shipMoveDown();
  }

  public void shoot() {
    playingFieldRepository.shoot();
  }
}
