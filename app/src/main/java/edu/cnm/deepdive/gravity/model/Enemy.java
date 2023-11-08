package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.security.SecureRandom;

public class Enemy {

  private Rect enemy;
  private SecureRandom rng;


  public Enemy() {
    int left = 0, right = 0, top = 0, bottom = 0; // FIXME: 11/8/23 Get random values.
    this.enemy = new Rect(left, top, right, bottom);
  }

  public int position(){
    throw new UnsupportedOperationException(); // FIXME: 11/8/23 Do I need more than Y position?
  }

  public void move(){ // TODO: 10/24/23 Not sure if it'll move or not.

  }
}
