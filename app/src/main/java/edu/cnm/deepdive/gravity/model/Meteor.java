package edu.cnm.deepdive.gravity.model;

public class Meteor {
  private final GameField gameField;
  private double velocity;
  private double xVelocity;
  private double yVelocity;
  private double positionX;
  private double positionY;
  private double angle;
  private double shipPosition;
  private double totalFlyingTime;
  private double flyingTime;
  private double GRAVITY = -9.81;

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
    shipPosition = gameField.getShip().position();
    xVelocity = (velocity * Math.cos(angle));
    yVelocity = (velocity * Math.sin(angle));
    totalFlyingTime = -yVelocity - (Math.sqrt(Math.pow(yVelocity,2) - (4*shipPosition*0.5*GRAVITY))/GRAVITY);
    positionX = xVelocity * flyingTime; // TODO: 11/2/23 Add a loop to increment the position.
    positionY = (shipPosition + yVelocity) * flyingTime + (0.5 * GRAVITY * Math.pow(flyingTime,2));


  }

  public int position(){
    throw new UnsupportedOperationException();
  }

}
