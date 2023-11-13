package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.security.SecureRandom;

public class Enemy {

  private Rect enemyBox;
  private static final int ENEMY_SIZE = 40;
  private GameField gameField;
  private int yPosition;
  private SecureRandom rng;


  public Enemy(GameField gameField, int positionY, int positionX) {
    this.gameField = gameField;
    // FIXME: 11/13/23 Check logic.
    this.enemyBox = new Rect(positionX, positionY, positionX+ENEMY_SIZE, positionY - ENEMY_SIZE);
  }

  public boolean inside(Rect enemy){
    return enemyBox.intersect(enemy);
  }
  public Rect position(){
    throw new UnsupportedOperationException(); // FIXME: 11/8/23 Do I need more than Y position?
  }

  public void move(){ // TODO: 10/24/23 Not sure if it'll move or not.
  }

  public Rect getEnemyBox() {
    return enemyBox;
  }

  public void setyPosition(int yPosition) {
    this.yPosition = yPosition;
  }

}
