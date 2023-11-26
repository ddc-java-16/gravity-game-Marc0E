package edu.cnm.deepdive.gravity.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.gravity.model.entity.User;
import edu.cnm.deepdive.gravity.service.UserRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

/**
 * Provides access (for a UI controller or data-bound view) to the {@link User} entity instance
 * corresponding to the current signed-in user, a user specified by its unique identifier (primary
 * key value), and the full collection of users who have signed in to the app. This access includes
 * persistence operations on individual {@link User} entity instances.
 */
@HiltViewModel
public class UserViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final UserRepository repository;
  private final MutableLiveData<Long> currentUserId;
  private final LiveData<User> currentUser;
  private final MutableLiveData<Long> userId;
  private final LiveData<User> user;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  @Inject
  UserViewModel(@ApplicationContext Context context, UserRepository repository) {
    this.repository = repository;
    currentUserId = new MutableLiveData<>();
    currentUser = Transformations.switchMap(currentUserId, repository::get);
    userId = new MutableLiveData<>();
    user = Transformations.switchMap(userId, repository::get);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    fetchCurrentUser();
  }

  /**
   * Returns {@link LiveData} containing the {@link User} entity instance for the current signed-in
   * user.
   */
  public LiveData<User> getCurrentUser() {
    return currentUser;
  }

  public void setUserId(long userId) {
    this.userId.setValue(userId);
  }

  /**
   * Returns {@link LiveData} containing the {@link User} entity instance specified in the most
   * recent invocation of {@link #setUserId(long)}. If there are any
   * {@link androidx.lifecycle.Observer observers} of the result returned by this method, the
   * retrieval will be performed automatically on invocation of {@link #setUserId(long)}, and the
   * observers will be invoked (asynchronously) with the result.
   */
  public LiveData<User> getUser() {
    return user;
  }


  public LiveData<List<User>> getUsers() {
    return repository.getAll();
  }

  public void save(User user) {
    repository
        .save(user)
        .subscribe(
            (u) -> {
            },
            this::postThrowable,
            pending
        );
  }

  public void delete(User user) {
    repository
        .delete(user)
        .subscribe(
            () -> {
            },
            this::postThrowable,
            pending
        );
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
  }

  private void fetchCurrentUser() {
    repository
        .getCurrent()
        .subscribe(
            (user) -> currentUserId.postValue(user.getId()),
            this::postThrowable,
            pending
        );
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}
