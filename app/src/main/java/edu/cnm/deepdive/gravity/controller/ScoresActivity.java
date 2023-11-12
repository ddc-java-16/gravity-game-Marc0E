package edu.cnm.deepdive.gravity.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.databinding.FragmentScoresBinding;

public class ScoresActivity extends AppCompatActivity {
  private FragmentScoresBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scores);
  }

  public static class ScoresFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
      setPreferencesFromResource(R.xml.scores, rootKey);
    }
  }
}