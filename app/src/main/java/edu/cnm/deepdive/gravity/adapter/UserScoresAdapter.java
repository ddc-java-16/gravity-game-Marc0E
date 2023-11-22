package edu.cnm.deepdive.gravity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.adapter.UserScoresAdapter.Holder;
import edu.cnm.deepdive.gravity.databinding.ItemUserScoreBinding;
import edu.cnm.deepdive.gravity.model.pojo.UserScore;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class UserScoresAdapter extends RecyclerView.Adapter<Holder> {

  private final List<UserScore> scores;
  private final LayoutInflater inflater;
  private final NumberFormat numberFormatter;
  private final DateTimeFormatter dateFormatter;
  private final ZoneId zone;
  @ColorInt
  private final int  oddRowBackground;
  @ColorInt
  private final int  evenRowBackground;

  public UserScoresAdapter(Context context, List<UserScore> scores) {
    this.scores = scores;
    inflater = LayoutInflater.from(context);
    dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,FormatStyle.SHORT);
    oddRowBackground = context.getColor(android.R.color.background_light);
    evenRowBackground = context.getColor(R.color.even_row_background);
    numberFormatter = NumberFormat.getIntegerInstance();
    zone = ZoneId.systemDefault();
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(ItemUserScoreBinding.inflate(inflater, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return scores.size();
  }

  public  class Holder extends RecyclerView.ViewHolder {

    private final ItemUserScoreBinding binding;
    private Holder(@NonNull ItemUserScoreBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(int position) {
      UserScore score = scores.get(position);
      binding.ranking.setText(String.valueOf(position+1));
      binding.displayName.setText(score.getDisplay_name());
      binding.score.setText(numberFormatter.format(score.getValue()));
      binding.created.setText(LocalDateTime.ofInstant(score.getCreated(),zone).format(dateFormatter));
      binding.getRoot().setBackgroundColor((position % 2 ==0) ? evenRowBackground : oddRowBackground);
    }

  }

}
