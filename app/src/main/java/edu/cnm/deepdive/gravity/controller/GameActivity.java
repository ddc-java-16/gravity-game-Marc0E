package edu.cnm.deepdive.gravity.controller;

import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.model.GameField;

public class GameActivity extends AppCompatActivity {

  GameField gameField;
  TextView levelView;
  TextView counterView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    gameField = new GameField();

    levelView = findViewById(R.id.level);
    levelView.setText(String.valueOf(gameField.getLevel()));

    counterView = findViewById(R.id.counter);
    counterView.setText(String.valueOf(gameField.getCounter()));

  }
}