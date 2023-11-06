package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import java.util.Objects;

public class Projectile {

  private final GameField gameField;
  //private final Meteor meteor;
  private double velocity;
  private double xVelocity;
  private double yVelocity;
  private double positionX;
  private double positionY;
  private double angle;
  private double shipPosition;
  private double totalFlyingTime;
  private double flyingTime;
  private double gravity;


  @Expose(deserialize = true, serialize = false)
  private final String key = null;
  @Expose
  private String name;
  @Expose
  private boolean move;
  private static final String TO_STRING_FORMAT = "%1$s[key=%2$s, name=%3$s, move=%4$s]";

  public Projectile(GameField gameField, Ship ship, Meteor meteor) {
    this.gameField = gameField;
    //this.meteor = meteor;
  }


  public void trajectory(){
    shipPosition = gameField.getShip().position();
    xVelocity = (velocity * Math.cos(angle));
    yVelocity = (velocity * Math.sin(angle));
    gravity = gameField.getMeteor().getGravity();
    totalFlyingTime = -yVelocity - (Math.sqrt(Math.pow(yVelocity,2) - (4*shipPosition*0.5*gravity))/gravity);
    positionX = xVelocity * flyingTime; // TODO: 11/2/23 Add a loop to increment the position.
    positionY = (shipPosition + yVelocity) * flyingTime + (0.5 * gravity * Math.pow(flyingTime,2));
  }
  
  public boolean canMove(){
    // TODO: 10/24/23 If projectile in motion hits the frame of the screen
    //  I don't know if I should do something or not. 
    throw new UnsupportedOperationException();
  }

  public boolean detonate(){
    throw new UnsupportedOperationException();
  }
  
  public int fire(){
    // TODO: 10/24/23 Projectile needs to move using the formula of projectile motion.   
    throw new UnsupportedOperationException();
  }
  public int position(){
    // TODO: 10/24/23 Keep track of the motion of projectile to know if it hits something.
    //  I dont know if I should do this in fire method or if I need this method.
    throw new UnsupportedOperationException();
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
