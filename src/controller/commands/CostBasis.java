package controller.commands;

import controller.ICommandController;
import model.User;

public class CostBasis implements ICommandController {
  @Override
  public void go(User m) {
    m.calculateCostBasis();
  }
}
