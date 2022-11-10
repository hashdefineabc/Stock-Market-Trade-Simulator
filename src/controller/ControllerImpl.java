package controller;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
<<<<<<< HEAD
import model.IUserInterface;
import model.IstockModel;;
import view.ViewInterface;
=======

import model.IUserInterface;
import model.IstockModel;
import view.ViewInterface;

>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47
import java.util.Scanner;

/**
 * Class to implement the controller of the stock market application.
 */
public class ControllerImpl implements Controller {
  private static ViewInterface view;
  private static IUserInterface user;

  /**
   * The Scanner.
   */
  Scanner scanner;

  /**
   * Instantiates a new Controller.
   * It takes in user, view and an input stream and
   * instantiates it to the private fields of this class.
   *
   * @param user the user
   * @param view the view
   * @param in   the input
   */
  public ControllerImpl(IUserInterface user, ViewInterface view, InputStream in) {
    InputStream userInput;
    this.view = view;
    this.user = user;
    userInput = in;
    scanner = new Scanner(userInput);
  }

  @Override
  public int showMenuOnView() {
    int userOption = 5;
    List<Integer> validMenuOptions = Arrays.asList(1, 2, 3, 4);
    while (!validMenuOptions.contains(userOption)) {
      try {
        this.view.displayMenu();
        userOption = Integer.parseInt(scanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    }
    return userOption;
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

  @Override
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

  @Override
  public boolean addMoreStocksFromView() {
    int userInput = 3;
    List<Integer> validOptions = Arrays.asList(0, 1);

    while (!validOptions.contains(userInput)) {
      this.view.addMoreStocks();
      userInput = scanner.nextInt();
    }
    return userInput == 1;
  }

  @Override
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

  @Override
  public int getSelectedPortFolioFromView() {
    int index = -1;
    while ((index < 0) || (index > user.getPortfolioNamesCreated().size())) {
      view.getSelectedPortfolio();
      index = scanner.nextInt();
    }
    return index;
  }

  @Override
  public LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.getDateFromUser();
        valueDate = LocalDate.parse(scanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
    }
    return valueDate;
  }

  private boolean isPortFolioEmpty(List<IstockModel> stockList) {
    if (stockList.isEmpty()) {
      this.view.displayMsgToUser("Portfolio is empty right now...");
      return true;
    }
    return false;
  }

  private LocalDate validateDateForValue() {
    LocalDate date = this.getDateFromView();
    LocalDate today = LocalDate.now();
    //validation for date
    while (date.isAfter(today)) {
      view.displayMsgToUser("Can't get value for date greater than today");
      date = this.getDateFromView();
    }
    LocalTime fourPM = LocalTime.of(16, 00, 00, 342123342);
    if (date.equals(today) && LocalTime.now().compareTo(fourPM) < 0) {
      view.displayMsgToUser("Value will be calculated based on yesterday's closing price.");
      date = date.minusDays(1); //modify date to yesterday
    }
    return date;
  }

  /**
   * Display csv path to user.
   */
  public void displayCsvPathToUser() {
    this.view.displayMsgToUser("Please place the csv at the location:\n"
            + this.user.getFolderPath());
  }

  @Override
  public boolean retrievePortFolios() {
    if (user.getPortfolioNamesCreated().size() == 0) {
      view.displayMsgToUser("No portfolios created till now");
      return false;
    }
    view.displayMsgToUser("Following are the portfolios created till now:");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated());
    int portfolioIndex = this.getSelectedPortFolioFromView();
    List<String[]> stocksToDisplay = user.displayStocksOfPortFolio(portfolioIndex);
    view.displayStocks(stocksToDisplay);
    return true;
  }



  @Override
  public void goController() {

    while (true) {
      switch (this.showMenuOnView()) {
        // create new portfolio and add stocks to the new portfolio
        case 1:
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

          break;

        //retrieve portfolio
        case 2:
          if (!this.retrievePortFolios()) {
            continue;
          }
          break;

        // value of a particular portfolio
        case 3:
          if (user.getPortfolioNamesCreated().size() == 0) {
            view.displayMsgToUser("No portfolios created till now, can't calculate value");
            continue;
          }
          view.displayListOfPortfolios(user.getPortfolioNamesCreated());
          int portfolioIndexForVal = this.getSelectedPortFolioFromView();

          LocalDate date = this.validateDateForValue();
          double val = user.calculateValueOfPortfolio(portfolioIndexForVal, date);

          if (val == 0) {
            view.displayMsgToUser("Market was closed on " + date);
          } else if (val == -1) {
            view.displayMsgToUser(
                    "Value cannot be calculated for a date prior to portfolio creation");
          } else {
            view.displayValue(val);
          }
          break;

        case 4:
          view.displayMsgToUser("Closing the application");
          System.exit(0);
          break;

        default:
          System.exit(0);
      }
    }
  }

}