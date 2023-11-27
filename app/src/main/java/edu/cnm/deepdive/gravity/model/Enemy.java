package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.security.SecureRandom;

/**
 * Represents an enemy object within the game field.
 */
public class Enemy {

  private static final int ENEMY_SIZE = 40;
  private Rect enemyBox;
  private GameField gameField;
  private int yPosition;
  private SecureRandom rng;

  /**
   * Constructs an enemy object within the game field.
   *
   * @param gameField The game field in which the enemy exists.
   * @param positionY The Y-coordinate position of the enemy.
   * @param positionX The X-coordinate position of the enemy.
   */
  public Enemy(GameField gameField, int positionY, int positionX) {
    this.gameField = gameField;
    this.enemyBox = new Rect(positionX - ENEMY_SIZE / 2, positionY - ENEMY_SIZE / 2,
        positionX + ENEMY_SIZE / 2, positionY + ENEMY_SIZE);
  }

  /**
   * @param enemy Implementation details to check if the provided rectangle overlaps with this
   *              enemy's rectangle.
   * @return Return true if there is an overlap, false otherwise.
   */
  public boolean inside(Rect enemy) {
    return enemyBox.intersect(enemy);
  }

//  public Rect position() {
//    throw new UnsupportedOperationException(); // FIXME: 11/8/23 Do I need more than Y position?
//  }

//  public void move() { // TODO: 10/24/23 Not sure if it'll move or not.
//  }

  /**
   * @return The bounding box (Rect) defining the position and size of this enemy within the game
   * field.
   */
  public Rect getEnemyBox() {
    return enemyBox;
  }

//  public void setyPosition(int yPosition) {
//    this.yPosition = yPosition;
//  }

}
