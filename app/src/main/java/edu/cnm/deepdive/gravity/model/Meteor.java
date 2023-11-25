package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import android.util.Log;
import java.security.SecureRandom;

public class Meteor {

  private Rect meteorBox;
  private final int METEOR_SIZE = 20;
  private SecureRandom rng;
  GameField gameField;
  private double gravity;
  private int xPosition;
  private int yPosition;


  public Meteor(GameField gameField, int positionX, int positionY) {
    this.gameField = gameField;
    xPosition = positionX;
    yPosition = positionY;
    computeMeteor(positionX, positionY);

  }

  private void computeMeteor(int positionX, int positionY) {
    this.meteorBox = new Rect(positionX - METEOR_SIZE / 2, positionY - METEOR_SIZE / 2, positionX + METEOR_SIZE / 2,
        positionY + METEOR_SIZE / 2);
    //Log.d(getClass().getSimpleName(), meteorBox.toString());
  }

  public boolean inside(Rect meteor) {
    return meteorBox.intersect(meteor);
  }


  public void updatePosition(int increment) {
    xPosition -= increment;
    computeMeteor(xPosition, yPosition);
  }

  public int getyPosition() {
    return yPosition;
  }

  public Rect getMeteorBox() {
    return meteorBox;
  }

  public void setyPosition(int yPosition) {
    this.yPosition = yPosition;
    computeMeteor(xPosition, yPosition);
  }
}
