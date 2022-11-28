package controller;

import java.io.InputStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controller.commands.BuySell;
import controller.commands.Composition;
import controller.commands.CostBasis;
import controller.commands.Create;
import controller.commands.DisplayChart;
import controller.commands.ICommandController;
import controller.commands.Value;
import model.IUserInterface;
import view.ViewInterface;

/**
 * This is our main controller classes that provides implementation for our command controller.
 * It has access to view and model (user).
 * It has view, user as private fields.
 * This class interacts with the model (user) and view.
 * It tells view what to print and tells model what to do.
 */

public class CommandController implements IController {

  private static ViewInterface view;
  private static IUserInterface user;
  private static InputStream userInput;
  Scanner inputScanner;

  /**
   * Instantiates a new Controller.
   * It takes in user, view and an input stream and
   * instantiates it to the private fields of this class.
   * It uses command design pattern which promotes delegation.
   * The commands supported in our application are as follows
   * 1. Create a new portfolio
   * 2. Display composition of a portfolio
   * 3. Check value of a portfolio on a particular date
   * 4. Option to buy or sell stocks for flexible portfolios
   * 5. View the cost basis of portfolios
   * 6. View performance of a portfolio
   * Details of each command are now kept in separate classes,
   * instead of all appearing within the controller.
   *
   * @param user the user model
   * @param view the view model
   * @param in   the input
   */
  public CommandController(IUserInterface user, ViewInterface view, InputStream in) {

    this.view = view;
    this.user = user;
    userInput = in;
    inputScanner = new Scanner(userInput);
  }

  /**
   * This method is a driver of our application.
   * It delegates the commands to their respective classes and methods.
   * It initially asks view to display the list of commands that are supported.
   * Based on the user input, it delegates the commands to respective classes.
   */

  @Override
  public void go() {
    ICommandController cmd = null;
    while (true) {
      try {
        switch (this.showMenuOnView()) {
          case 1: //Create new portfolio
            cmd = new Create(view, user, inputScanner);
            break;
          case 2: // Show composition of a Portfolio
            cmd = new Composition(view, user, inputScanner);
            break;
          case 3: // Check value of a Portfolio
            cmd = new Value(view, user, inputScanner);
            break;
          case 4: // Buy or sell shares of a portfolio
            cmd = new BuySell(view, user, inputScanner);
            break;
          case 5: // Check cost basis of a portfolio
            cmd = new CostBasis(view, user, inputScanner);
            break;
          case 6: // Display bar chart of a portfolio
            cmd = new DisplayChart(view, user, inputScanner);
            break;

          case 7:
            view.displayMsgToUser("Closing the application");
            System.exit(0);
            return;

          default:
            view.displayMsgToUser("Unknown command");
            System.exit(0);
            cmd = null;
            break;
        }
        if (cmd != null) {
          cmd.goController();
          cmd = null;
        }
      } catch (InputMismatchException ime) {
        view.displayMsgToUser("Unknown command");
      }
    }
  }

  /**
   * Asks view to display the menu options that are supported in our application.
   * Takes user input and returns it to the caller.
   *
   * @return user selection of the menu options.
   */

  private int showMenuOnView() {
    int userOption = 0;
    Boolean isOkay = false;
    List<Integer> validMenuOptions = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    do {
      try {
        this.view.displayMenu();
        userOption = Integer.parseInt(this.inputScanner.next());
        if (!validMenuOptions.contains(userOption)) {
          throw new IllegalArgumentException("Invalid Option!!");
        }
        isOkay = true;
      } catch (IllegalArgumentException ie) {
        if (!ie.getMessage().equals("Invalid Option!!")) {
          this.view.displayMsgToUser("Please enter an integer value");
        } else {
          this.view.displayMsgToUser(ie.getMessage());
        }

        isOkay = false;
      }
    }
    while (!isOkay);
    return userOption;
  }
}
