package edu.cnm.deepdive.gravity.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.databinding.ActivityGameBinding;
import edu.cnm.deepdive.gravity.model.GameField;
import edu.cnm.deepdive.gravity.model.entity.User;
import edu.cnm.deepdive.gravity.viewmodel.GameFieldViewModel;
import edu.cnm.deepdive.gravity.viewmodel.ScoreViewModel;
import edu.cnm.deepdive.gravity.viewmodel.UserViewModel;
import java.util.concurrent.atomic.AtomicInteger;

@AndroidEntryPoint
public class GameActivity extends AppCompatActivity {

  GameField gameField;
  ImageView imageViewPhoto;
  ImageView imageViewShip;
  TextView levelView;
  TextView counterView;
  Button moveUp;
  int Measuredwidth = 0;
  int Measuredheight = 0;
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
    gameFieldViewModel = new ViewModelProvider(this).get(GameFieldViewModel.class);
    Log.i("GameActivity", "Initialized");
    gameFieldViewModel
        .getGameField()
        .observe(this, (field) -> {
          gameField = field;
          binding.level.setText(String.valueOf(gameField.getLevel()));
          binding.counter.setText(String.valueOf(gameField.getCounter()));
          // TODO: 11/18/23 Pass updated gamefield to a view to render, create a subclass of view that's gonna render.
        });

    binding.play.setOnClickListener((view) -> gameFieldViewModel.run());
    binding.pause.setOnClickListener((view) -> gameFieldViewModel.paused());
    binding.moveUp.setOnClickListener((view) -> gameFieldViewModel.shipMoveUp());
    binding.moveDown.setOnClickListener((view) -> gameFieldViewModel.shipMoveDown());
    binding.shoot.setOnClickListener((view) -> gameFieldViewModel.shoot());
     

    //getScreenSize();

    binding.gravity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
        getResources().getStringArray(R.array.gravity_array)));
    binding.gravity.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String value = binding.gravity.getSelectedItem().toString();
        System.out.println(value);
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

  private void getScreenSize() {
    Point size = new Point();
    WindowManager w = getWindowManager();
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
      w.getDefaultDisplay().getSize(size);
      Measuredwidth = size.x;
      Measuredheight = size.y;
    }else{
      Display d = w.getDefaultDisplay();
      Measuredwidth = d.getWidth();
      Measuredheight = d.getHeight();
    }
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