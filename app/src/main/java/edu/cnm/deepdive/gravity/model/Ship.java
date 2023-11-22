package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import java.util.Objects;

public class Ship {

  private final String key = null;
  private int xPosition;
  private int yPosition;
  private String name;
  private boolean fly;
  private static final String TO_STRING_FORMAT = "%1$s[key=%2$s, name=%3$s, fly=%4$s]";
  private static final int SHIP_SIZE = 30;
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

  public Ship(GameField gameField, int y, int x) {
    // FIXME: 11/9/23 Check if this is correct
    //ship = new Rect(0, gameField.getBoundingBox().top/2,0,0);
    this.gameField = gameField;
    positionX = x;
    positionY = y;
    computeShipBox();
  }

  private void computeShipBox() {
    shipBox = new Rect(positionX - SHIP_SIZE, positionY - SHIP_SIZE /2, positionX + SHIP_SIZE,
        positionY + SHIP_SIZE/2);
    //Log.d(getClass().getSimpleName(), shipBox.toString());
  }

  public void moveUp() {
    if (positionY - 1 - SHIP_SIZE / 2 > gameField.getBoundingBox().top) {
      positionY -= 10;
      computeShipBox();
    }
  }

  public void moveDown() {
    if ((positionY + 1 - SHIP_SIZE / 2 < gameField.getBoundingBox().bottom)) {
      positionY += 10;
      computeShipBox();
    }
  }

//  public boolean canMove(){
//   return shipBox.bottom >= gameField.getBoundingBox().bottom && shipBox.height() <= gameField.getBoundingBox().top; // FIXME: 11/8/23 Maybe I'll need to use left and right instead of bottom and top.
//  }

  public boolean intersects(Rect intersect) {
    //return shipBox.centerX() == intersect.centerX() && shipBox.centerY() == intersect.centerY();
    return shipBox.intersect(intersect);
  }

  public Projectile fire() {

    computeTrajectory(velocity);
    // FIXME: 11/18/23 Pass x and y of ship
    Projectile projectile = new Projectile(positionX, positionY, xVelocity, yVelocity, gravity, gameField);
   // projectile.fire();
    return projectile;
    //projectile.fire(shipBox.height(),xVelocity, yVelocity, gravity);
  }

  public boolean canFire(Rect project) {
    return !project.isEmpty(); // FIXME: 11/8/23 What would be the best way to determine if ship can fire another projectile or not.
  }


  public void computeTrajectory(double velocity) {
    shipPosition = shipBox.height();
    xVelocity = (velocity * Math.cos(Math.toRadians(angle)));
    yVelocity = (velocity * Math.sin(Math.toRadians(angle)));
    //gravity = gameField.getMeteor().getGravity();
    totalFlyingTime =
        -yVelocity - (Math.sqrt(Math.pow(yVelocity, 2) - (4 * shipPosition * 0.5 * gravity))
            / gravity);
  }

  public double getVelocity() {
    return velocity;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public void setGravity(double gravity) {
    this.gravity = gravity;
  }

  public int getPositionX() {
    return positionX;
  }

  public int getPositionY() {
    return positionY;
  }

  public Rect getShipBox() {
    return shipBox;
  }

  public void setPositionY(int positionY) {
    this.positionY = positionY;
    computeShipBox();
  }

  public void setPositionX(int positionX) {
    this.positionX = positionX;
    computeShipBox();
  }


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
