package controller.commands;

import controller.ICommandController;
import model.User;

public class DisplayChart implements ICommandController {
  @Override
  public void go(User m) {
    m.displayChart();
  }
}
