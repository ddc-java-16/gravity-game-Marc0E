package edu.cnm.deepdive.gravity.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.model.GameField;

public class GameFieldView extends View {

  private GameField gameField;
  private Drawable shipImage;
  private Drawable meteorImage;
  private Rect destination = new Rect();


  public GameFieldView(Context context) {
    super(context);
    loadResources(context.getResources());
  }

  public GameFieldView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    loadResources(context.getResources());
  }

  public GameFieldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    loadResources(context.getResources());
  }

  public GameFieldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    loadResources(context.getResources());
  }


  @Override
  protected void onDraw(@NonNull Canvas canvas) {
    super.onDraw(canvas);
    if (gameField != null) {
      int canvasWidth = canvas.getWidth();
      int canvasHeight = canvas.getHeight();
      int gameWidth = gameField.getBoundingBox().width();
      int gameHeight = gameField.getBoundingBox().height();
      double horizontalScale = (double) canvasWidth / gameWidth;
      double verticalScale = (double) canvasHeight / gameHeight;

      // TODO: 11/20/23 Draw the ship on canvas.
      destination.set((int) (gameField.getShip().getShipBox().left * horizontalScale),
          (int) (gameField.getShip().getShipBox().top * verticalScale),
          (int) (gameField.getShip().getShipBox().right * horizontalScale),
          (int) (gameField.getShip().getShipBox().bottom * verticalScale));
      shipImage.setBounds(destination);
      shipImage.draw(canvas);

      destination.set((int) (gameField.getMeteor().getMeteorBox().left * horizontalScale),
          (int) (gameField.getMeteor().getMeteorBox().top * verticalScale),
          (int) (gameField.getMeteor().getMeteorBox().right * horizontalScale),
          (int) (gameField.getMeteor().getMeteorBox().bottom * verticalScale));
      meteorImage.setBounds(destination);
      meteorImage.draw(canvas);
      // TODO: 11/20/23 Draw the rest of the objects.


    }
  }

  public GameField getGameField() {
    return gameField;
  }

  public void setGameField(GameField gameField) {
    this.gameField = gameField;
  }

  private void loadResources(Resources resources){
    shipImage = ResourcesCompat.getDrawable(resources, R.drawable.ship, null);
    // TODO: 11/20/23 Load the other drawables.
  }
}
