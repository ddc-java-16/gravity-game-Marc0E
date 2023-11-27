/*
 *  Copyright 2023 CNM Ingenuity, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cnm.deepdive.gravity.controller;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.model.GameField;
import edu.cnm.deepdive.gravity.viewmodel.LoginViewModel;
import edu.cnm.deepdive.gravity.viewmodel.PreferencesViewModel;

/**
 * Represents the main activity of the application, providing the main user interface.
 * This activity contains UI elements and functionality to navigate to different parts
 * of the application, such as starting the game, accessing settings, viewing scores,
 * and exiting the application.
 * It initializes UI components and sets up click listeners to handle user interactions,
 * navigating to different activities based on button clicks.
 * Additionally, it uses a MediaPlayer to handle audio playback for button clicks and
 * background music when the activity is created.
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  MediaPlayer mediaPlayer;
  GameField gameField;
  Button playButton;
  Button settingsButton;
  Button scoresButton;
  Button exitButton;
  private LoginViewModel loginViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mediaPlayer = MediaPlayer.create(this, R.raw.main_menu);
    mediaPlayer.start();
    setupNavigation();
    setupViewModels();

    playButton = findViewById(R.id.play);
    playButton.setOnClickListener((v) -> {
      Intent intent = new Intent(this, GameActivity.class);
      mediaPlayer = MediaPlayer.create(this, R.raw.onclick);
      mediaPlayer.start();
      startActivity(intent);
    });

    settingsButton = findViewById(R.id.settings);
    settingsButton.setOnClickListener((v) -> {
      Intent intent = new Intent(this, SettingsActivity.class);
      mediaPlayer = MediaPlayer.create(this, R.raw.onclick);
      mediaPlayer.start();
      startActivity(intent);
    });

    scoresButton = findViewById(R.id.scores);
    scoresButton.setOnClickListener((v) -> {
      Intent intent = new Intent(this, ScoresActivity.class);
      mediaPlayer = MediaPlayer.create(this, R.raw.onclick);
      mediaPlayer.start();
      startActivity(intent);

    });

    exitButton = findViewById(R.id.exit);
    exitButton.setOnClickListener((v) -> {
      mediaPlayer = MediaPlayer.create(this, R.raw.onclick);
      mediaPlayer.start();
      finish();
    });
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mediaPlayer != null) {
      mediaPlayer.release();
      mediaPlayer = null;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    int itemId = item.getItemId();
    if (itemId == R.id.settings) {
      Intent intent = new Intent(this, SettingsActivity.class);
      startActivity(intent);
    } else if (itemId == R.id.sign_out) {
      loginViewModel.signOut();
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public boolean onSupportNavigateUp() {
    getOnBackPressedDispatcher().onBackPressed();
    return true;
  }


  private void setupNavigation() {
//    AppBarConfiguration config = new AppBarConfiguration.Builder(
//        R.id.game_fragment, R.id.scores_fragment
//    ).build();
//    NavController navController = ((NavHostFragment)getSupportFragmentManager()
//        .findFragmentById(R.id.nav_host_fragment)).getNavController();
//    NavigationUI.setupActionBarWithNavController(this, navController, config);
  }

  private void setupViewModels() {
    loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    loginViewModel
        .getAccount()
        .observe(this, this::handleAccount);
    //NavHostFragment navHostFragment =
    //    (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
  }

  private void handleAccount(GoogleSignInAccount account) {
    if (account == null) {
      Intent intent = new Intent(this, LoginActivity.class)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }
  }

}