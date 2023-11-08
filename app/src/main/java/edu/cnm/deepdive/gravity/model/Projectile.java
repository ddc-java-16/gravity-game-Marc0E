package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import java.util.Objects;

public class Projectile {

  private final GameField gameField;
  //private final Meteor meteor;
  private Rect projectile;
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
  
  public boolean canMove(Rect enemy, int top, int right, int bottom){
    return !projectile.intersect(enemy) || projectile.intersects(0,top, right,bottom); // TODO: 11/8/23 Get top, right, bottom from screen.
  }

  public boolean detonate(Rect enem){
    return projectile.intersect(enem);
  }
  
  public int fire(){
    if (gameField.getShip().canFire(projectile)) {
      
    }else {
      
    }
    return 0; // FIXME: 11/8/23  
  }
  public int position(Rect project){
    return project.height();
  }

  public void computeTrajectory(Rect ship){
    shipPosition = gameField.getShip().position();
    xVelocity = (velocity * Math.cos(angle));
    yVelocity = (velocity * Math.sin(angle));
    //gravity = gameField.getMeteor().getGravity();
    totalFlyingTime = -yVelocity - (Math.sqrt(Math.pow(yVelocity,2) - (4*shipPosition*0.5*gravity))/gravity);
    positionX = xVelocity * flyingTime; // TODO: 11/2/23 Add a loop to increment the position.
    positionY = (shipPosition + yVelocity) * flyingTime + (0.5 * gravity * Math.pow(flyingTime,2));
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
