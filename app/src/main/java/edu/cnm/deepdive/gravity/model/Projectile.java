package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import java.util.Objects;

/**
 * Represents a projectile within the game.
 * Projectiles are objects fired from the player's ship to interact with game elements.
 */
public class Projectile {

  private static final int PROJECTILE_SIZE = 20;
  private GameField gameField;
  private Rect projectileBox;
  private double flyingTime;
  private int positionX;
  private int positionY;
  int shipPosition;
  double xVelocity;
  double yVelocity;
  double gravity;

  private final String key = null;
  private String name;
  private boolean move;
  private static final String TO_STRING_FORMAT = "%1$s[key=%2$s, name=%3$s, move=%4$s]";

  /**
   * Retrieves the X-coordinate position of the projectile.
   * @return The X-coordinate position of the projectile.
   */
  public int getPositionX() {
    return positionX;
  }


  /**
   * Retrieves the Y-coordinate position of the projectile.
   * @return The Y-coordinate position of the projectile.
   */
  public int getPositionY() {
    return positionY;
  }

  /**
   * Retrieves the bounding box of the projectile.
   * @return The Rect object representing the bounding box of the projectile.
   */
  public Rect getProjectileBox() {
    return projectileBox;
  }

  /**
   * Constructs a Projectile object with specified parameters.
   * @param positionX   The initial X-coordinate position of the projectile.
   * @param positionY   The initial Y-coordinate position of the projectile.
   * @param xVelocity   The initial velocity in the X direction.
   * @param yVelocity   The initial velocity in the Y direction.
   * @param gravity     The gravitational force affecting the projectile's movement.
   * @param gameField   The game field in which the projectile exists.
   */
  public Projectile(int positionX, int positionY, double xVelocity, double yVelocity, double gravity, GameField gameField) {
    // TODO: 11/13/23 xposition, yposition, gameFiled, xvelocity and yvelocity.
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.gravity = gravity;
    this.gameField = gameField;
    this.positionX = positionX;
    this.positionY = positionY;
//    positionX = gameField.getShip().getPositionX();
//    positionY = gameField.getShip().getPositionY();
    computeProjectileBox();

  }

//  public Projectile(int positionX, int positionY) {
//    this.positionX = positionX;
//    this.positionY = positionY;
//    computeProjectileBox();
//  }

  private void computeProjectileBox() {
    projectileBox = new Rect(positionX - PROJECTILE_SIZE / 2, positionY - PROJECTILE_SIZE  / 2,
        positionX + PROJECTILE_SIZE / 2, positionY + PROJECTILE_SIZE / 2);
    //Log.d(getClass().getSimpleName(), projectileBox.toString());
  }

  /**
   * Checks if the current projectile intersects with an enemy.
   * @param other The Rect object representing the bounding box of the enemies.
   * @return {@code true} if there is an intersection; {@code false} otherwise.
   */
  public boolean intersects(Rect other) {
    return projectileBox.intersect(other);
  }

//  public boolean detonate(Rect enemy) {
//    return projectileBox.intersect(enemy);
//  }

//  public void fire() {
//    if (gameField.getShip().canFire(projectileBox)) {
//      shipPosition = gameField.getShip().getPositionY();
//      double positionX =
//          xVelocity * flyingTime; // TODO: 11/2/23 Add a loop to increment the position.
//      double positionY =
//          (shipPosition + yVelocity) * flyingTime + (0.5 * gravity * Math.pow(flyingTime, 2));
//      projectileBox.inset((int) positionX,
//          (int) positionY); // FIXME: 11/9/23 Check if cast is the  best option for this.
//      updatePosition();
//    }
//  }

  /**
   * Updates the position of the projectile within the game field.
   * Handles the projectile's movement based on its velocity and gravity.
   */
  public void updatePosition() {
//    positionX += (int) (xVelocity * flyingTime);
//    positionY += (int) ((gameField.getShip().getPositionY() + yVelocity) * flyingTime + (0.5 * gameField.getGravity() * Math.pow(flyingTime, 2)))/10;
//    flyingTime += 0.05;
    //System.out.println(gameField.getGravity());

    positionX += xVelocity;
    positionY += yVelocity;
    yVelocity += (gameField.getGravity())/10;
    computeProjectileBox();
//    positionX += xVelocity;
//    positionY += yVelocity;
//    yVelocity += (gameField.getGravity())/100;

  }


  @Override
  public boolean equals(@Nullable Object obj) {
    boolean equivalent;
    if (obj == this) {
      equivalent = true;
    } else if (obj instanceof Projectile other) {
      //noinspection ConstantValue
      equivalent = Objects.equals(key, other.key) && Objects.equals(name, other.name)
          && (move == other.move);
    } else {
      equivalent = false;
    }
    return equivalent;
  }

  @Override
  public int hashCode() {
    return (name == null) ? Objects.hash(key, name, move) : Objects.hash(key, name);
  }

  @SuppressLint("DefaultLocale")
  @NonNull
  @Override
  public String toString() {
    return String.format(
        TO_STRING_FORMAT, getClass().getSimpleName(), key, name, move);
  }

}
