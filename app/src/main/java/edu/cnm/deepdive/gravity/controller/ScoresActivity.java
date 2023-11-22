package edu.cnm.deepdive.gravity.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.adapter.UserScoresAdapter;
import edu.cnm.deepdive.gravity.databinding.ActivityScoresBinding;
import edu.cnm.deepdive.gravity.databinding.FragmentScoresBinding;
import edu.cnm.deepdive.gravity.viewmodel.ScoreViewModel;
import org.jetbrains.annotations.NotNull;

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