package controller.commands;

import controller.ICommandController;
import model.User;

public class BuySell implements ICommandController {
  @Override
  public void go(User m) {
    m.buySell();
  }
}
