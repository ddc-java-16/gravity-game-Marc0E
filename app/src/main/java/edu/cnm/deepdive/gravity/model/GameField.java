package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.util.List;

public class GameField {
  private Rect gameField;
  private final Ship ship;
  private Meteor meteor;
  private Enemy enemy;
  private int level;
  private final Projectile projectile;
  private final List<Meteor> meteors;
  private final List<Enemy> enemies;

  public GameField(Ship ship, Projectile projectile, List<Meteor> meteors, List<Enemy> enemies, Meteor meteor, Enemy enemy) {
    this.ship = ship;
    this.projectile = projectile;
    this.meteors = meteors;
    this.enemies = enemies;
    this.meteor = meteor;
    this.enemy = enemy;
  }

  public int changeGravity(){
    // TODO: 10/24/23 If ship and meteor have the same position than gravity will change
    throw new UnsupportedOperationException();
  }

  public Ship getShip() {
    return ship;
  }

  public Projectile getProjectile() {
    return projectile;
  }

  public List<Meteor> getMeteors() {
    return meteors;
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

  public Meteor getMeteor() {
    return meteor;
  }

  public Rect getGameField() {
    return gameField;
  }

  public int getLevel() {
    return level;
  }

  public void setGameField(){

  }

  public void obstacle(){
    // TODO: 10/24/23 Field will display some obstacles to make more difficult to hit the enemies.
    //  I dont know if this should be its own class.
    // TODO: 10/25/23 Im gonna need fields to keep track of the dimension. For meteor im gonna
    //  need 2 constructors one that's gonna do meteor(field field),
    //  meteor (field field, int x, int y), every tick im gonna need to refresh everything.
    //  Create vector class for position and velocity.
  }

  public void countObjectDestroyed(){

  }
}
