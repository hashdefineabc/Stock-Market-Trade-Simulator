package controller.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.IUserInterface;
import model.User;
import controller.ICommandController;
import view.ViewInterface;

public class Create implements ICommandController {
  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;
  public Create(ViewInterface view, IUserInterface user) {
    this.view = view;
    this.user = user;
    scanner = new Scanner(System.in);
  }

  @Override
  public void go() {
    if (this.showCreatePortfolioOptionsOnView() == 1) {
      view.displayMsgToUser("Creating a new portfolio...");
      String portfolioName = this.getPortFolioNameFromView();
      List<String[]> stockList = new ArrayList<>();
      do {
        String[] s = this.takeStockInputFromView();
        stockList.add(s);
      }
      while (this.addMoreStocksFromView());
      if (user.createPortfolioManually(portfolioName, stockList)) {
        view.displayMsgToUser("Portfolio saved successfully");
      } else {
        view.displayMsgToUser("Portfolio was not saved. Try again");
      }
    } else if (this.showCreatePortfolioOptionsOnView() == 2) {
      //upload a file
      this.displayCsvPathToUser();
      //check if file uploaded
      view.isFileUploaded();
      if (scanner.nextInt() == 1) {
        user.createPortFolioFromFile();
      }
    }
  }

  /**
   * Show create portfolio options on view int.
   *
   * @return the int
   */
  public int showCreatePortfolioOptionsOnView() {
    int userOption = 3;
    List<Integer> validMenuOptions = Arrays.asList(1, 2);
    while (!validMenuOptions.contains(userOption)) {
      try {
        this.view.displayCreatePortFolioOptions();
        userOption = Integer.parseInt(scanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    }
    return userOption;
  }

  public String getPortFolioNameFromView() {
    this.view.getPortfolioNameFromUser();
    String portfolioName = scanner.next();
    while (user.checkIfFileExists(portfolioName)) {
      this.view.displayMsgToUser("This portfolio already exists in the system!!");
      this.view.getPortfolioNameFromUser();
      portfolioName = scanner.next();
    }
    return portfolioName;
  }

  public String[] takeStockInputFromView() {
    String[] userStockInput = new String[2];
    String tickerNameFromUser = "";
    int numUnits = -1;
    while (numUnits <= 0 || !user.isTickerValid(tickerNameFromUser)) {
      this.view.takeTickerName();
      tickerNameFromUser = scanner.next();
      userStockInput[0] = tickerNameFromUser;
      this.view.takeNumOfUnits();
      numUnits = scanner.nextInt();
      userStockInput[1] = Integer.toString(numUnits);
    }

    return userStockInput;
  }

  public boolean addMoreStocksFromView() {
    int userInput = 3;
    List<Integer> validOptions = Arrays.asList(0, 1);

    while (!validOptions.contains(userInput)) {
      this.view.addMoreStocks();
      userInput = scanner.nextInt();
    }
    return userInput == 1;
  }

  public void displayCsvPathToUser() {
    this.view.displayMsgToUser("Please place the csv at the location:\n"
            + this.user.getFolderPath());
  }
}
