package edu.cnm.deepdive.gravity.model;

public class Meteor {
  private final GameField gameField;
  private int x;
  private int y;
  private int xVelocity;
  private int yVelocity;

  public Meteor(GameField gameField) {
    this.gameField = gameField;
  }
  //first constructor is gonna generate random position
  //second constructor is gonna generate attributes

  public int changeGravity(){
    // TODO: 10/24/23 There will be two types of meteors one that damage you and other that will change
    //  gravity used for projectile motion.
    throw new UnsupportedOperationException();
  }
  public boolean detonate(){
    // TODO: 10/24/23 Based on position if meteor hits the ship it will do some damage.
    throw new UnsupportedOperationException();
  }

  public int randomPosition(){ // Random or int ??
    // TODO: 10/24/23 Meteor will appear from the right side of the screen in a random position
    throw new UnsupportedOperationException();
  }

  public int position(){
    throw new UnsupportedOperationException();
  }

}
