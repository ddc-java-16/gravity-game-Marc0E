package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GameField {

  private static final int ENEMY_LEVEL_MULTIPLIER = 3;
  private static final double TIMING_OFFSET = 0.8;
  private static final double TIMING_LEVEL_MULTIPLIER = 0.007;
  private static final double BASED_METEOR_PROBABILITY = 0.01;

  private double secondsPerTick;
  private Rect boundingBox;
  private Ship ship;
  private Meteor meteor;
  private Enemy enemy;
  private Random rng;
  private int level;
  private int counter;
  private double velocity;
  private double gravity;
  private int angle;
  private int levelEnemiesRemoved;
  private int collision;
  private int meteorOut;
  private Projectile projectile;
  private List<Meteor> meteors;
  private List<Enemy> enemies;
  private List<Enemy> enemiesDestroyed;
  private List<Meteor> meteorDestroyed;

  public GameField(int x, int y) {
    this.level = 0;
    this.counter = 0;
    boundingBox = new Rect(0, 0, x, y);
    ship = new Ship(this, 150, 80);
    enemiesDestroyed = new LinkedList<>();
    meteorDestroyed = new LinkedList<>();
    meteors = new LinkedList<>();
    enemies = new LinkedList<>();
    rng = new Random();
  }



  public Ship getShip() {
    return ship;
  }

  public int getCollision() {
    return collision;
  }

  public Projectile getProjectile() {
    return projectile;
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

  public void setGravity(double gravity) {
    this.gravity = gravity;
  }

  public List<Meteor> getMeteors() {
    return meteors;
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

  public Meteor getMeteor() {
    return meteor = meteors.get(meteors.size() - 1); // FIXME: 11/20/23
  }

  public double getSecondsPerTick() {
    return secondsPerTick;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public void setAngle(int angle) {
    this.angle = angle;
  }

  public void start(int level) {
    //level = 1;
    this.level = level;
    computeTiming();
    // FIXME: 11/22/23 If I want more enemies multiply by level.
    for (int i = 0; i < 3; i++) {
      addEnemies();
    }
    addMeteor();

  }

//  public void shipFire() {
//    ship.setVelocity(velocity); // FIXME: 11/9/23 Input from user.
//    ship.setAngle(angle);       // FIXME: 11/9/23 Input from user.
//    ship.setGravity(gravity);   // FIXME: 11/9/23 This shouldn't be here.
//    projectile = ship.fire();
//  }

  public void update() {
    for (Meteor meteor : meteors) {
     meteor.updatePosition(level);
    }
    //System.out.println(gravity); // FIXME: 11/17/23 Test for gravity changes.
    meteorDestroyed.clear();
    enemiesDestroyed.clear();
    for (ListIterator<Meteor> iterator = meteors.listIterator(); iterator.hasNext(); ) {
      Meteor meteor = iterator.next();
      // TODO: 11/13/23 Meteors update position
      if (ship.intersects(meteor.getMeteorBox())) {
        // TODO: 11/13/23 damage the ship/ change gravity.
        collision++;
        ship = null;
        meteorDestroyed.add(meteor);
        iterator.remove();
        break;
      }
    }
    if (rng.nextDouble() < BASED_METEOR_PROBABILITY * level) {
      addMeteor();
    }
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
            counter++;
            projectile = null;
            updateLevel();
            iterator.remove();
            break;
          }
        }
        if (enemies.isEmpty()) {
          for (int i = 0; i < 3; i++) {
            addEnemies();
          }
        }
      }
    }
  }


  public void shipMoveUp() {
    ship.moveUp();
  }

  public void shipMoveDown() {
    ship.moveDown();
  }

  public void shoot() {
    ship.setGravity(this.gravity);
    ship.setAngle(this.angle);
    ship.setVelocity(this.velocity);
    ship.fire();
    projectile = ship.fire();
  }

  public void addMeteor() {
    boolean intersection;
    Meteor meteor = new Meteor(this,
        boundingBox.right - 20,
        boundingBox.top + rng.nextInt(
            boundingBox.height())); // FIXME: 11/13/23 I think it should be boundingBox.bottom.

    do {
      intersection = false;
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
    Enemy enemy;
    do {
      intersection = false;
//      enemy.setyPosition(
//          rng.nextInt());
      enemy = new Enemy(this, boundingBox.top + rng.nextInt(boundingBox.height()),
          boundingBox.left + boundingBox.width() / 2 + rng.nextInt((boundingBox.width() / 2)));
      for (Enemy nmy : enemies) {
        if (nmy.inside(enemy.getEnemyBox())) {
          intersection = true;
          break;
        }
      }
    } while (intersection);
    enemies.add(enemy);
  }

//  public void countObjectDestroyed() {
//    // FIXME: 11/9/23 Do I need a loop to check all possible enemies?
//    if (projectile.detonate(enemy.position())) {
//      counter++;
//      // FIXME: 11/16/23 when do I check update level.
//    }
//  }

  private void updateLevel() {
    levelEnemiesRemoved += 1;
    if (levelEnemiesRemoved >= (ENEMY_LEVEL_MULTIPLIER * level)) {
      level++;
      computeTiming();
      levelEnemiesRemoved = 0;
    }
  }

  public boolean isGameOver() {
    return level > 0 && ship == null;
  }

  // FIXME: 11/16/23
  public boolean projectileOutOfBounds() {
    return true;
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

  private void computeTiming() {
    secondsPerTick = Math.pow(TIMING_OFFSET - (level - 1) * TIMING_LEVEL_MULTIPLIER, level - 1);
  }

}
