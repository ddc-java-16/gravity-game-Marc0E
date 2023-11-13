package edu.cnm.deepdive.gravity.controller;

import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.motion.widget.MotionController;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.model.GameField;
import edu.cnm.deepdive.gravity.model.entity.User;
import edu.cnm.deepdive.gravity.service.UserRepository;

public class GameActivity extends AppCompatActivity {

  GameField gameField;
  ImageView imageViewPhoto;
  TextView levelView;
  TextView counterView;

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    imageViewPhoto = findViewById(R.id.profile_photo);
    return super.onTouchEvent(event);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    gameField = new GameField();

    levelView = findViewById(R.id.level);
    levelView.setText(String.valueOf(gameField.getLevel()));

    counterView = findViewById(R.id.counter);
    counterView.setText(String.valueOf(gameField.getCounter()));

    //imageViewPhoto.setImageURI();

  }
}