import Controller.ControllerImpl;
import Model.IUserInterface;
import Model.User;
import View.ViewImpl;
import View.ViewInterface;

public class StockMarket {

  public static void main(String args[]) {

    //create the model.
    IUserInterface u = new User("First User");

    //create the view.
    ViewInterface view = new ViewImpl(System.out); //this input stream can be used by view as well as junit tests.

    //create the controller and give it the model.
    ControllerImpl controller = new ControllerImpl(u,view,System.in);

    //control to the controller.
    controller.go();
  }
}
