package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Represents the game field where gameplay occurs. Manages game elements such as the ship, enemies,
 * and meteors.
 */
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

  /**
   * Constructs a GameField object with the specified dimensions.
   *
   * @param x The width of the game field.
   * @param y The height of the game field.
   */
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


  /**
   * Retrieves the ship object within the game field.
   *
   * @return The Ship object representing the player's ship.
   */
  public Ship getShip() {
    return ship;
  }

//  public int getCollision() {
//    return collision;
//  }

  /**
   * Retrieves the projectile object within the game field.
   *
   * @return The Projectile object.
   */
  public Projectile getProjectile() {
    return projectile;
  }

  /**
   * Retrieves the bounding box of the object.
   *
   * @return The Rect object representing the bounding box of the game field.
   */
  public Rect getBoundingBox() {
    return boundingBox;
  }

  /**
   * Retrieves the level.
   *
   * @return int number of the currently level.
   */
  public int getLevel() {
    return level;
  }


  /**
   * Retrieves the counter of enemies destroyed.
   *
   * @return int number of enemies destroyed.
   */
  public int getCounter() {
    return counter;
  }

  /**
   * Retrieves the gravity selected by user in the UI.
   *
   * @return representation of gravity in the format of double.
   */
  public double getGravity() {
    return gravity;
  }

  /**
   * Sets the gravitational force affecting game elements.
   *
   * @param gravity The value representing the gravitational force.
   */
  public void setGravity(double gravity) {
    this.gravity = gravity;
  }

  /**
   * Retrieves the list of meteors present within the game field.
   *
   * @return A List of Meteor objects representing the meteors in the game field.
   */
  public List<Meteor> getMeteors() {
    return meteors;
  }

  /**
   * Retrieves the list of enemies present within the game field.
   *
   * @return A List of Enemy objects representing the enemies in the game field.
   */
  public List<Enemy> getEnemies() {
    return enemies;
  }

//  public Meteor getMeteor() {
//    return meteor = meteors.get(meteors.size() - 1);
//  }
//
//  public double getSecondsPerTick() {
//    return secondsPerTick;
//  }

  /**
   * Sets the velocity of the projectile based on user selection.
   *
   * @param velocity The value representing the velocity of the projectile.
   */
  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  /**
   * Sets the angle of the projectile based on user selection.
   *
   * @param angle The value representing the angle of the projectile.
   */
  public void setAngle(int angle) {
    this.angle = angle;
  }

  /**
   * Starts a game session at the specified level.
   *
   * @param level The level at which to start the game session.
   */
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


  /**
   * Updates game elements, handles collisions, and manages game state. - Updates the meteor list
   * and enemy list. - Checks if the enemy list is empty and adds more enemies to the game field. -
   * Checks if the projectile intersects an enemy, removing the enemy from the list and the game
   * field. - Checks if any meteor intersects with the ship, leading to the ship's destruction and
   * game over.
   */
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
    if (rng.nextDouble() < BASED_METEOR_PROBABILITY * level / 2) {
      addMeteor();
    }
    if (projectile != null) {
      projectile.updatePosition();

      if (projectile.getPositionY() >= boundingBox.bottom
          || projectile.getPositionX() >= boundingBox.right) {
        projectile = null;
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


  /**
   * Moves the ship upwards. Invokes the ship's movement method to move upward within the game
   * field.
   */
  public void shipMoveUp() {
    ship.moveUp();
  }

  /**
   * Moves the ship downwards. Invokes the ship's movement method to move downward within the game
   * field.
   */
  public void shipMoveDown() {
    ship.moveDown();
  }

  /**
   * Initiates the shooting action of the ship. - Sets the gravity, angle, and velocity of the
   * projectile fired by the ship. - Fires the projectile from the ship.
   */
  public void shoot() {
    ship.setGravity(this.gravity);
    ship.setAngle(this.angle);
    ship.setVelocity(this.velocity);
    ship.fire();
    projectile = ship.fire();
  }

  /**
   * Adds a meteor to the game field while preventing intersection with existing meteors.
   */
  public void addMeteor() {
    boolean intersection;
    Meteor meteor = new Meteor(this,
        boundingBox.right - 20,
        boundingBox.top + rng.nextInt(
            boundingBox.height()));

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

  /**
   * Adds an enemy to the game field while preventing intersection with existing enemies.
   */
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


  /**
   * Updates the game level, enemy count, and object velocities based on gameplay progression.
   * Increases the level when the number of enemies destroyed reaches 3 times the current level.
   * Adjusts object velocities depending on the updated level.
   */
  private void updateLevel() {
    levelEnemiesRemoved += 1;
    if (levelEnemiesRemoved >= (ENEMY_LEVEL_MULTIPLIER * level)) {
      level++;
      computeTiming();
      levelEnemiesRemoved = 0;
    }
  }


  /**
   * Checks if the game is over based on specific conditions.
   *
   * @return {@code true} if the game is over; {@code false} otherwise. The game is considered over
   * when the ship object is null and the level is greater than zero.
   */
  public boolean isGameOver() {
    return level > 0 && ship == null;
  }

//  // FIXME: 11/16/23
//  public boolean projectileOutOfBounds() {
//    return true;
//  }

//  public void obstacle() {
//    // TODO: 10/24/23 Field will display some obstacles to make more difficult to hit the enemies.
//    //  I dont know if this should be its own class.
//    // TODO: 10/25/23 Im gonna need fields to keep track of the dimension. For meteor im gonna
//    //  need 2 constructors one that's gonna do meteor(field field),
//    //  meteor (field field, int x, int y), every tick im gonna need to refresh everything.
//    //  Create vector class for position and velocity.
//  }

//  private void initialize(int level) {
//    // TODO: 11/13/23 create a new ship, create repopulate the list of enemies with the number of enemies depending on level.
//    // TODO: 11/13/23 clear the list of meteors,
//  }

  private void computeTiming() {
    secondsPerTick = Math.pow(TIMING_OFFSET - (level - 1) * TIMING_LEVEL_MULTIPLIER, level - 1);
  }

}
