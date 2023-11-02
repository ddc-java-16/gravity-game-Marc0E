package edu.cnm.deepdive.gravity.model;

import android.annotation.SuppressLint;
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

  public boolean canMove(){
    // TODO: 10/24/23 Delimit distance of movement up and down
   throw new UnsupportedOperationException();
  }

  public int position(){
    throw new UnsupportedOperationException();
  }

  public void fire(){
    // TODO: 10/24/23 This method should call Projectile method.

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
