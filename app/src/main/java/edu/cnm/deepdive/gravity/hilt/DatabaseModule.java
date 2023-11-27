package edu.cnm.deepdive.gravity.hilt;

import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.cnm.deepdive.gravity.model.dao.ScoreDao;
import edu.cnm.deepdive.gravity.model.dao.UserDao;
import edu.cnm.deepdive.gravity.service.GravityDatabase;
import javax.inject.Singleton;

/**
 * Uses Dagger {@link Provides @Provides}-annotated methods to satisfy dependencies on concrete
 * implementations of {@link GravityDatabase} and {@link UserDao}.
 */
@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {

  public DatabaseModule() {
  }

  @Provides
  @Singleton
  GravityDatabase provideLocalDatabase(@ApplicationContext Context context) {
    return Room
        .databaseBuilder(context, GravityDatabase.class, GravityDatabase.NAME)
        .addCallback(new GravityDatabase.Callback())
        .build();
  }

  @Provides
  UserDao provideUserDao(GravityDatabase database) {
    return database.getUserDao();
  }

  @Provides
  @Singleton
  ScoreDao provideScoreDao(GravityDatabase database) {
    return database.getScoreDao();
  }

}
