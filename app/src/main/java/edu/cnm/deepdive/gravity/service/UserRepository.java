package edu.cnm.deepdive.gravity.service;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.gravity.model.dao.UserDao;
import edu.cnm.deepdive.gravity.model.entity.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

  private final UserDao userDao;
  private final GoogleSignInService signInService;

  @Inject
  UserRepository(UserDao userDao, GoogleSignInService signInService) {
    this.userDao = userDao;
    this.signInService = signInService;
  }

  public Single<User> getCurrent() {
    return getOrCreate().subscribeOn(Schedulers.io());
  }

  public LiveData<User> get(long id) {
    return userDao.select(id);
  }

  public LiveData<List<User>> getAll() {
    return userDao.select();
  }

  public Single<User> save(User user) {
    return (
        (user.getId() == 0)
            ? insert(user)
            : update(user)
    )
        .subscribeOn(Schedulers.io());
  }

  public Completable delete(User user) {
    return (
        (user.getId() == 0)
            ? Completable.complete()
            : checkSafeDelete(user)
                .flatMap(userDao::delete)
                .ignoreElement()
    )
        .subscribeOn(Schedulers.io());
  }

  private Single<User> getOrCreate() {
    return signInService
        .refresh()
        .flatMap((account) -> userDao.select(account.getId())
            .switchIfEmpty(
                Single
                    .fromSupplier(() -> {
                      User user = new User();
                      user.setOauthKey(Objects.requireNonNull(account.getId()));
                      user.setDisplayName(Objects.requireNonNull(account.getDisplayName()));
                      user.setUserPhoto(Objects.requireNonNull(account.getPhotoUrl()).toString());
                      return user;
                    })
                    .flatMap(this::insert)
            )
        );
  }

  private Single<User> insert(User user) {
    user.setCreated(Instant.now());
    return userDao
        .insert(user)
        .map((id) -> {
          user.setId(id);
          return user;
        });
  }


  private Single<User> update(User user) {
    return userDao
        .update(user)
        .map((count) -> user);
  }

  private Single<User> checkSafeDelete(User user) {
    return getOrCreate()
        .map((u) -> {
          if (u.equals(user)) {
            throw new IllegalStateException("");
          }
          return u;
        });
  }

}
