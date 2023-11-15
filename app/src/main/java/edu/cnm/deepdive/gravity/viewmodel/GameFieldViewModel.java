package edu.cnm.deepdive.gravity.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameFieldViewModel extends ViewModel {

  public MutableLiveData<Integer> top;
  public GameFieldViewModel() {
    top = new MutableLiveData<>();
    top.setValue(0);
    Log.i("Main view model", "ViewModel is created");

  }

  public void increment() {
    top.setValue(top.getValue()+1);
  }

  public void decrement() {
    top.setValue(top.getValue()-1);
  }
}
