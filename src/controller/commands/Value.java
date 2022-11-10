package controller.commands;

import java.time.LocalDate;

import controller.ICommandController;
import model.User;

public class Value implements ICommandController {
  private Integer portfolioIndex;
  private LocalDate date;

  public void Value(Integer index, LocalDate date) {
    this.portfolioIndex = index;
    this.date = date;
  }
  @Override
  public void go(User m) {
    m.calculateValueOfPortfolio(portfolioIndex, date);
  }
}
