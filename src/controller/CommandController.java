package controller;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controller.commands.BuySell;
import controller.commands.Composition;
import controller.commands.CostBasis;
import controller.commands.Create;
import controller.commands.DisplayChart;
import controller.commands.Value;
import model.IUserInterface;
import model.User;
import view.ViewInterface;

public class CommandController implements ICommandController{

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
  public CommandController(IUserInterface user, ViewInterface view, InputStream in) {
    InputStream userInput;
    this.view = view;
    this.user = user;
    userInput = in;
    scanner = new Scanner(userInput);
  }

  @Override
  public void go() {
    ICommandController cmd = null;
    while (true) {
      try {
        switch (this.showMenuOnView()) {
          case 1: //Create new portfolio
            cmd = new Create(view, user);
            break;
          case 2: // Show composition of a Portfolio
            cmd = new Composition(view, user);
            break;
          case 3: // Check value of a Portfolio
            cmd = new Value(view, user);
            break;
          case 4: // Buy or sell shares of a portfolio
            cmd = new BuySell(view, user);
            break;
          case 5: // Check cost basis of a portfolio
            cmd = new CostBasis(view, user);
            break;
          case 6: // Display bar chart of a portfolio
            cmd = new DisplayChart();
            break;

          case 7:
            view.displayMsgToUser("Closing the application");
            System.exit(0);
            break;

          default:
            System.out.println(String.format("Unknown command"));
            System.exit(0);
            cmd = null;
            break;
        }
        if (cmd != null) {
          cmd.go();
          cmd = null;
        }
      } catch (InputMismatchException ime) {
        System.out.println("Unknown command");
      }
    }
  }

  private int showMenuOnView() {
    int userOption = 8;
    List<Integer> validMenuOptions = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
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
}
