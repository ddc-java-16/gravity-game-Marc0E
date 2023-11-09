package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.security.SecureRandom;
import java.util.Random;

public class Meteor {
  private Rect meteorBox;
  private SecureRandom rng;
  private double gravity;
  private int xPosition;
  private int yPosition;



  public boolean inside(Rect meteor){
    return meteorBox.intersect(meteor);
  }

  public boolean isInBounds(){
    return xPosition >= 0;
  }

  public void velocity(){

  }

  public void updatePosition(){
    xPosition++;
  }

  public int getyPosition() {
    return yPosition;
  }

  public Rect getMeteorBox() {
    return meteorBox;
  }

  public void setyPosition(int yPosition) {
    this.yPosition = yPosition;
  }
}
