package edu.cnm.deepdive.gravity.service;

public class PlayingFieldRepository {

  public void create() {

  }

  public void start() {

  }

  public void stop() {

  }

  public void timekeeper() {
    // TODO: 11/1/23 Timer in charge of movement of objects.
  }

  public void updateShip() {
    // TODO: 11/1/23 Add two helper methods to move ship up/down.

  }

  public void updateProjectile() {
    // TODO: 11/1/23 Based on the force and angle selected the projectile will move.

  }

  public void gravityChanger() {
    // TODO: 11/1/23 Create at least 3 different type of meteor (each one will have a different gravity)
    //  that won't damage the ship but if the ship crashes with them the gravity the ship is using to shot projectile will change.
    //  These gravityChangers are going to be generated randomly just like meteors.
  }

  public void updateMeteor() {
// TODO: 11/1/23 Based on the width of the screen and size of ship meteor will generate a random number
//  of meteors (from 1 to ((width/ship.size) - ship.size - gravityChanger.count)) moving from right to left leaving space for
//  the ship to move between them.
  }

  public void enemy() {
    // TODO: 11/1/23 Display enemies in different positions of the screen.
  }

  public void enemyIntersection(){

  }

  public void shipIntersection(){

  }


}
