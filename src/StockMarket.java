import java.util.Scanner;

import controller.CommandController;
import controller.GUIController;
import controller.IController;
import model.IUserInterface;
import model.User;
import view.ViewImpl;
import view.ViewInterface;
import view.viewButton.ButtonView;
import view.viewButton.HomeView;

/**
 * Driver Class of this application.
 */
public class StockMarket {

  /**
   * Main method of the driver class.
   */
  public static void main(String[] args) {

    //create the model.
    IUserInterface user = new User(null);
    Scanner scanner = new Scanner(System.in);

    //create the view.
    ViewInterface vText;
    ButtonView vGUI;
    //create the controller and give it the model and view.
    IController controller;

    while (true) {
      System.out.println("Pick an option\n");
      System.out.println("1 - Text-Based UI");
      System.out.println("2 - GUI");

      String option = scanner.next();

      if (option.equals("1")) {
        vText = new ViewImpl(System.out);
        controller = new CommandController(user, vText, System.in);
        break;
      }
      else if (option.equals("2")) {
        vGUI = new HomeView("Home");
        controller = new GUIController(user, vGUI);
        break;
      }
      else {
        System.out.println("Invalid choice, try again\n");
      }
    }
    controller.go();

  }
}
