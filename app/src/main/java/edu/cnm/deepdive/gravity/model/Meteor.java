package edu.cnm.deepdive.gravity.model;

import android.graphics.Rect;
import android.util.Log;
import java.security.SecureRandom;

/**
 * Represents a meteor object within the game field.
 * Meteors are elements that pose a threat to the player's ship.
 */
public class Meteor {

  private Rect meteorBox;
  private final int METEOR_SIZE = 20;
  private SecureRandom rng;
  GameField gameField;
  private double gravity;
  private int xPosition;
  private int yPosition;


  /**
   * Constructs a meteor object within the game field.
   * @param gameField  The game field in which the meteor exists.
   * @param positionX  The X-coordinate position of the meteor.
   * @param positionY  The Y-coordinate position of the meteor.
   */
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

  /**
   *
   * @param meteor Implementation details to check if the provided rectangle overlaps with this meteor's rectangle.
   * @return Return true if there is an overlap, false otherwise.
   */
  public boolean inside(Rect meteor) {
    return meteorBox.intersect(meteor);
  }

  /**
   * Updates the position of the object within the game field by the given increment.
   * @param increment The amount by which to increment the object's position.
   */
  public void updatePosition(int increment) {
    xPosition -= increment;
    computeMeteor(xPosition, yPosition);
  }

  public int getyPosition() {
    return yPosition;
  }

  /**
   * Retrieves the bounding box of the meteor.
   * @return The Rect object representing the bounding box of the meteor.
   */
  public Rect getMeteorBox() {
    return meteorBox;
  }

  /**
   * Sets the Y-coordinate position of the meteor within the game field.
   * @param yPosition The Y-coordinate position to set for the meteor.
   */
  public void setyPosition(int yPosition) {
    this.yPosition = yPosition;
    computeMeteor(xPosition, yPosition);
  }
}
