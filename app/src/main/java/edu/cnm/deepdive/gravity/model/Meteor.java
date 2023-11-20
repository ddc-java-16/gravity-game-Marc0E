package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import android.util.Log;
import java.security.SecureRandom;
import java.util.Random;

public class Meteor {
  private Rect meteorBox;
  private final int METEOR_SIZE = 40;
  private SecureRandom rng;
  GameField gameField;
  private double gravity;
  private int xPosition;
  private int yPosition;



  public Meteor(GameField gameField, int positionX, int positionY){
    this.gameField = gameField;
    xPosition = positionX;
    yPosition = positionY;
    computeMeteor(positionX, positionY);

  }

  private void computeMeteor(int positionX, int positionY) {
    this.meteorBox = new Rect(positionX, positionY, positionX + METEOR_SIZE, positionY - METEOR_SIZE);
    Log.d(getClass().getSimpleName(), meteorBox.toString());
  }

  public boolean inside(Rect meteor){
    return meteorBox.intersect(meteor);
  }

  public boolean isInBounds(){
    return xPosition >= 0;
  }

  public void velocity(){

  }

  public void updatePosition(int increment, Meteor meteor){
    this.meteorBox = meteor.getMeteorBox(); // FIXME: 11/20/23 Is there a better way to do it?
    xPosition-= increment;
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
  }
}
