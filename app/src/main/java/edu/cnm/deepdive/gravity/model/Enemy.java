package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.security.SecureRandom;

public class Enemy {

  private Rect enemyBox;
  private int yPosition;
  private SecureRandom rng;


  public Enemy() {
    int left = 0, right = 0, top = 0, bottom = 0; // FIXME: 11/8/23 Replace top with yPosition.
    this.enemyBox = new Rect(left, top, right, bottom);
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
