package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import java.util.Objects;

public class Ship {

  @Expose(deserialize = true, serialize = false)
  private final String key = null;
  @Expose
  private String name;
  @Expose
  private boolean fly;
  private static final String TO_STRING_FORMAT = "%1$s[key=%2$s, name=%3$s, fly=%4$s]";

  private Rect shipBox;
  private GameField gameField;
  private Projectile projectile;
  private double xVelocity;
  private double yVelocity;
  private double velocity;
  private double positionX;
  private double positionY;
  private double angle;
  private double shipPosition;
  private double totalFlyingTime;
  private double flyingTime;
  private double gravity;

  public Ship(Rect ship) {
    // FIXME: 11/9/23 Check if this is correct
    ship = new Rect(0, gameField.getBoundingBox().top/2,0,0);
    this.shipBox = ship;
  }

  public void moveUp(){
    if(shipBox.bottom >= gameField.getBoundingBox().bottom && shipBox.height() <= gameField.getBoundingBox().top) { // FIXME: 11/8/23 Maybe I'll need to use left and right instead of bottom and top.
      shipBox.top = shipBox.top + 1;
    }
  }
  public void moveDown(){
    if(shipBox.bottom >= gameField.getBoundingBox().bottom && shipBox.height() <= gameField.getBoundingBox().top) {
      shipBox.bottom = shipBox.bottom - 1;
    }
  }


//  public boolean canMove(){
//   return shipBox.bottom >= gameField.getBoundingBox().bottom && shipBox.height() <= gameField.getBoundingBox().top; // FIXME: 11/8/23 Maybe I'll need to use left and right instead of bottom and top.
//  }

  public int position() {
    return shipBox.height();
  }

  public Projectile fire(){
    computeTrajectory(velocity);
    Projectile projectile = new Projectile(shipBox.height(),xVelocity, yVelocity, gravity);
    return projectile;
    //projectile.fire(shipBox.height(),xVelocity, yVelocity, gravity);
  }

  public boolean canFire(Rect project){
    return project.isEmpty(); // FIXME: 11/8/23 What would be the best way to determine if ship can fire another projectile or not.
  }


  public void computeTrajectory(double velocity){
    shipPosition = shipBox.height();
    xVelocity = (velocity * Math.cos(angle));
    yVelocity = (velocity * Math.sin(angle));
    //gravity = gameField.getMeteor().getGravity();
    totalFlyingTime = -yVelocity - (Math.sqrt(Math.pow(yVelocity,2) - (4*shipPosition*0.5*gravity))/gravity);
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
