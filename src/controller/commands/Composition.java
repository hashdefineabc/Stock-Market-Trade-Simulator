package controller.commands;

import controller.ICommandController;
import model.User;

public class Composition implements ICommandController {
  private Integer portfolioIndex;

  public void Composition(Integer index) {
    this.portfolioIndex = index;
  }
  @Override
  public void go(User m) {
    m.displayStocksOfPortFolio(this.portfolioIndex);
  }
}
