package edu.cnm.deepdive.gravity.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.gravity.adapter.UserScoresAdapter;
import edu.cnm.deepdive.gravity.databinding.ActivityScoresBinding;
import edu.cnm.deepdive.gravity.viewmodel.ScoreViewModel;

@AndroidEntryPoint
public class ScoresActivity extends AppCompatActivity {

  private ActivityScoresBinding binding;
  private ScoreViewModel scoreViewModel;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityScoresBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);
    scoreViewModel.getAllScores().observe(this, (scores) ->
        binding.scores.setAdapter(new UserScoresAdapter(this, scores)));
  }

}