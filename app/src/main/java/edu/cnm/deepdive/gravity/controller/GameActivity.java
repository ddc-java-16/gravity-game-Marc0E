package edu.cnm.deepdive.gravity.controller;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.databinding.ActivityGameBinding;
import edu.cnm.deepdive.gravity.model.GameField;
import edu.cnm.deepdive.gravity.model.entity.User;
import edu.cnm.deepdive.gravity.viewmodel.GameFieldViewModel;
import edu.cnm.deepdive.gravity.viewmodel.ScoreViewModel;
import edu.cnm.deepdive.gravity.viewmodel.UserViewModel;
import java.util.concurrent.atomic.AtomicInteger;

public class GameActivity extends AppCompatActivity {

  GameField gameField;
  ImageView imageViewPhoto;
  ImageView imageViewShip;
  TextView levelView;
  TextView counterView;
  Button moveUp;
  GameFieldViewModel gameFieldViewModel;
  int sum=0;
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
    setContentView(R.layout.activity_game);
    gameFieldViewModel = new ViewModelProvider(this).get(GameFieldViewModel.class);
    Log.i("GameActivity", "Initialized");

    gameField = new GameField();

    levelView = findViewById(R.id.level);
    //levelView = binding.level;
    levelView.setText(String.valueOf(gameField.getLevel()));

    counterView = findViewById(R.id.counter);
    counterView.setText(String.valueOf(gameField.getCounter()));

    gameFieldViewModel.top.observe(this, new Observer<Integer>() {
      @Override
      public void onChanged(Integer integer) {
        moveUp.setText(String.valueOf(gameFieldViewModel.top.getValue()));
      }
    });
    //imageViewPhoto.setImageURI();
    moveUp =  findViewById(R.id.move_up);
    imageViewShip=findViewById(R.id.ship);
    AtomicInteger p = new AtomicInteger(imageViewShip.getTop());
    moveUp.setOnClickListener((v) ->{
      gameFieldViewModel.increment();
      //sum = sum + gameFieldViewModel.getTop();
      //moveUp.setText(String.valueOf(gameFieldViewModel.top()));
      //imageViewShip.setTop(sum);
        });
    moveUp.setOnClickListener((v) ->{
     // moveUp.setText(String.valueOf(p.getAndIncrement()));
      //imageViewShip.setTop(imageViewPhoto.getTop() + 1);
    });

  }

  private void setupUserViewModel(FragmentActivity activity, LifecycleOwner owner) {
    userViewModel = new ViewModelProvider(activity)
        .get(UserViewModel.class);
    userViewModel
        .getCurrentUser()
        .observe(owner, (user) -> this.currentUser = user);
  }

  private void setupScoreViewModel(FragmentActivity activity, LifecycleOwner owner) {
    scoreViewModel = new ViewModelProvider(activity)
        .get(ScoreViewModel.class);
    // TODO: 10/26/23 Observe scoreId or Score from view model.
  }
}