package view;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.IstockModel;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the view controller delegation.
 */
public class ViewImplTestDelegateTest {

  /**
   * Class to test the text user interface of this application.
   */
  class MockView implements ViewInterface {

    private StringBuilder log;

    public MockView(StringBuilder log) {
      this.log = log;
    }

    /**
     * Method to display the below options to the user.
     * 1. create new portfolio
     * 2. retrieve portfolio
     * 3. check value of a particular portfolio
     * 4. Exit the application
     */
    @Override
    public void displayMenu() {
      log.append("DisplayMenu method called.");
    }

    /**
     * Method to tell the user to input a ticker name.
     */
    @Override
    public void takeTickerName() {
      log.append("takeTickerName method called.");
    }

    /**
     * Method to tell the user to input the num of units bought for that particular share.
     */
    @Override
    public void takeNumOfUnits() {
      log.append("takeNumOfUnits method called.");
    }

    /**
     * Method that asks the user if they want to add more stocks to the portfolio.
     */
    @Override
    public void addMoreStocks() {
      log.append("addMoreStocks method called.");
    }

    /**
     * Method that displays the list of created portfolios.
     *
     * @param portfolios = list of portfolios as given by the controller.
     */
    @Override
    public void displayListOfPortfolios(List<String> portfolios) {
      log.append("displayListOfPortfolios method called.");
    }

    /**
     * Method that displays list of portfolios and asks user to select one.
     */
    @Override
    public void getSelectedPortfolio() {
      log.append("getSelectedPortfolio method called.");
    }

    /**
     * Method that displays stocks of a particular portfolio.
     *
     * @param listOfStocks list of stocks
     */
    @Override
    public void displayStocks(List<IstockModel> listOfStocks) {
      log.append("displayStocks method called.");
    }

    /**
     * Method that tells the user to input the date for which he/she wishes to see the value of the
     * portfolio.
     */
    @Override
    public void getDateFromUser() {
      log.append("getDateFromUser method called.");
    }

    @Override
    public void displayValue(double val, LocalDate valueDate) {
      log.append("displayValue method called.");
    }

    /**
     * Method to display any arbitrary msg to the user.
     *
     * @param msg = the msg passed by the controller.
     */
    @Override
    public void displayMsgToUser(String msg) {
      log.append("displayMsgToUser method called.");
    }

    /**
     * Method to get the portfolio name from the user.
     */
    @Override
    public void getPortfolioNameFromUser() {
      log.append("getPortfolioNameFromUser method called.");
    }

    /**
     * Method to display the following two options of creating a portfolio.
     * 1. Enter the stock details manually
     * 2. Upload an existing file
     */
    @Override
    public void displayCreatePortFolioOptions() {
      log.append("displayCreatePortFolioOptions method called.");
    }

    /**
     * Method to check if the file to create a portfolio has been uploaded by the user.
     */
    @Override
    public void isFileUploaded() {
      log.append("isFileUploaded method called.");
    }

    @Override
    public void chooseFixedOrFlexible() {
      log.append("chooseFixedOrFlexible method called.");
    }

    @Override
    public void takeCommissionValue() {
      log.append("takeCommissionValue method called.");
    }

    @Override
    public void displayCostBasis(double costBasis, LocalDate costBasisDate) {
      log.append("displayCostBasis method called.");
    }

    @Override
    public void askAddOrSell() {
      log.append("askAddOrSell method called.");
    }

    @Override
    public void displayOptionsForChart() {
      log.append("displayOptionsForChart method called.");
    }

    @Override
    public void displayChartWeek(Map<LocalDate, String> chart) {
      log.append("displayChartWeek method called.");
    }

    @Override
    public void displayChartMonth(Map<LocalDate, String> chart) {
      log.append("displayChartMonth method called.");
    }
  }

  @Test
  public void displayListOfPortfolios() {

    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);

    List<String> portfolios = new ArrayList<>();
    view.displayListOfPortfolios(portfolios);
    String expectedResult = "displayListOfPortfolios method called.";

    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayListOfStocks() {

    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    List<IstockModel> stocks = new ArrayList<>();
    view.displayStocks(stocks);
    String expectedResult = "displayStocks method called.";

    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayValue() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    LocalDate testDate = LocalDate.now();
    view.displayValue(20.00, testDate);
    String expectedResult = "displayValue method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayMsgToUser() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.displayMsgToUser("Display msg");
    String expectedResult = "displayMsgToUser method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayMenu() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.displayMenu();
    String expectedResult = "DisplayMenu method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTakeTickerName() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.takeTickerName();
    String expectedResult = "takeTickerName method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTakeNumOfUnits() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.takeNumOfUnits();
    String expectedResult = "takeNumOfUnits method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testAddMoreStocks() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.addMoreStocks();
    String expectedResult = "addMoreStocks method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetSelectedPortfolio() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.getSelectedPortfolio();
    String expectedResult = "getSelectedPortfolio method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetDateFromUser() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.getDateFromUser();
    String expectedResult = "getDateFromUser method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetPortfolioNameFromUser() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.getPortfolioNameFromUser();
    String expectedResult = "getPortfolioNameFromUser method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayCreatePortFolioOptions() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.displayCreatePortFolioOptions();
    String expectedResult = "displayCreatePortFolioOptions method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testIsFileUploaded() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.isFileUploaded();
    String expectedResult = "isFileUploaded method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testChooseFixedOrFlexible() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.isFileUploaded();
    String expectedResult = "chooseFixedOrFlexible method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTakeCommissionValue() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.isFileUploaded();
    String expectedResult = "takeCommissionValue method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayCostBasis() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.isFileUploaded();
    String expectedResult = "displayCostBasis method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testAskAddOrSell() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.isFileUploaded();
    String expectedResult = "askAddOrSell method called..";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testdisplayOptionsForChart() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.isFileUploaded();
    String expectedResult = "displayOptionsForChart method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testdisplayChartWeek() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.isFileUploaded();
    String expectedResult = "displayChartWeek method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayChartMonth() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockView(log);
    view.isFileUploaded();
    String expectedResult = "displayChartMonth method called.";
    assertEquals(expectedResult, log.toString());
  }




}