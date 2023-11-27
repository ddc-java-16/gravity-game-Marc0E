package edu.cnm.deepdive.gravity.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import edu.cnm.deepdive.gravity.model.entity.Score;
import edu.cnm.deepdive.gravity.model.entity.User;
import edu.cnm.deepdive.gravity.model.pojo.UserScore;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

/**
 * Provides CRUD operations on {@link Score} entity instances. {@code INSERT}
 */
@Dao
public interface ScoreDao {

  @Insert
  Single<Long> insert(Score score);

  @Delete
  Single<Integer> delete(Score score);

  @Query("SELECT * FROM score WHERE score_id = :id")
  LiveData<Score> select(long id);

  @Query("SELECT * FROM score WHERE player_id = :playerId ORDER BY value DESC")
  LiveData<List<Score>> selectByPlayerId(long playerId);

  @Query("SELECT s.player_id, u.display_name, u.display_nick_name, s.value, s.created "
      + "FROM user AS u JOIN score AS s ON u.user_id = s.player_id "
      + "ORDER BY value DESC")
  LiveData<List<UserScore>> selectUserScores();
}
