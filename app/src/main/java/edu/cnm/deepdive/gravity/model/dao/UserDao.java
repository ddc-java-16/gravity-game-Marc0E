package edu.cnm.deepdive.gravity.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.gravity.model.pojo.UserScore;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import edu.cnm.deepdive.gravity.model.entity.User;
import java.util.Collection;
import java.util.List;


@Dao
public interface UserDao {

  @Insert
  Single<Long> insert(User user);

  @Insert
  Single<List<Long>> insert(User... user);

  @Insert
  Single<List<Long>> insert(Collection<User> users);

  @Update
  Single<Integer> update(User users);

  @Update
  Single<Integer> update(User... users);

  @Update
  Single<Integer> update(Collection<User> users);

  @Delete
  Single<Integer> delete(User users);

  @Delete
  Single<Integer> delete(User... users);

  @Delete
  Single<Integer> delete(Collection<User> users);

  @Query("SELECT * FROM user WHERE user_id = :id")
  LiveData<User> select(long id);

  @Query("SELECT * FROM user WHERE oauth_key = :oauthKey")
  Maybe<User> select(String oauthKey);

  @Query("SELECT * FROM user ORDER BY display_name ASC")
  LiveData<List<User>> select();


}
