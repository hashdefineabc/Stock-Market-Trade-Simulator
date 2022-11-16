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
  String portfolioOptions = null;
  String displayListOfFixedPortfolios = null;
  String testFixedPortfolio1 = null;
  String displayListOfFlexiblePortfolios = null;
  String dateFromUser = null;
  String testFlexiblePortfolio1 = null;

  @Before
  public void setup() {
    out = new ByteArrayOutputStream();
    input = null;
    u = new User("./resources/testPortfolio");
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
    portfolioOptions = "Please pick one \n" +
            "1. Fixed Portfolio\n" +
            "2. Flexible Portfolio\n";
    displayListOfFixedPortfolios = "Following are the fixed portfolios created till now:\n" +
            "1 testFixedPortfolio1.csv\n" +
            "2 testPortfolio1.csv\n" +
            "Pick a portfolio\n";
    testFixedPortfolio1 = "Following stocks are present in the portfolio : \n" +
            "TickerName\tNumberOfUnits\tTransactionDate\tCommission(USD)\tPrice(USD)\tBUY/SELL\n" +
            "MSFT\t\t200.0\t\t\t2022-10-26\t\t0.0\t\t\t2000.0\t\t\tBUY\n" +
            "AAPL\t\t100.0\t\t\t2022-11-15\t\t0.0\t\t\t1000.0\t\t\tBUY\n";
    displayListOfFlexiblePortfolios = "Following are the flexible portfolios created till now:\n" +
            "1 testFlexiblePortfolio1.csv\n" + "Pick a portfolio\n";
    dateFromUser = "Please enter the date for which you want to view the composition(yyyy-MM-dd)\n";
    testFlexiblePortfolio1 = "Following stocks are present in the portfolio : \n" +
            "TickerName\tNumberOfUnits\tTransactionDate\tCommission(USD)\tPrice(USD)\tBUY/SELL\n" +
            "MSFT\t\t200.0\t\t\t2022-10-26\t\t20.0\t\t\t2000.0\t\t\tBUY\n" +
            "AAPL\t\t100.0\t\t\t2022-11-15\t\t10.0\t\t\t1000.0\t\t\tBUY\n";

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

  @Test
  public void testCompositionOfFixedPortfolio() {
    log = new StringBuilder();
    log.append(menu+portfolioOptions+displayListOfFixedPortfolios+testFixedPortfolio1+menu+exit);
    input = new ByteArrayInputStream("2 1 1 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testCompositionOfFlexiblePortfolio() {
    log = new StringBuilder();
    log.append(menu+portfolioOptions + displayListOfFlexiblePortfolios + dateFromUser
            + testFlexiblePortfolio1+menu+exit);
    input = new ByteArrayInputStream("2 2 1 2022-11-15 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }



}