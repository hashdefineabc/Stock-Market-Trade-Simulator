package controller.commands;

import java.util.Scanner;

import controller.ICommandController;
import model.User;

public class CommandController {

  public void executeCommandController() {
    Scanner s = new Scanner(System.in);
    User m = new User();
    ICommandController cmd = null;
    while (s.hasNext()) {
      String in = s.next();
  }
}

}
