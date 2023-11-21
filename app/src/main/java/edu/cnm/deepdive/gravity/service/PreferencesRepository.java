package edu.cnm.deepdive.gravity.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesRepository implements OnSharedPreferenceChangeListener {

  private final MutableLiveData<SharedPreferences> preferences;
  private final SharedPreferences prefs;

  @Inject
  PreferencesRepository(@ApplicationContext Context context) {
    prefs = PreferenceManager.getDefaultSharedPreferences(context);
    preferences = new MutableLiveData<>(prefs);
    prefs.registerOnSharedPreferenceChangeListener((prefs, key) -> preferences.postValue(prefs));
  }

  public LiveData<SharedPreferences> getPreferences() {
    return preferences;
  }

  public <T> T get(String key, T defaultValue) {
    //noinspection unchecked
    T result = (T) prefs.getAll().get(key);
    return (result != null) ? result : defaultValue;
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences preferences, String ignoredKey) {
    this.preferences.postValue(preferences);
  }
}
