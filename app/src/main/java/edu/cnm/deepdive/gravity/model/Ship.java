package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import java.util.List;
import java.util.Objects;

public class Ship {

  @Expose(deserialize = true, serialize = false)
  private final String key = null;
  @Expose
  private String name;
  @Expose
  private boolean fly;
  private static final String TO_STRING_FORMAT = "%1$s[key=%2$s, name=%3$s, fly=%4$s]";

  private Rect ship;
  private GameField gameField;


  public boolean canMove(){
    return ship.bottom >= gameField.getGameField().bottom && ship.height() <= gameField.getGameField().top; // FIXME: 11/8/23 Maybe I'll need to use left and right instead of bottom and top.
  }

  public int position() {
    return ship.height();
  }

  public boolean canFire(Rect project){
    return project.isEmpty(); // FIXME: 11/8/23 What would be the best way to determine if ship can fire another projectile or not.
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
