package controller.commands;

import model.User;
import model.PortfolioModel;
import controller.ICommandController;

public class Create implements ICommandController {
  private PortfolioModel portfolio;

  public void create(PortfolioModel portfolio) {
    this.portfolio = portfolio;
  }

  @Override
  public void go(User m) {
    m.CreateNewPortfolio(this.portfolio);
  }
}
