<<<<<<< HEAD
import Controller.ControllerImpl;
=======
import controller.ControllerImpl;
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47
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
    IUserInterface u = new User();

    //create the view.
    ViewInterface view = new ViewImpl(System.out);
    //this input stream can be used by view as well as junit tests.

    //create the controller and give it the model.
    ControllerImpl controller = new ControllerImpl(u,view,System.in);

    //control to the controller.
    controller.goController();
  }
}
