package cs3500.pa05.controller;

import cs3500.pa05.model.bullets.Bullet;
import javafx.scene.control.Button;

/**
 * represents a controller for bullets
 */
public interface BulletController extends MainController {
  /**
   * gets the bullet created by this bullet controller
   *
   * @return the created bullet
   */
  Bullet getBullet();

  /**
   * gets the create button from this bullet controller
   *
   * @return the create button
   */
  Button getCreateButton();

  /**
   * disables the create button based on whether fields in
   * new bullet dialog are entered properly
   */
  void disable();
}
