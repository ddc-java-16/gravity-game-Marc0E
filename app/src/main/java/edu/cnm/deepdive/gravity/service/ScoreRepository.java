package edu.cnm.deepdive.gravity.service;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.gravity.model.dao.ScoreDao;
import edu.cnm.deepdive.gravity.model.entity.Score;
import edu.cnm.deepdive.gravity.model.pojo.UserScore;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Represents a ScoreRepository class responsible for managing score data
 * within the application.
 * The ScoreRepository serves as a centralized source for handling score-related
 * operations, such as score retrieval, storage.
 * As a singleton, this class promotes the use of a single shared instance, allowing
 * consistent and efficient access to score-related functionalities across various
 * components or modules within the application.
 */
@Singleton
public class ScoreRepository {

  private final ScoreDao dao;

  @Inject
  public ScoreRepository(ScoreDao dao) {
    this.dao = dao;
  }

  public Single<Long> create(Score score) {
    return dao.insert(score)
        .subscribeOn(Schedulers.io());
  }

  public Single<Integer> delete(Score score) {
    return dao.delete(score)
        .subscribeOn(Schedulers.io());
  }

  public LiveData<Score> read(long id) {
    return dao.select(id);
  }

  public LiveData<List<Score>> readAllScoresForUser(long userId) {
    return dao.selectByPlayerId(userId);
  }

  public LiveData<List<UserScore>> readAllUserScores() {
    return dao.selectUserScores();
  }
}
