package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
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
  String loadedFromFile = null;
  String testFlexiblePortfolio1 = null;
  String wrongDate = null;
  String furtureBuy = null;
  String wrongCommission = null;
  String costBasisPortfolio = null;
  String costBasisDate = null;
  String loadFileDone = null;
  String loadedFixed = null;
  String loadedPf = null;
  String costBasis = null;
  String valueDate = null;
  String addOrSell = null;
  String unitsToSell = null;
  String cannotSell = null;
  String addSellList = null;
  String fixedValue = null;
  String optionsForGraph = null;
  String fixedWeeklyGraph = null;
  String fixedMonthlyGraph = null;
  String fixedYearlyGraph = null;
  String flexibleValue = null;
  String askCom = null;
  String txnDate = null;
  String flexStockDate1 = null;
  String flexStockDate2 = null;
  String createOptions = null;
  String nameForCreate = null;
  String enterTickerName = null;
  String enterUnits = null;
  String marketStringForFixed = null;
  String previousDay = null;
  String addMoreStocks = null;
  String portfolioSaved = null;

  @Before
  public void setup() {
    out = new ByteArrayOutputStream();
    input = null;
    u = new User("./resources/testPortfolio");
    view = new ViewImpl(new PrintStream(out));
    log = new StringBuilder();
    menu = "\n***********************************\n"
            + "\t1. Create a new Portfolio\n"
    + "\t2. View Composition of a Portfolio\n" + "\t3. Check value of a Portfolio\n"
    + "\t4. Buy or Sell Shares in a Portfolio\n" + "\t5. View Cost Basis of a Portfolio\n"
            + "\t6. Display bar chart of a Portfolio\n" + "\t7. Close the Application\n"
            + "Pick one of the options\n";

    exit = "Closing the application\n";
    wrongMenu = "Invalid Option!!\n";
    portfolioOptions = "Please pick one \n" + "1. Fixed Portfolio\n" + "2. Flexible Portfolio\n";
    createOptions = "\t1. Enter the stock details manually\n" + "\t2. Upload an existing file\n";
    nameForCreate = "Creating a new portfolio...\n" + "\tPlease enter a name for this portfolio:"
            + " (No spaces or special characters allowed in the name)\n";
    enterTickerName = "Enter the ticker name:\n";
    enterUnits = "Enter the number of units purchased\n";
    marketStringForFixed = "Market is not closed today yet, "
            + "previous available closing price will be considered as your transaction price...\n"
            + "Price of the stock considered is of the date ";
    previousDay = LocalDate.now().minusDays(1).toString() + "\n";
    addMoreStocks = "Do you want to add more stocks to this portfolio? "
            + "(Press 1 for Yes, 0 for No):\n";
    portfolioSaved = "Portfolio saved successfully\n";
    displayListOfFixedPortfolios = "Following are the fixed portfolios created till now:\n"
            + "1 testFixed1.csv\n" + "2 testFixedPortfolio1.csv\n" + "Pick a portfolio\n";
    testFixedPortfolio1 = "Following stocks are present in the portfolio : \n"
            + "TickerName\tNumberOfUnits\tTransactionDate\tCommission(USD)\tPrice(USD)\tBUY/SELL\n"
            + "TSLA\t\t10.0\t\t\t2022-11-16\t\t0.0\t\t\t194.42\t\t\tBUY\n"
            + "MSFT\t\t50.0\t\t\t2022-11-16\t\t0.0\t\t\t241.97\t\t\tBUY\n"
            + "GOOG\t\t100.0\t\t\t2022-11-16\t\t0.0\t\t\t98.72\t\t\tBUY\n"
            + "AAPL\t\t40.0\t\t\t2022-11-16\t\t0.0\t\t\t150.04\t\t\tBUY\n"
            + "V\t\t75.0\t\t\t2022-11-16\t\t0.0\t\t\t209.99\t\t\tBUY\n";
    displayListOfFlexiblePortfolios = "Following are the flexible portfolios created till now:\n"
            + "1 testFlex1.csv\n" +"2 testFlexiblePortfolio1.csv\n" + "Pick a portfolio\n";
    dateFromUser = "Please enter the date for which you want to view the composition(yyyy-MM-dd)\n";
    testFlexiblePortfolio1 = "Following stocks are present in the portfolio : \n"
            + "TickerName\tNumberOfUnits\tTransactionDate\tCommission(USD)\tPrice(USD)\tBUY/SELL\n"
            + "NVDA\t\t50.0\t\t\t2022-09-07\t\t10.0\t\t\t137.14\t\t\tBUY\n"
            + "V\t\t50.0\t\t\t2022-10-17\t\t10.0\t\t\t185.25\t\t\tBUY\n"
            + "TSLA\t\t100.0\t\t\t2022-11-05\t\t10.0\t\t\t207.47\t\t\tBUY\n"
            + "GOOG\t\t25.0\t\t\t2022-11-12\t\t10.0\t\t\t96.73\t\t\tBUY\n"
            + "V\t\t25.0\t\t\t2022-11-01\t\t10.0\t\t\t206.93\t\t\tSELL\n"
            + "NVDA\t\t25.0\t\t\t2022-10-31\t\t10.0\t\t\t134.97\t\t\tSELL\n";

    costBasisPortfolio = "Cost Basis can be calculated only for flexible portfolios.\n"
            + "Following are the flexible portfolios created till now:\n"
            + "1 testFlex1.csv\n" + "2 testFlexiblePortfolio1.csv\n" + "Pick a portfolio\n";
    costBasisDate = "Enter the date :(yyyy-mm-dd)\n";
    costBasis = "CostBasis of the portfolio as of date 2022-11-16 is: 39344.75 USD\n";
    valueDate = "Enter Date for which you want to check the value : (yyyy-mm-dd)\n";
    fixedValue = "Value of the portfolio as of date 2022-11-16 is: 45566.80 USD\n";
    flexibleValue = "Value of the portfolio as of date 2022-11-16 is: 30397.75 USD\n";
    askCom = "Enter the commission value (USD) for this transaction: \n";
    txnDate = "Enter the date of transaction : (yyyy-mm-dd)\n";
    flexStockDate1 = "Price of the stock considered is of the date 2022-11-10\n";
    flexStockDate2 = "Price of the stock considered is of the date 2022-11-09\n";
    optionsForGraph = "Please pick an option\n"
            + "1. Display chart for previous week\n"
            + "2. Display chart for previous month\n"
            + "3. Display chart for previous year\n";
    fixedWeeklyGraph = "Performance of the portfolio testFixed1.csv from 2022-11-17 to 2022-11-10\n"
            + "2022-11-17:******************************\n"
            + "2022-11-16:******************************\n" + "2022-11-15:<*\n"
            + "2022-11-14:<*\n" + "2022-11-13:<*\n" + "2022-11-12:<*\n" + "2022-11-11:<*\n"
            + "<* means the value of the portfolio for a particular timestamp "
            + "is less than the scale\n" + "Scale: * = $1518.8933333333332\n";
    fixedMonthlyGraph = "Performance of the portfolio testFixed1.csv from 2022-11-17"
            + " to 2022-10-18\n" + "2022-11-17:******************************\n"
            + "2022-11-16:******************************\n"
            + "2022-11-15:<*\n" + "2022-11-14:<*\n" + "2022-11-13:<*\n" + "2022-11-12:<*\n"
            + "2022-11-11:<*\n" + "2022-11-10:<*\n" + "2022-11-09:<*\n" + "2022-11-08:<*\n"
            + "2022-11-07:<*\n" + "2022-11-06:<*\n" + "2022-11-05:<*\n" + "2022-11-04:<*\n"
            + "2022-11-03:<*\n" + "2022-11-02:<*\n" + "2022-11-01:<*\n" + "2022-10-31:<*\n"
            + "2022-10-30:<*\n" + "2022-10-29:<*\n" + "2022-10-28:<*\n" + "2022-10-27:<*\n"
            + "2022-10-26:<*\n" + "2022-10-25:<*\n" + "2022-10-24:<*\n" + "2022-10-23:<*\n"
            + "2022-10-22:<*\n" + "2022-10-21:<*\n" + "2022-10-20:<*\n" + "2022-10-19:<*\n"
            + "<* means the value of the portfolio for a particular timestamp is less than "
            + "the scale\n" + "Scale: * = $1518.8933333333332\n";

    fixedYearlyGraph = "Performance of the portfolio testFixed1.csv from NOVEMBER 2022 "
            + "to DECEMBER 2021\n" + "NOV 2022 : ******************************\n"
            + "OCT 2022 : <*\n" + "SEP 2022 : <*\n" + "AUG 2022 : <*\n" + "JUL 2022 : <*\n"
            + "JUN 2022 : <*\n" + "MAY 2022 : <*\n" + "APR 2022 : <*\n" + "MAR 2022 : <*\n"
            + "FEB 2022 : <*\n" + "JAN 2022 : <*\n" + "DEC 2021 : <*\n"
            + "<* means the value of the portfolio for a particular timestamp "
            + "is less than the scale\n"
            + "Scale: * = $1518.8933333333332\n";
    addOrSell = "Please pick an operation\n" + "1. Add Stock to the portfolio\n"
            + "2. Sell Stock from the portfolio\n";
    unitsToSell = "Enter the number of units to sell\n";
    cannotSell = "Cannot sell this stock!\n";
    addSellList = "Following is the list of portfolios available for adding/sellingstocks\n"
            + "1 testFlex1.csv\n" + "2 testFlexiblePortfolio1.csv\n" + "Pick a portfolio\n";
    wrongDate = "Future date entered... Please enter a date that is not later than today!!! \n";
    furtureBuy = "Price of the stock considered is of the date 2022-11-16\n";
    wrongCommission = "Commission cannot be -ve, Please enter a positive value\n";
    loadFileDone = "Please place the csv at the location:\n"
            + "C:\\Users\\anush\\OneDrive\\Desktop\\CS5010_PDP_Projects\\Assignment5\\"
            + "resources\\testPortfolio\\FixedPortfolios\n" +
            "\tHas the file been placed at the above location? 1.Yes 0.No";
    loadedFixed = "Following are the fixed portfolios created till now:\n"
            + "1 testFixed1.csv\n" + "2 testFixedPortfolio1.csv\n" + "3 testLoad1.csv\n"
            + "Pick a portfolio\n";
    loadedPf = "Following stocks are present in the portfolio : \n"
            + "TickerName\tNumberOfUnits\tTransactionDate\tCommission(USD)\tPrice(USD)\tBUY/SELL\n"
            + "MSFT\t\t50.0\t\t\t2022-11-17\t\t0.0\t\t\t241.68\t\t\tBUY\n"
            + "AAPL\t\t50.0\t\t\t2022-11-17\t\t0.0\t\t\t150.72\t\t\tBUY\n"
            + "GOOG\t\t25.0\t\t\t2022-11-17\t\t0.0\t\t\t98.5\t\t\tBUY\n";
    loadedFromFile = "TSLA\t\t10.0\t\t\t2022-11-16\t\t0.0\t\t\t194.42\t\t\tBUY\n"
            + "MSFT\t\t50.0\t\t\t2022-11-16\t\t0.0\t\t\t241.97\t\t\tBUY\n"
            + "GOOG\t\t100.0\t\t\t2022-11-16\t\t0.0\t\t\t98.72\t\t\tBUY\n"
            + "AAPL\t\t40.0\t\t\t2022-11-16\t\t0.0\t\t\t150.04\t\t\tBUY\n"
            + "V\t\t75.0\t\t\t2022-11-16\t\t0.0\t\t\t209.99\t\t\tBUY\n";
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
    log.append(menu+portfolioOptions+displayListOfFlexiblePortfolios+valueDate
            +flexibleValue+menu+exit);
    input = new ByteArrayInputStream("3 2 1 2022-11-16 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testCreateANewFixedPortfolio() {
    log = new StringBuilder();
    log.append(menu+portfolioOptions+createOptions+nameForCreate+enterTickerName+enterUnits
            +marketStringForFixed+previousDay+addMoreStocks+enterTickerName+enterUnits
            +marketStringForFixed+previousDay+addMoreStocks+portfolioSaved+menu+exit);
    input = new ByteArrayInputStream("1 1 1 testCreate1 GOOG 50 1 MSFT 30 0 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    File file = new File("./resources/testPortfolio/FixedPortfolios/testCreate1.csv");
    file.delete();
    assertEquals(log.toString(), out.toString());

  }

  @Test
  public void testCreateANewFlexiblePortfolio() {
    log = new StringBuilder();
    log.append(menu+portfolioOptions+createOptions+nameForCreate+enterTickerName+enterUnits
            + askCom + txnDate
            +flexStockDate1+addMoreStocks+enterTickerName+enterUnits + askCom+txnDate
            +flexStockDate2+addMoreStocks+portfolioSaved+menu+exit);
    input = new ByteArrayInputStream(("1 2 1 testCreate2 GOOG 50 10 2022-11-10 1 MSFT 40 "
            + "10 2022-11-09 0 7").getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
    File file = new File("./resources/testPortfolio/FlexiblePortfolios/testCreate2.csv");
    file.delete();
  }


  @Test
  public void testWeeklyChart() {
    log = new StringBuilder();
    log.append(menu + portfolioOptions + displayListOfFixedPortfolios + optionsForGraph
    + fixedWeeklyGraph + menu + exit);
    input = new ByteArrayInputStream("6 1 1 1 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testMonthlyChart() {
    log = new StringBuilder();
    log.append(menu + portfolioOptions + displayListOfFixedPortfolios + optionsForGraph
            + fixedMonthlyGraph + menu + exit);
    input = new ByteArrayInputStream("6 1 1 2 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testYearlyChart() {
    log = new StringBuilder();
    log.append(menu + portfolioOptions + displayListOfFixedPortfolios + optionsForGraph
            + fixedYearlyGraph + menu + exit);
    input = new ByteArrayInputStream("6 1 1 3 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testSellMore() {
    log = new StringBuilder();
    log.append(menu + portfolioOptions + displayListOfFlexiblePortfolios + dateFromUser
            +testFlexiblePortfolio1+ menu +addSellList + addOrSell + enterTickerName + unitsToSell
            + costBasisDate +askCom + cannotSell + menu + exit);
    input = new ByteArrayInputStream("2 2 1 2022-11-17 4 1 2 V 30 2022-11-17 10 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testSellBeforeBuy() {
    log = new StringBuilder();
    log.append(menu + portfolioOptions + displayListOfFlexiblePortfolios + dateFromUser
            +testFlexiblePortfolio1+ menu +addSellList + addOrSell + enterTickerName + unitsToSell
            + costBasisDate +askCom + cannotSell + menu + exit);
    input = new ByteArrayInputStream("2 2 1 2022-11-17 4 1 2 V 20 2022-10-10 10 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
  }

  @Test
  public void testFutureBuy() {
    log = new StringBuilder();
    log.append(menu + portfolioOptions + createOptions + nameForCreate + enterTickerName
            + enterUnits + askCom + txnDate + wrongDate + txnDate + furtureBuy + addMoreStocks
            + portfolioSaved + menu + exit);
    input = new ByteArrayInputStream(("1 2 1 testFutureBuy NVDA 30 10 2022-11-25 2022-11-16 "
            + "0 7").getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
    File file = new File("./resources/testPortfolio/FlexiblePortfolios/testFutureBuy.csv");
    file.delete();
  }

  @Test
  public void testNegativeComm() {
    log = new StringBuilder();
    log.append(menu + portfolioOptions + createOptions + nameForCreate + enterTickerName
            + enterUnits + askCom + wrongCommission + txnDate + furtureBuy + addMoreStocks
            + portfolioSaved + menu + exit);
    input = new ByteArrayInputStream("1 2 1 NegCom AAPL 50 -10 10 2022-11-16 0 7".getBytes());
    ICommandController controller = new CommandController(u, view, input);
    controller.go();
    assertEquals(log.toString(), out.toString());
    File file = new File("./resources/testPortfolio/FlexiblePortfolios/NegCom.csv");
    file.delete();
  }

  @Test
  public void testLoadFromFile() {

    String line = null;
    StringBuilder finalString = new StringBuilder();
    try {
      BufferedReader br = new BufferedReader(new FileReader("resources/testPortfolio"
              + "/FixedPortfolios/testFixed1.csv"));
      while ((line = br.readLine()) != null) {
        finalString.append(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(finalString.toString().replaceAll(",",""),
            loadedFromFile.toString().replaceAll("\n","")
                    .replaceAll("\t", ""));

  }







}