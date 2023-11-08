package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import java.util.Random;

public class Meteor {
  private final GameField gameField;
  private Rect meteor;
  private Random rng;
  private double gravity;
  private int xPosition;
  private int yPosition;



  public Meteor(GameField gameField, int y) {
    this.yPosition = y;
    this.gameField = gameField;
  }
  public boolean inside(Rect spaceShip){
    return meteor.intersect(spaceShip);

  }

  public boolean isInBounds(){
    return xPosition >= 0;
  }

  public void velocity(){

  }

  public void updatePosition(){
    xPosition++;
  }


}
