package edu.cnm.deepdive.gravity.model;

public class Meteor {
  private final GameField gameField;
  private int x;
  private int y;
  private double velocity;
  private double xVelocity;
  private double yVelocity;
  private double angle;
  private double trajectory;
  private double heightOfShip;
  private double distance;
  private double flyingTime;
  private final double GRAVITY = 9.81;

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

  public void trajectory(){
    xVelocity = (Math.cos(velocity));
    yVelocity = (Math.sin(velocity));
    flyingTime = ((2*yVelocity) / GRAVITY);
    //distance =
    heightOfShip = gameField.getShip().position();
    trajectory = heightOfShip;

  }

  public int position(){
    throw new UnsupportedOperationException();
  }

}
