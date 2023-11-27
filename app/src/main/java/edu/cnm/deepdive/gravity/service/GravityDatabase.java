package edu.cnm.deepdive.gravity.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.gravity.model.dao.ScoreDao;
import edu.cnm.deepdive.gravity.model.dao.UserDao;
import edu.cnm.deepdive.gravity.model.entity.Score;
import edu.cnm.deepdive.gravity.model.entity.User;
import edu.cnm.deepdive.gravity.model.pojo.UserScore;
import edu.cnm.deepdive.gravity.service.GravityDatabase.Converters;
import java.time.Instant;

/**
 * Defines a connection to a local Room/SQLite database, All database reads/writes are performed
 * using data-access object (DAO) instances obtained from the singleton instance of this class.
 */

@Database(
    entities = {User.class, Score.class},
    views = {UserScore.class},
    version = 1
)

@TypeConverters(Converters.class)

public abstract class GravityDatabase extends RoomDatabase {

  public static final String NAME = "gravity_scores";


  public abstract UserDao getUserDao();

  public abstract ScoreDao getScoreDao();

  public static class Converters {

    @TypeConverter
    @Nullable
    public static Long toLong(@Nullable Instant value) {
      return (value != null) ? value.toEpochMilli() : null;
    }

    @TypeConverter
    @Nullable
    public static Instant toInstant(@Nullable Long value) {
      return (value != null) ? Instant.ofEpochMilli(value) : null;
    }
  }

  public static class Callback extends RoomDatabase.Callback {

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
    }
  }
}
