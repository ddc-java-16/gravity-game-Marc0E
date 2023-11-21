package edu.cnm.deepdive.gravity.model.pojo;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import java.time.Instant;

@DatabaseView(
    viewName = "user_score",
    value =
        "SELECT s.player_id, u.display_name, u.display_nick_name, s.value, s.created "
            + "FROM user AS u JOIN score AS s ON u.user_id = s.player_id "
            + "ORDER BY value DESC"
)
public class UserScore {

  @ColumnInfo(name = "player_id")
  private long playerId;

  @ColumnInfo(name = "display_name")
  private String display_name;


  private long value;

  @ColumnInfo(name = "display_nick_name")
  private String display_nick_name;

  private Instant created;

  public long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(long playerId) {
    this.playerId = playerId;
  }

  public String getDisplay_name() {
    return display_name;
  }

  public void setDisplay_name(String display_name) {
    this.display_name = display_name;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  public String getDisplay_nick_name() {
    return display_nick_name;
  }

  public void setDisplay_nick_name(String display_nick_name) {
    this.display_nick_name = display_nick_name;
  }

  public Instant getCreated() {
    return created;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }
}
