package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import java.util.Objects;

public class Projectile {

  private GameField gameField;
  private Rect projectileBox;
  private double flyingTime;
  int shipPosition;
  double xVelocity;
  double yVelocity;
  double gravity;


  @Expose(deserialize = true, serialize = false)
  private final String key = null;
  @Expose
  private String name;
  @Expose
  private boolean move;
  private static final String TO_STRING_FORMAT = "%1$s[key=%2$s, name=%3$s, move=%4$s]";

  public Projectile(int shipPosition, double xVelocity, double  yVelocity, double gravity) {
    this.shipPosition = shipPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.gravity = gravity;

  }
  
  public boolean canMove(Rect enemy, Rect gameField){
    return !projectileBox.intersect(enemy) || projectileBox.intersect(gameField); // FIXME: 11/9/23 Check if have right logic.
  }

  public boolean detonate(Rect enemy){
    return projectileBox.intersect(enemy);
  }
  
  public int fire(){
    if (gameField.getShip().canFire(projectileBox)) {
      double positionX =
          xVelocity * flyingTime; // TODO: 11/2/23 Add a loop to increment the position.
      double positionY =
          (shipPosition + yVelocity) * flyingTime + (0.5 * gravity * Math.pow(flyingTime, 2));
      projectileBox.inset((int) positionX, (int) positionY); // FIXME: 11/9/23 Check if cast is the  best option for this.
    }
    return 0; // FIXME: 11/8/23  
  }
  public int position(Rect project){
    return project.height();
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
