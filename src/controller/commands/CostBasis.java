package controller.commands;

import java.util.Scanner;

import controller.ICommandController;
import model.IUserInterface;
import model.User;
import view.ViewInterface;

public class CostBasis implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;

  public CostBasis(ViewInterface view, IUserInterface user) {
    this.view = view;
    this.user = user;
    scanner = new Scanner(System.in);
  }
  @Override
  public void go() {
    String portfolioType = null;
    if (this.showFixedOrFlexPortfolioOptionsOnView() == 1) {
      //create a fixed portfolio
      portfolioType = "fixed";
    }
    else if (this.showFixedOrFlexPortfolioOptionsOnView() == 2) {
      //create a flexible portfolio
      portfolioType = "flexible";
    }


  }
}
