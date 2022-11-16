package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;

import model.IUserInterface;
import model.User;
import view.ViewImpl;
import view.ViewInterface;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the flow of the model, view and controller.
 */
public class MVCTest {

  OutputStream out;
  InputStream input;
  IUserInterface u;
  ViewInterface view;
  StringBuilder log;

  HashMap<String, String> expectedOutputs = new HashMap<>();
  String menu = null;
  String exit = null;
  String wrongMenu = null;

  @Before
  public void setup() {
    out = new ByteArrayOutputStream();
    input = null;
    u = new User();
    view = new ViewImpl(new PrintStream(out));
    log = new StringBuilder();
    menu = "\n***********************************\n"
            + "\t1. Create a new Portfolio\n"
    +"\t2. View Composition of a Portfolio\n"
    +"\t3. Check value of a Portfolio\n"
    +"\t4. Buy or Sell Shares in a Portfolio\n"
            + "\t5. View Cost Basis of a Portfolio\n"
            + "\t6. Display bar chart of a Portfolio\n"
            + "\t7. Close the Application\n"
            + "Pick one of the options\n";

    exit = "Closing the application\n";
    wrongMenu = "Invalid Option!!\n";

  }

  @Test
  public void testDisplayMenu() {

    log = new StringBuilder();
    log.append(menu+exit);
    input = new ByteArrayInputStream("7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testWrongMenuOption() {

    log = new StringBuilder();
    log.append(menu+wrongMenu+menu+exit);
    input = new ByteArrayInputStream("9 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString() );
  }


}