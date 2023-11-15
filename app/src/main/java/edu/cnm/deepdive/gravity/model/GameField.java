package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GameField {

  private Rect boundingBox;
  private Ship ship;
  private Meteor meteor;
  private Enemy enemy;
  private SecureRandom rng;
  private int level;
  private int counter;
  private double velocity;
  private double gravity;
  private int angle;
  private Projectile projectile;
  private List<Meteor> meteors;
  private List<Enemy> enemies;
  private List<Enemy> enemiesDestroyed;
  private List<Meteor> meteorDestroyed;

  public GameField() {
    this.level = 1;
    this.counter = 0;
    enemiesDestroyed = new LinkedList<>();
    meteorDestroyed = new LinkedList<>();
    meteors = new LinkedList<>();
    enemies = new LinkedList<>();
    //score
  }

  public int changeGravity() {
    // TODO: 10/24/23 If ship and meteor have the same position than gravity will change
    throw new UnsupportedOperationException();
  }

  public Ship getShip() {
    return ship;
  }

  public Rect getBoundingBox() {
    return boundingBox;
  }

  public int getLevel() {
    return level;
  }

  public int getCounter() {
    return counter;
  }

  public double getGravity() {
    return gravity;
  }

  public void shipMoveUp() {
    ship.moveUp();
  }

  public void shipMoveDown() {
    ship.moveDown();
  }

  public void shipFire() {
    ship.setVelocity(velocity); // FIXME: 11/9/23 Input from user.
    ship.setAngle(angle);       // FIXME: 11/9/23 Input from user.
    ship.setGravity(gravity);   // FIXME: 11/9/23 This shouldn't be here.
    projectile = ship.fire();
  }

  public void update() {
    meteorDestroyed.clear();
    enemiesDestroyed.clear();
    if (projectile != null) {
      projectile.updatePosition();

      if (projectile.getPositionY() >= boundingBox.bottom
          || projectile.getPositionX() >= boundingBox.right) {
        projectile = null;// TODO: 11/13/23 Remove the projectile.
      } else {
        for (ListIterator<Enemy> iterator = enemies.listIterator(); iterator.hasNext(); ) {
          Enemy enemy = iterator.next();
          if (projectile.intersects(enemy.getEnemyBox())) {
            enemiesDestroyed.add(enemy);
            iterator.remove();
          }
        }
      }
    }
    for (ListIterator<Meteor> iterator = meteors.listIterator(); iterator.hasNext();) {
      Meteor meteor = iterator.next();
      // TODO: 11/13/23 Meteors update position
      if (ship.intersects(meteor.getMeteorBox())) {
        // TODO: 11/13/23 damage the ship/ change gravity.
        meteorDestroyed.add(meteor);
        iterator.remove();
      }
    }
  }

  public void addMeteor() {
    boolean intersection;
    Meteor meteor = new Meteor(this,
        boundingBox.right - 1,
        boundingBox.top + rng.nextInt(boundingBox.height())); // FIXME: 11/13/23 I think it should be boundingBox.bottom.
    do {
      intersection = false;
//      meteor.setyPosition(
//          rng.nextInt());
      // FIXME: 11/9/23 How many meteors I'll create on each level?

      for (Meteor m : meteors) {
        if (m.inside(meteor.getMeteorBox())) {
          intersection = true;
          meteor.setyPosition(boundingBox.top + rng.nextInt(boundingBox.height()));
          break;
        }
      }
    } while (intersection);
    meteors.add(meteor);
  }

  public void addEnemies() {
    boolean intersection;
    do {
      intersection = false;
//      enemy.setyPosition(
//          rng.nextInt());
// FIXME: 11/9/23 How many enemies I'll create on each level?, max rng number?
          Enemy enemy = new Enemy(this, rng.nextInt(boundingBox.height() + 1), rng.nextInt((boundingBox.width()/2) + 1));
      for (Enemy nmy : enemies) {
        if (nmy.inside(enemy.getEnemyBox())) {
          intersection = true;
          break;
        }
      }
    } while (intersection);
    enemies.add(enemy);
  }

  public void countObjectDestroyed() {
    // FIXME: 11/9/23 Do I need a loop to check all possible enemies?
    if (projectile.detonate(enemy.position())) {
      counter++;
    }
  }


  public void obstacle() {
    // TODO: 10/24/23 Field will display some obstacles to make more difficult to hit the enemies.
    //  I dont know if this should be its own class.
    // TODO: 10/25/23 Im gonna need fields to keep track of the dimension. For meteor im gonna
    //  need 2 constructors one that's gonna do meteor(field field),
    //  meteor (field field, int x, int y), every tick im gonna need to refresh everything.
    //  Create vector class for position and velocity.
  }

  private void initialize(int level) {
    // TODO: 11/13/23 create a new ship, create repopulate the list of enemies with the number of enemies depending on level.
    // TODO: 11/13/23 clear the list of meteors,
  }
}
