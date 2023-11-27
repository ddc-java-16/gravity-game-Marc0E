package edu.cnm.deepdive.gravity.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.preference.PreferenceManager;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlin.jvm.functions.Function1;


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

  /***
   * Utility method to allow pass-trhough read access to the underlying {@link SharedPreferences},
   * by key. The compiler will infer the type parameter {@code T} by examining the
   * {@code defaultValue} and the reference type of the assignment target (if any); if the inferred
   * type is not one supported as a Shared
   * @param key
   * @param defaultValue
   * @return
   * @param <T>
   */
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
