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
  String costBasisPortfolio = null;
  String costBasisDate = null;
  String costBasis = null;
  String valueDate = null;
  String fixedValue = null;
  String flexibleDate = null;

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
            "1 testFixed1.csv\n" +
            "2 testFixedPortfolio1.csv\n" +
            "3 testPortfolio1.csv\n" +
            "Pick a portfolio\n";
    testFixedPortfolio1 = "Following stocks are present in the portfolio : \n" +
            "TickerName\tNumberOfUnits\tTransactionDate\tCommission(USD)\tPrice(USD)\tBUY/SELL\n" +
            "TSLA\t\t10.0\t\t\t2022-11-16\t\t0.0\t\t\t194.42\t\t\tBUY\n" +
            "MSFT\t\t50.0\t\t\t2022-11-16\t\t0.0\t\t\t241.97\t\t\tBUY\n" +
            "GOOG\t\t100.0\t\t\t2022-11-16\t\t0.0\t\t\t98.72\t\t\tBUY\n" +
            "AAPL\t\t40.0\t\t\t2022-11-16\t\t0.0\t\t\t150.04\t\t\tBUY\n" +
            "V\t\t75.0\t\t\t2022-11-16\t\t0.0\t\t\t209.99\t\t\tBUY\n";
    displayListOfFlexiblePortfolios = "Following are the flexible portfolios created till now:\n" +
            "1 testFlex1.csv\n"
            + "2 testFlexiblePortfolio1.csv\n"
            + "Pick a portfolio\n";
    dateFromUser = "Please enter the date for which you want to view the composition(yyyy-MM-dd)\n";
    testFlexiblePortfolio1 = "Following stocks are present in the portfolio : \n" +
            "TickerName\tNumberOfUnits\tTransactionDate\tCommission(USD)\tPrice(USD)\tBUY/SELL\n" +
            "NVDA\t\t50.0\t\t\t2022-09-07\t\t10.0\t\t\t137.14\t\t\tBUY\n" +
            "V\t\t50.0\t\t\t2022-10-17\t\t10.0\t\t\t185.25\t\t\tBUY\n" +
            "TSLA\t\t100.0\t\t\t2022-11-05\t\t10.0\t\t\t207.47\t\t\tBUY\n" +
            "GOOG\t\t25.0\t\t\t2022-11-12\t\t10.0\t\t\t96.73\t\t\tBUY\n" +
            "V\t\t25.0\t\t\t2022-11-01\t\t10.0\t\t\t206.93\t\t\tSELL\n" +
            "NVDA\t\t25.0\t\t\t2022-10-31\t\t10.0\t\t\t134.97\t\t\tSELL\n";

    costBasisPortfolio = "Cost Basis can be calculated only for flexible portfolios.\n" +
            "Following are the flexible portfolios created till now:\n" +
            "1 testFlex1.csv\n" + "Pick a portfolio\n";
    costBasisDate = "Enter the date :(yyyy-mm-dd)\n";
    costBasis = "CostBasis of the portfolio as of date 2022-11-16 is: 39344.75 USD\n";
    valueDate = "Enter Date for which you want to check the value : (yyyy-mm-dd)\n";
    fixedValue = "Value of the portfolio as of date 2022-11-16 is: 45566.80 USD\n";
    flexibleDate = "Value of the portfolio as of date 2022-11-16 is: 30397.75 USD\n";
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
    input = new ByteArrayInputStream("2 2 1 2022-11-16 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testCostBasisOfFlexiblePortfolio() {
    log = new StringBuilder();
    log.append(menu+costBasisPortfolio+costBasisDate+costBasis+menu+exit);
    input = new ByteArrayInputStream("5 1 2022-11-16 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testValueOfFixedPortfolio() {
    log = new StringBuilder();
    log.append(menu+portfolioOptions+displayListOfFixedPortfolios+valueDate+fixedValue+menu+exit);
    input = new ByteArrayInputStream("3 1 1 2022-11-16 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testValueOfFlexiblePortfolio() {
    log = new StringBuilder();
    log.append(menu+portfolioOptions+displayListOfFlexiblePortfolios+valueDate+flexibleDate+menu+exit);
    input = new ByteArrayInputStream("3 2 1 2022-11-16 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }


}