package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.security.SecureRandom;
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
  private Projectile projectile;
  private List<Meteor> meteors;
  private List<Enemy> enemies;
  private List<Enemy> enemiesDestroyed;
  private List<Meteor> meteorDestroyed;

  public GameField(int x, int y) {
    this.level = 0;
    this.counter = 0;
    ship = new Ship(this, 40, 40);
    enemiesDestroyed = new LinkedList<>();
    meteorDestroyed = new LinkedList<>();
    meteors = new LinkedList<>();
    enemies = new LinkedList<>();
    boundingBox = new Rect(0, 0, y, x);
    rng = new Random();
    addMeteor();
    addEnemies();
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

  public List<Meteor> getMeteors() {
    return meteors;
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

  public Meteor getMeteor() {
    return meteor = meteors.get(meteors.size()-1); // FIXME: 11/20/23 
  }

  public double getSecondsPerTick() {
    return secondsPerTick;
  }

  public void start(int level){
    //level = 1;
    this.level = level;
    computeTiming();
    for (int i = 0; i < 3 * level; i++) {
      addEnemies();
    }
    addShip();
    addMeteor();

  }

  public void shipFire() {
    ship.setVelocity(velocity); // FIXME: 11/9/23 Input from user.
    ship.setAngle(angle);       // FIXME: 11/9/23 Input from user.
    ship.setGravity(gravity);   // FIXME: 11/9/23 This shouldn't be here.
    projectile = ship.fire();
  }

  public void update() {
    if(!meteors.isEmpty()) {
      meteor = meteors.get(meteors.size()-1); // FIXME: 11/20/23 Is there a better way to do it?
      meteor.updatePosition(level, meteors.get(meteors.size() - 1));
    }
    System.out.println(gravity); // FIXME: 11/17/23 Test for gravity changes.
    meteorDestroyed.clear();
    enemiesDestroyed.clear();
    for (ListIterator<Meteor> iterator = meteors.listIterator(); iterator.hasNext(); ) {
      Meteor meteor = iterator.next();
      // TODO: 11/13/23 Meteors update position
      if (ship.intersects(meteor.getMeteorBox())) {
        // TODO: 11/13/23 damage the ship/ change gravity.
        meteorDestroyed.add(meteor);
        iterator.remove();
      }
    }
    if(rng.nextDouble() < BASED_METEOR_PROBABILITY * level){
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
            projectile = null;
            iterator.remove();
            break;
          }
        }
        if(enemies.isEmpty()){
          start(level+1);
        }
      }
    }
  }

  public void addShip(){
    // FIXME: 11/16/23 How to know the X position of the ship, change new Rect().
     ship = new Ship(this, boundingBox.height()/2, boundingBox.right+30);

  }

  public void shipMoveUp() {
    ship.moveUp();
  }

  public void shipMoveDown() {
    ship.moveDown();
  }

  public void shoot(){
    ship.setAngle(45);
    ship.setVelocity(40); // FIXME: 11/18/23 Get velocity from input.
    ship.fire();
  }

  public void addMeteor() {
    boolean intersection;
    Meteor meteor = new Meteor(this,
        boundingBox.right - 1,
        boundingBox.top + rng.nextInt(
            boundingBox.height())); // FIXME: 11/13/23 I think it should be boundingBox.bottom.
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
    Enemy enemy;
    do {
      intersection = false;
//      enemy.setyPosition(
//          rng.nextInt());
      int h = boundingBox.height();
      int w = boundingBox.width();
      enemy = new Enemy(this, boundingBox.top + rng.nextInt(boundingBox.height()),
          boundingBox.left + boundingBox.width()/2 + rng.nextInt((boundingBox.width() / 2)));
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
      // FIXME: 11/16/23 when do I check update level.
    }
  }

  private void updateLevel(int enemiesRemoved) {
    levelEnemiesRemoved += enemiesRemoved;
    if (levelEnemiesRemoved >= ENEMY_LEVEL_MULTIPLIER * level) {
      level++;
      computeTiming();
      levelEnemiesRemoved = 0;
    }
  }

  public boolean isGameOver(){
    return level>0 && ship == null;
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

  public void setGravity(double gravity) {
    this.gravity = gravity;
  }

}
