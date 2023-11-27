package edu.cnm.deepdive.gravity.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.Instant;

/**
 * Encapsulates the data elements maintained for a signed-in user in the app's Room/SQLite-based
 * persistent data store.
 */
@Entity(
    tableName = "user",
    indices = {
        @Index(value = "oauth_key", unique = true),
        @Index(value = "display_name", unique = true)
    }
)
public class User {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "user_id")
  private long id;

  @NonNull
  private Instant created = Instant.MIN;

  @ColumnInfo(name = "oauth_key")
  @NonNull
  private String oauthKey = "";

  @ColumnInfo(name = "display_name", collate = ColumnInfo.NOCASE)
  @NonNull
  private String displayName = "";

  @ColumnInfo(name = "display_nick_name", collate = ColumnInfo.NOCASE)
  @NonNull
  private String displayNickName = "";

  @ColumnInfo(name = "user_photo")
  private String userPhoto = "";

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  public void setCreated(@NonNull Instant created) {
    this.created = created;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
  }

  @NonNull
  public String getDisplayNickName() {
    return displayNickName;
  }

  public void setDisplayNickName(@NonNull String displayNickName) {
    this.displayNickName = displayNickName;
  }

  public String getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }

  @Override
  public int hashCode() {
    return Long.hashCode(id);
  }

}

