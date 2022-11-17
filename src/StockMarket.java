import controller.CommandController;
import controller.ICommandController;
import model.IUserInterface;
import model.User;
import view.ViewImpl;
import view.ViewInterface;

/**
 * Driver Class of this application.
 */
public class StockMarket {

  /**
   * Main method of the driver class.
   */
  public static void main(String[] args) {

    //create the model.
    IUserInterface u = new User(null);

    //create the view.
    ViewInterface view = new ViewImpl(System.out);

    //create the controller and give it the model and view.
    ICommandController controller = new CommandController(u, view, System.in);
    controller.go();

  }
}
