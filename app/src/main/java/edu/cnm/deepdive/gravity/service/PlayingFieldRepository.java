package edu.cnm.deepdive.gravity.service;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.gravity.model.GameField;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayingFieldRepository {

  private GameField gameField;
  private final Scheduler moveShip;
  private final Scheduler moveMeteors;
  private final Scheduler projectile;
  private final Scheduler refresh;

  private Subject<Boolean> ticker;

  @Inject
  public PlayingFieldRepository(@ApplicationContext Context context) {
    moveShip = Schedulers.single();
    moveMeteors = Schedulers.single();
    projectile = Schedulers.single();
    refresh = Schedulers.single();


  }

  public Observable<Boolean> run(){
    clearTicker();
    if(gameField.getLevel() == 0){
      gameField.start(1);
    } // TODO: 11/16/23 Check to see if the game has ended.
    ticker = BehaviorSubject.createDefault(true);
    return ticker
        .filter(Boolean.TRUE::equals)
        .delay(100, TimeUnit.MILLISECONDS)
        .observeOn(refresh)
        .map(this::tick);
  }


  private boolean tick(boolean running){
    boolean result;
    gameField.update();

    if (gameField.isGameOver()) {
      ticker.onComplete();
      result = false;
    } else {
      ticker.onNext(true);
      result = true;
    }
    return result;
  }

  public void pause(){
    clearTicker();
  }

  public void create() {
    gameField = new GameField();

  }


  public void stop() {

  }

  public void timekeeper() {
    // TODO: 11/1/23 Timer in charge of movement of objects.
  }

  public void updateShip() {
    // TODO: 11/1/23 Add two helper methods to move ship up/down.

  }


  public void updateProjectile() {
    // TODO: 11/1/23 Based on the velocity and angle selected the projectile will move.

  }

  public void gravityChanger() {
    // TODO: 11/1/23 Create at least 3 different type of meteor (each one will have a different gravity)
    //  that won't damage the ship but if the ship crashes with them the gravity the ship is using to shot projectile will change.
    //  These gravityChangers are going to be generated randomly just like meteors.
  }

  public void updateMeteor() {
// TODO: 11/1/23 Based on the width of the screen and size of ship meteor will generate a random number
//  of meteors (from 1 to ((width/ship.size) - ship.size - gravityChanger.count)) moving from right to left leaving space for
//  the ship to move between them.
  }

  public void enemy() {
    // TODO: 11/1/23 Display enemies in different positions of the screen.
  }

  public void enemyIntersection(){

  }

  public void shipIntersection(){

  }

  public GameField getGameField() {
    return gameField;
  }

  private void clearTicker(){
    if (ticker != null && ! ticker.hasComplete()){
      ticker.onComplete();
    }
    ticker = null;
  }


}
