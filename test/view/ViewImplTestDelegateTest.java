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
  class MockViewModel implements ViewInterface {

    private StringBuilder log;

    public MockViewModel(StringBuilder log) {
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
      log.append("List of portfolios provided is : ");
      for (String s : portfolios) {
        log.append(s);
      }
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
      log.append("List of stocks provided is : ");
      for (String[] s : listOfStocks) {
        log.append(s);
      }
    }

    /**
     * Method that tells the user to input the date for which he/she wishes to see the value of the
     * portfolio.
     */
    @Override
    public void getDateFromUser() {
      log.append("getDateFromUser method called.");
    }

    /**
     * Method to display the calculated value of the portfolio.
     *
     * @param val = value as calculated by model.
     */
    @Override
    public void displayValue(double val) {
      log.append("Value provided = " + val);
    }

    /**
     * Method to display any arbitrary msg to the user.
     *
     * @param msg = the msg passed by the controller.
     */
    @Override
    public void displayMsgToUser(String msg) {
      log.append("String provided to print = " + msg);
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


  }

  @Test
  public void displayListOfPortfolios() {

    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);

    List<String> portfolios = new ArrayList<>();
    portfolios.add("p1.csv");
    view.displayListOfPortfolios(portfolios);
    String expectedResult = "List of portfolios provided is : " + portfolios.get(0);

    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayListOfStocks() {

    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);

    List<String[]> stocks = new ArrayList<>();
    stocks.add(new String[]{"AAPL", "5", "2022-10-26"});
    view.displayStocks(stocks);
    String expectedResult = "List of stocks provided is : " + stocks.get(0);

    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayValue() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.displayValue(20);
    String expectedResult = "Value provided = " + 20.0;
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayMsgToUser() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.displayMsgToUser("Hey there");
    String expectedResult = "String provided to print = " + "Hey there";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayMenu() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.displayMenu();
    String expectedResult = "DisplayMenu method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTakeTickerName() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.takeTickerName();
    String expectedResult = "takeTickerName method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTakeNumOfUnits() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.takeNumOfUnits();
    String expectedResult = "takeNumOfUnits method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testAddMoreStocks() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.addMoreStocks();
    String expectedResult = "addMoreStocks method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetSelectedPortfolio() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.getSelectedPortfolio();
    String expectedResult = "getSelectedPortfolio method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetDateFromUser() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.getDateFromUser();
    String expectedResult = "getDateFromUser method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetPortfolioNameFromUser() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.getPortfolioNameFromUser();
    String expectedResult = "getPortfolioNameFromUser method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testDisplayCreatePortFolioOptions() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.displayCreatePortFolioOptions();
    String expectedResult = "displayCreatePortFolioOptions method called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testIsFileUploaded() {
    StringBuilder log = new StringBuilder();
    ViewInterface view = new MockViewModel(log);
    view.isFileUploaded();
    String expectedResult = "isFileUploaded method called.";
    assertEquals(expectedResult, log.toString());
  }


}