package edu.cnm.deepdive.gravity.controller;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.slider.Slider;
import com.google.android.material.slider.Slider.OnChangeListener;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.databinding.ActivityGameBinding;
import edu.cnm.deepdive.gravity.model.GameField;
import edu.cnm.deepdive.gravity.model.entity.Score;
import edu.cnm.deepdive.gravity.model.entity.User;
import edu.cnm.deepdive.gravity.viewmodel.GameFieldViewModel;
import edu.cnm.deepdive.gravity.viewmodel.ScoreViewModel;
import edu.cnm.deepdive.gravity.viewmodel.UserViewModel;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the GameActivity within the application, where the gameplay occurs.
 * This activity extends AppCompatActivity, providing compatibility with older versions
 * of Android.
 * It sets up the initial state of the game.
 * Upon creation, this activity initializes the game environment, such as setting up
 * the layout, initializing MediaPlayer for sound effects, and configuring game-specific
 * functionalities.
 */
@AndroidEntryPoint
public class GameActivity extends AppCompatActivity {

  GameField gameField;
  ImageView imageViewPhoto;
  MediaPlayer mediaPlayer;
  MediaPlayer mediaPlayer2;
  MediaPlayer mediaPlayer3;
  int background = 1;
  ImageView imageViewShip;
  TextView levelView;
  TextView counterView;
  Button moveUp;
  int Measuredwidth = 0;
  int Measuredheight = 0;
  Boolean inProgress;
  int score;
  int counter = 1;
  GameFieldViewModel gameFieldViewModel;
  private ActivityGameBinding binding;
  private UserViewModel userViewModel;
  private ScoreViewModel scoreViewModel;
  private User currentUser;


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    imageViewPhoto = findViewById(R.id.profile_photo);
    return super.onTouchEvent(event);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityGameBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    mediaPlayer3 = MediaPlayer.create(this, R.raw.explosion2);
    setup();
    gameButtons();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mediaPlayer != null) {
      mediaPlayer.release();
      mediaPlayer = null;
    }
    if (mediaPlayer2 != null) {
      mediaPlayer2.release();
      mediaPlayer2 = null;
    }
    if (mediaPlayer3 != null) {
      mediaPlayer3.release();
      mediaPlayer3 = null;
    }

  }

  private void gameButtons() {
    binding.play.setOnClickListener((view) -> {
      gameFieldViewModel.run();
      binding.pause.setVisibility(View.VISIBLE);
      binding.pause.setEnabled(true);
      binding.moveUp.setEnabled(true);
      binding.moveDown.setEnabled(true);
      binding.shoot.setEnabled(true);
      binding.play.setVisibility(View.INVISIBLE);
      mediaPlayer2 = MediaPlayer.create(this, R.raw.intro);
      mediaPlayer2.start();
    });
    binding.pause.setOnClickListener((view) -> {
      binding.play.setVisibility(View.VISIBLE);
      binding.moveUp.setEnabled(false);
      binding.moveDown.setEnabled(false);
      binding.shoot.setEnabled(false);
      binding.pause.setVisibility(View.INVISIBLE);
      gameFieldViewModel.paused();
    });
    binding.moveUp.setOnClickListener((view) -> {
      gameFieldViewModel.shipMoveUp();
    });
    binding.moveDown.setOnClickListener((view) -> gameFieldViewModel.shipMoveDown());
    binding.shoot.setOnClickListener((view) -> {
      gameFieldViewModel.shoot();
      mediaPlayer = MediaPlayer.create(this, R.raw.shoot);
      mediaPlayer.start();
    });
    binding.velocity.addOnChangeListener(new OnChangeListener() {
      @Override
      public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
        gameFieldViewModel.setVelocity(value);
        //System.out.println(value);
      }
    });
    binding.angle.addOnChangeListener(new OnChangeListener() {
      @Override
      public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
        gameFieldViewModel.setAngle((int) value);
        //System.out.println(value);
      }
    });

    binding.gravity.setBackgroundColor(Color.parseColor("#FFFFFF"));
    binding.gravity.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item,
        getResources().getStringArray(R.array.gravity_array)));
    binding.gravity.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String value = binding.gravity.getSelectedItem().toString();
        //System.out.println(value);
        switch (value) {
          case "Moon":
            gameFieldViewModel.setGravity(1.6);
            break;
          case "Mercury", "Mars":
            gameFieldViewModel.setGravity(3.7);
            break;
          case "Uranus":
            gameFieldViewModel.setGravity(8.7);
            break;
          case "Venus":
            gameFieldViewModel.setGravity(8.9);
            break;
          case "Saturn":
            gameFieldViewModel.setGravity(9);
            break;
          case "Earth":
            gameFieldViewModel.setGravity(9.8);
            break;
          case "Neptune":
            gameFieldViewModel.setGravity(11);
            break;
          case "Jupiter":
            gameFieldViewModel.setGravity(23.1);
            break;
          case "Sun":
            gameFieldViewModel.setGravity(274);
            break;
        }

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        gameFieldViewModel.setGravity(9.8);
      }
    });
  }

  private void setup() {
    binding.pause.setVisibility(View.INVISIBLE);
    binding.moveUp.setEnabled(false);
    binding.moveDown.setEnabled(false);
    binding.shoot.setEnabled(false);
    ViewModelProvider viewModelProvider = new ViewModelProvider(this);
    gameFieldViewModel = viewModelProvider.get(GameFieldViewModel.class);
    //Log.i("GameActivity", "Initialized");
    gameFieldViewModel
        .getGameField()
        .observe(this, (field) -> {
          if (gameField != field) {
            binding.gameView.setGameField(field);
            gameField = field;
          }
          int level = gameField.getLevel();
          binding.gameView.invalidate();
          binding.level.setText(String.valueOf(level));
          binding.counter.setText(String.valueOf(gameField.getCounter()));
          score = gameField.getCounter() * 50;
          if (this.counter == gameField.getCounter()) {
            mediaPlayer = MediaPlayer.create(this, R.raw.explosion2);
            mediaPlayer.start();
            this.counter++;
          }
          binding.scoreValueText.setText(String.valueOf(score));
          Drawable b = switch ((level - 1) % 3 + 1) {
            case 1 ->
                ResourcesCompat.getDrawable(getResources(), R.drawable.play_background1, null);
            case 2 -> ResourcesCompat.getDrawable(getResources(), R.drawable.img_1, null);
            case 3 -> ResourcesCompat.getDrawable(getResources(), R.drawable.img, null);
            default -> null;
          };
          if (b != null) {
            binding.gameConstraint.setBackground(b);
          }

        });
    gameFieldViewModel.getInProgress()
        .observe(this, (inProgress) -> {
          Log.d(getClass().getSimpleName(), "inProgress=" + inProgress);
          if (Boolean.TRUE.equals(this.inProgress) && Boolean.FALSE.equals(inProgress)) {
            Drawable b = ResourcesCompat.getDrawable(getResources(), R.drawable.play_background1,
                null);
            binding.gameConstraint.setBackground(b);
            mediaPlayer3.start();
            addScores();
            finish();
          }
          this.inProgress = inProgress;
        });
    setupUserViewModel(viewModelProvider, this);
    setupScoreViewModel(viewModelProvider, this);
  }


  private void setupUserViewModel(ViewModelProvider provider, LifecycleOwner owner) {
    userViewModel = provider
        .get(UserViewModel.class);
    userViewModel
        .getCurrentUser()
        .observe(owner, (user) -> this.currentUser = user);
  }

  private void setupScoreViewModel(ViewModelProvider provider, LifecycleOwner owner) {
    scoreViewModel = provider
        .get(ScoreViewModel.class);
    // TODO: 10/26/23 Observe scoreId or Score from view model.
  }

  private void addScores() {
    Score score = new Score();
    score.setStarted(Instant.now());
    score.setValue(this.score);
    score.setPlayer_id(currentUser.getId());
    scoreViewModel.save(score, currentUser);
  }


}