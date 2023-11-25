package edu.cnm.deepdive.gravity.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import edu.cnm.deepdive.gravity.R;
import edu.cnm.deepdive.gravity.databinding.ActivityGameBinding;
import edu.cnm.deepdive.gravity.model.Enemy;
import edu.cnm.deepdive.gravity.model.GameField;
import edu.cnm.deepdive.gravity.model.Meteor;

public class GameFieldView extends View {

  private GameField gameField;
  private Drawable shipImage;
  private Drawable meteorImage;
  private Drawable projectileImage;
  private Drawable enemyImage;
  int test = 0;
  private final Rect destination = new Rect();


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
      if (gameField.getShip() != null) {
        Rect shipBox = gameField.getShip().getShipBox();
        destination.set((int) (shipBox.left * horizontalScale),
            (int) (shipBox.top * verticalScale),
            (int) (shipBox.right * horizontalScale),
            (int) (shipBox.bottom * verticalScale));
        shipImage.setBounds(destination);
        shipImage.draw(canvas);
//        Paint outlinePaint = new Paint();
//        outlinePaint.setStyle(Paint.Style.STROKE);
//        outlinePaint.setStrokeWidth(5); 
//        outlinePaint.setColor(Color.RED);
//        canvas.drawRect(destination, outlinePaint);
      }

      if (gameField.getProjectile() != null) {
        Rect projectileBox = gameField.getProjectile().getProjectileBox();
        destination.set((int) (projectileBox.left * horizontalScale),
            (int) (projectileBox.top * verticalScale),
            (int) (projectileBox.right * horizontalScale),
            (int) (projectileBox.bottom * verticalScale));
        projectileImage.setBounds(destination);
        projectileImage.draw(canvas);
      }

      for (Meteor meteor : gameField.getMeteors()) {
        Rect meteorBox = meteor.getMeteorBox();
        destination.set((int) (meteorBox.left * horizontalScale),
            (int) (meteorBox.top * verticalScale),
            (int) (meteorBox.right * horizontalScale),
            (int) (meteorBox.bottom * verticalScale));
        meteorImage.setBounds(destination);
        meteorImage.draw(canvas);

//        Paint outlinePaint = new Paint();
//        outlinePaint.setStyle(Paint.Style.STROKE);
//        outlinePaint.setStrokeWidth(5);
//        outlinePaint.setColor(Color.RED);
//        canvas.drawRect(destination, outlinePaint);
      }

      for (Enemy enemy : gameField.getEnemies()) {
        Rect enemyBox = enemy.getEnemyBox();
        destination.set((int) (enemyBox.left * horizontalScale),
            (int) (enemyBox.top * verticalScale),
            (int) (enemyBox.right * horizontalScale),
            (int) (enemyBox.bottom * verticalScale));
        enemyImage.setBounds(destination);
        enemyImage.draw(canvas);
      }
    }

    // TODO: 11/20/23 Draw the rest of the objects.

  }


  public GameField getGameField() {
    return gameField;
  }

  public void setGameField(GameField gameField) {
    this.gameField = gameField;
  }


  private void loadResources(Resources resources) {
    shipImage = ResourcesCompat.getDrawable(resources, R.drawable.ship, null);
    meteorImage = ResourcesCompat.getDrawable(resources, R.drawable.meteor2, null);
    enemyImage = ResourcesCompat.getDrawable(resources, R.drawable.enemy, null);
    projectileImage = ResourcesCompat.getDrawable(resources, R.drawable.projectile9, null);
  }


}
