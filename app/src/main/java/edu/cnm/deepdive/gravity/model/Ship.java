package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;

/**
 * Represents the player's ship within the game.
 * Manages ship movement, shooting, and interaction with game elements.
 */
public class Ship {

  private static final String TO_STRING_FORMAT = "%1$s[key=%2$s, name=%3$s, fly=%4$s]";
  private static final int SHIP_SIZE = 30;
  private final String key = null;
  private int xPosition;
  private int yPosition;
  private String name;
  private boolean fly;
  private Rect shipBox;
  private GameField gameField;
  private Projectile projectile;
  private double xVelocity;
  private double yVelocity;
  private double velocity;
  private int positionX;
  private int positionY;
  private double angle;
  private double shipPosition;
  private double totalFlyingTime;
  private double flyingTime;
  private double gravity;


  /**
   * Constructs a Ship object at a specified position within the game field.
   * @param gameField The game field in which the ship exists.
   * @param y         The Y-coordinate position of the ship.
   * @param x         The X-coordinate position of the ship.
   */
  public Ship(GameField gameField, int y, int x) {
    // FIXME: 11/9/23 Check if this is correct
    //ship = new Rect(0, gameField.getBoundingBox().top/2,0,0);
    this.gameField = gameField;
    positionX = x;
    positionY = y;
    computeShipBox();
  }

  private void computeShipBox() {
    shipBox = new Rect(positionX - SHIP_SIZE, positionY - SHIP_SIZE / 2, positionX + SHIP_SIZE,
        positionY + SHIP_SIZE / 2);
  }

  /**
   * Moves the ship upwards within the game field. Adjusts the ship's position by moving it in the
   * upward direction.
   */
  public void moveUp() {
    if (positionY - 1 - SHIP_SIZE / 2 > gameField.getBoundingBox().top) {
      positionY -= 10;
      computeShipBox();
    }
  }

  /**
   * Moves the ship downwards within the game field. Adjusts the ship's position by moving it in the
   * downward direction.
   */
  public void moveDown() {
    if ((positionY + 1 - SHIP_SIZE / 2 < gameField.getBoundingBox().bottom)) {
      positionY += 10;
      computeShipBox();
    }
  }


  /**
   * Checks if the current object's bounding box intersects with another rectangle (meteor).
   * @param intersect The Rect object representing the meteor bounding rectangle.
   * @return {@code true} if there is an intersection; {@code false} otherwise.
   */
  public boolean intersects(Rect intersect) {
    return shipBox.intersect(intersect);
  }

  /**
   * Fires a projectile from the ship.
   * @return The fired projectile object.
   */
  public Projectile fire() {
    computeTrajectory(velocity);
    Projectile projectile = new Projectile(positionX, positionY, xVelocity, yVelocity, gravity,
        gameField);
    return projectile;
  }

//  public boolean canFire(Rect project) {
//    return !project.isEmpty();
//  }


  /**
   * Computes the trajectory of the projectile based on the given velocity.
   * @param velocity The velocity of the projectile.
   */
  public void computeTrajectory(double velocity) {
    shipPosition = shipBox.height();
    xVelocity = (velocity * Math.cos(Math.toRadians(angle)));
    yVelocity = -(velocity * Math.sin(Math.toRadians(angle)));
    //gravity = gameField.getMeteor().getGravity();
    totalFlyingTime =
        -yVelocity - (Math.sqrt(Math.pow(yVelocity, 2) - (4 * shipPosition * 0.5 * gravity))
            / gravity);
  }

//  public double getVelocity() {
//    return velocity;
//  }

  /**
   * Sets the velocity value the ship will use for the projectile.
   * @param velocity The velocity value to be set.
   */
  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

//  public double getAngle() {
//    return angle;
//  }

  /**
   * Sets the angle value the ship will use for the projectile.
   * @param angle The angle value to be set.
   */
  public void setAngle(double angle) {
    this.angle = angle;
  }

  /**
   * Sets the gravity value the ship will use for the projectile.
   * @param gravity The gravity value to be set.
   */
  public void setGravity(double gravity) {
    this.gravity = gravity;
  }

//  public int getPositionX() {
//    return positionX;
//  }

  public int getPositionY() {
    return positionY;
  }

  /**
   * Retrieves the bounding box of the ship.
   * @return The Rect object representing the bounding box of the ship.
   */
  public Rect getShipBox() {
    return shipBox;
  }

//  public void setPositionY(int positionY) {
//    this.positionY = positionY;
//    computeShipBox();
//  }
//
//  public void setPositionX(int positionX) {
//    this.positionX = positionX;
//    computeShipBox();
//  }


  @Override
  public boolean equals(@Nullable Object obj) {
    boolean equivalent;
    if (obj == this) {
      equivalent = true;
    } else if (obj instanceof Ship other) {
      //noinspection ConstantValue
      equivalent = Objects.equals(key, other.key) && Objects.equals(name, other.name)
          && (fly == other.fly);
    } else {
      equivalent = false;
    }
    return equivalent;
  }

  @Override
  public int hashCode() {
    return (name == null) ? Objects.hash(key, fly) : Objects.hash(key, name, name);
  }

  @SuppressLint("DefaultLocale")
  @NonNull
  @Override
  public String toString() {
    return String.format(
        TO_STRING_FORMAT, getClass().getSimpleName(), key, name, fly);
  }

}
