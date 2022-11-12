package controller;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.FixedPortfolio;
import model.IUserInterface;
import model.IstockModel;
import model.IFixedPortfolio;
import model.Stock;

import static org.junit.Assert.assertEquals;

/**
 * Class to test all the functions of the controller.
 */
public class ControllerImplTest {

  StringBuilder log;
  IUserInterface user;

  @Before
  public void setUp() {
    log = new StringBuilder();
    user = new MockModel(log);
  }

  class MockModel implements IUserInterface {

    private StringBuilder log;

    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void loadExistingPortFolios() {
      log.append("loadExistingPortFolios method is called");
    }

    @Override
    public void createPortFolioFromFile() {
      log.append("createPortFolioFromFile method is called");
    }

    @Override
    public void CreateNewPortfolio(IFixedPortfolio newPortfolio) {
      log.append("CreateNewPortfolio method is called with " + newPortfolio.getNameOfPortFolio());
    }

    @Override
    public List<String> getPortfolioNamesCreated() {
      log.append("getPortfolioNamesCreated method is called");
      return null;
    }

    @Override
    public List<IFixedPortfolio> getPortfoliosCreatedObjects() {
      return null;
    }


    @Override
    public Boolean checkIfFileExists(String fileName) {
      log.append("fileName provided is : " + fileName);
      return null;
    }

    @Override
    public void savePortfolioToFile(IFixedPortfolio newPortfolio) {
      log.append("savePortfolioToFile is provided with the portfolio: " +
              newPortfolio.getNameOfPortFolio());
    }

    @Override
    public boolean isTickerValid(String tickerNameFromUser) {
      log.append("tickerName provided = " + tickerNameFromUser);
      return false;
    }

    @Override
    public String getFolderPath() {

      log.append("getFolderPath is called");
      return null;
    }

    @Override
    public List<String[]> displayStocksOfPortFolio(int portfolioIndex) {
      log.append("displayStocksOfPortFolio is provided with " + portfolioIndex);
      return null;
    }

    @Override
    public double calculateValueOfPortfolio(int portfolioIndex, LocalDate date) {
      log.append("calculateValueOfPortfolio is provided with portfolioIndex = " + portfolioIndex +
              " and date = " + date);
      return 0;
    }

    @Override
    public boolean createPortfolioManually(String portfolioName, List<String[]> stockList) {
      log.append("createPortfolioManually is provided with " + portfolioName);
      return false;
    }

    //todo

    @Override
    public void buySell() {

    }

    @Override
    public void calculateCostBasis() {

    }

    @Override
    public void displayChart() {

    }
  }

  @Test
  public void testControllerUserModelConnection() {

    String fileName = "p1";
    String expectedResult = "fileName provided is : " + fileName;
    user.checkIfFileExists(fileName);
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTickerNameProvided() {
    String tickerName = "AAPL";
    String expectedResult = "tickerName provided = " + tickerName;
    user.isTickerValid(tickerName);
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testLoadExistingPortFolios() {

    user.loadExistingPortFolios();
    String expectedResult = "loadExistingPortFolios method is called";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testcreatePortFolioFromFile() {

    user.createPortFolioFromFile();
    String expectedResult = "createPortFolioFromFile method is called";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testgetPortfolioNamesCreated() {
    user.getPortfolioNamesCreated();
    String expectedResult = "getPortfolioNamesCreated method is called";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testgetPortfoliosCreated_Objects() {
    user.getPortfolioNamesCreated();
    String expectedResult = "getPortfoliosCreated_Objects method is called";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testdisplayStocksOfPortFolio() {
    user.displayStocksOfPortFolio(2);
    String expectedResult = "displayStocksOfPortFolio is provided with " + 2;
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testgetFolderPath() {
    user.getFolderPath();
    String expectedResult = "getFolderPath is called";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testcreatePortfolioManually() {
    List<String[]> ls = new ArrayList<>();
    user.createPortfolioManually("Portfolio 1", ls);
    String expectedResult = "createPortfolioManually is provided with " + "Portfolio 1";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testcalculateValueOfPortfolio() {
    user.calculateValueOfPortfolio(2, LocalDate.of(2022, 10, 26));
    String expectedResult = "calculateValueOfPortfolio is provided with portfolioIndex = " + "2" +
            " and date = " + "2022-10-26";
    assertEquals(expectedResult, log.toString());
  }


  @Test
  public void testsavePortfolioToFile() {
    List<IstockModel> stockList = new ArrayList<>();
    Stock s = new Stock("AAPL", 5.0, LocalDate.of(2022, 10,
            26));
    stockList.add(s);
    s = new Stock("MSFT", 5.0, LocalDate.of(2022, 10,
            26));
    stockList.add(s);
    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);

    user.savePortfolioToFile(portfolio);
    String expectedResult = "savePortfolioToFile is provided with the portfolio: " +
            portfolio.getNameOfPortFolio();
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testCreateNewPortfolio() {
    List<IstockModel> stockList = new ArrayList<>();
    Stock s = new Stock("AAPL", 5.0,
            LocalDate.of(2022, 10, 26));
    stockList.add(s);
    s = new Stock("MSFT", 5.0,
            LocalDate.of(2022, 10, 26));
    stockList.add(s);
    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);

    user.CreateNewPortfolio(portfolio);
    String expectedResult = "CreateNewPortfolio method is called with " +
            portfolio.getNameOfPortFolio();
    assertEquals(expectedResult, log.toString());
  }
}
