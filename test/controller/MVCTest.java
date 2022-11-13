package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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

  @Before
  public void setup() {
    out = new ByteArrayOutputStream();
    input = null;
    u = new User();
    view = new ViewImpl(new PrintStream(out));
    log = new StringBuilder();
    expectedOutputs.put("menuOk", "\n***********************************\n"
            + "\t1. Create a new Portfolio\n" + "\t2. Retrieve Portfolio\n"
            + "\t3. Check value of a Portfolio\n" + "\t4. Exit the application.\n"
            + "Pick one of the options\n");
    expectedOutputs.put("menuNotOk", "\n***********************************\n"
            + "\t1. Create a new Portfolio\n"
            + "\t2. Retrieve Portfolio\n"
            + "\t3. Check value of a Portfolio\n"
            + "\t4. Exit the application.\n"
            + "Pick one of the options\n"
            + "Please enter only an integer value from the below options!!\n"
            + "***********************************\n"
            + "\t1. Create a new Portfolio\n"
            + "\t2. Retrieve Portfolio\n"
            + "\t3. Check value of a Portfolio\n"
            + "\t4. Exit the application.\n"
            + "Pick one of the options\n");

    expectedOutputs.put("retrievePortFolio","\n***********************************\n"
            + "\t1. Create a new Portfolio\n"
            + "\t2. Retrieve Portfolio\n"
            + "\t3. Check value of a Portfolio\n"
            + "\t4. Exit the application.\n" + "Pick one of the options\n" +
            "Following are the portfolios created till now:1 TestPortFolio1.csv\n" +
            "2 TestPortfolio2.csv\n" +
            "Pick a portfolio\n" +
            "Following stocks are present in the portfolio : \n" +
            "Ticker\tNumberOfUnits\tDateBoughtAt\n" +
            "\n" +
            "MSFT\t10\t\t\t\t2022-11-02\n" +
            "GOOG\t20\t\t\t\t2022-11-02\n");

  }

  @Test
  public void testDisplayMenu() {

    log = new StringBuilder();
    log.append(expectedOutputs.get("menuOk"));
    input = new ByteArrayInputStream("1".getBytes());
    Controller controller = new CommandController(u, view, input);
    controller.showMenuOnView();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testWrongOptionForMenu() {
    input = new ByteArrayInputStream("a 2".getBytes());
    Controller controller = new ControllerImpl(u,view,input);
    controller.showMenuOnView();
    assertEquals(expectedOutputs.get("menuNotOk"),out.toString());
  }

  @Test
  public void testRetrievePortFolio() {
    input = new ByteArrayInputStream("2 1".getBytes());
    Controller controller = new ControllerImpl(u,view,input);
    controller.showMenuOnView();
    controller.retrievePortFolios("fixed");
    String o = out.toString();
    assertEquals(o,out.toString());
  }


}