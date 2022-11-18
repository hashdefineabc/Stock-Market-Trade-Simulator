package controller;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import model.IFlexiblePortfolio;
import model.IUserInterface;
import model.IstockModel;
import model.IFixedPortfolio;
import model.PortfolioType;

import static org.junit.Assert.assertEquals;

/**
 * Class to test all the functions of the controller.
 */
public class ControllerImplDelegateTest {

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
    public void loadExistingPortFolios(PortfolioType portfolioType) {
      log.append("loadExistingPortFolios method is called.");
    }

    @Override
    public void createPortFolioFromFile(PortfolioType portfolioType) {
      log.append("createPortFolioFromFile method is called.");
    }

    @Override
    public List<String> getPortfolioNamesCreated(PortfolioType portfolioType) {
      log.append("getPortfolioNamesCreated method is called.");
      return null;
    }

    @Override
    public List<IFixedPortfolio> getFixedPortfoliosCreatedObjects() {
      log.append("getFixedPortfoliosCreatedObjects method is called.");
      return null;
    }

    @Override
    public List<IFlexiblePortfolio> getFlexiblePortfoliosCreatedObjects() {
      log.append("getFlexiblePortfoliosCreatedObjects method is called.");
      return null;
    }

    @Override
    public Boolean checkIfFileExists(String fileName, PortfolioType portfolioType) {
      log.append("checkIfFileExists method is called.");
      return null;
    }

    @Override
    public void savePortfolioToFile(List<String[]> dataToWrite, String portfolioName,
                                    PortfolioType portfolioType) {
      log.append("savePortfolioToFile method is called.");
    }

    @Override
    public boolean isTickerValid(String tickerNameFromUser) {
      log.append("isTickerValid method is called.");
      return false;
    }

    @Override
    public String getFixedPFPath() {
      log.append("getFixedPFPath method is called.");
      return null;
    }

    @Override
    public String getFlexPFPath() {
      log.append("getFlexPFPath method is called.");
      return null;
    }

    @Override
    public List<IstockModel> displayStocksOfPortFolio(int portfolioIndex,
                                                      PortfolioType typeofPortfolio,
                                                      LocalDate dateForComposition) {
      log.append("displayStocksOfPortFolio method is called.");
      return null;
    }

    @Override
    public Double calculateValueOfPortfolio(int portfolioIndex, LocalDate date,
                                            PortfolioType typeofPortfolio) {
      log.append("calculateValueOfPortfolio method is called.");
      return null;
    }

    @Override
    public boolean createNewPortfolio(String portfolioName, List<String[]> stockList,
                                      PortfolioType typeofPortfolio) {
      log.append("createNewPortfolio method is called.");
      return false;
    }

    @Override
    public Double calculateCostBasisOfPortfolio(int portfolioIndex, PortfolioType portfolioType,
                                                LocalDate costBasisDate) {
      log.append("calculateCostBasisOfPortfolio method is called.");
      return null;
    }


    @Override
    public Double getStockPriceFromDB(String tickerNameFromUser, LocalDate transactionDate) {
      log.append("getStockPriceFromDB method is called.");
      return null;
    }

    @Override
    public Map<LocalDate, String> calculateChart(int option, int portfolioIndexForVal,
                                                 PortfolioType portfolioType) {
      log.append("calculateChart method is called.");
      return null;
    }

    @Override
    public Double getScale(int portfolioIndexForVal, PortfolioType portfolioType) {
      log.append("getScale method is called.");
      return null;
    }

    @Override
    public String getPortfolioName(int portfolioIndex, PortfolioType portfolioType) {
      log.append("getPortfolioName method is called.");
      return null;
    }
  }

  @Test
  public void testControllerUserModelConnection() {

    String fileName = "p1";
    String expectedResult = "checkIfFileExists method is called.";
    user.checkIfFileExists(fileName,PortfolioType.fixed);
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTickerNameProvided() {
    String tickerName = "AAPL";
    String expectedResult = "isTickerValid method is called.";
    user.isTickerValid(tickerName);
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testLoadExistingPortFolios() {

    user.loadExistingPortFolios(PortfolioType.fixed);
    String expectedResult = "loadExistingPortFolios method is called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testcreatePortFolioFromFile() {

    user.createPortFolioFromFile(PortfolioType.fixed);
    String expectedResult = "createPortFolioFromFile method is called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testgetPortfolioNamesCreated() {
    user.getPortfolioNamesCreated(PortfolioType.fixed);
    String expectedResult = "getPortfolioNamesCreated method is called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetFixedPortfoliosCreatedObjects() {
    user.getFixedPortfoliosCreatedObjects();
    String expectedResult = "getFixedPortfoliosCreatedObjects method is called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetFlexiblePortfoliosCreatedObjects() {
    user.getFlexiblePortfoliosCreatedObjects();
    String expectedResult = "getFlexiblePortfoliosCreatedObjects method is called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testdisplayStocksOfPortFolio() {
    user.displayStocksOfPortFolio(2, PortfolioType.fixed, LocalDate.now());
    String expectedResult = "displayStocksOfPortFolio method is called.";
    assertEquals(expectedResult, log.toString());
  }


  @Test
  public void testCalculateValueOfPortfolio() {
    user.calculateValueOfPortfolio(2, LocalDate.now(), PortfolioType.fixed);
    String expectedResult = "calculateValueOfPortfolio method is called.";
    assertEquals(expectedResult, log.toString());
  }


  @Test
  public void testsavePortfolioToFile() {
    user.savePortfolioToFile(null, null, PortfolioType.flexible);
    String expectedResult = "savePortfolioToFile method is called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testCreateNewPortfolio() {
    user.createNewPortfolio(null, null, PortfolioType.fixed);
    String expectedResult = "createNewPortfolio method is called.";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetFixedPFPath() {

    String expectedResult = "getFixedPFPath method is called.";
    user.getFixedPFPath();
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetFlexPFPath() {

    String expectedResult = "getFlexPFPath method is called.";
    user.getFlexPFPath();
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testCalculateCostBasisOfPortfolio() {

    String expectedResult = "calculateCostBasisOfPortfolio method is called.";
    user.calculateCostBasisOfPortfolio(2,PortfolioType.flexible, LocalDate.now());
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetStockPriceFromDB() {

    String expectedResult = "getStockPriceFromDB method is called.";
    user.getStockPriceFromDB(null, LocalDate.now());
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testCalculateChart() {

    String expectedResult = "CalculateChart method is called.";
    user.calculateChart(1,1,PortfolioType.flexible);
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetScale() {

    String expectedResult = "getScale method is called.";
    user.getScale(1, PortfolioType.flexible);
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testGetPortfolioName() {

    String expectedResult = "getPortfolioName method is called.";
    user.getPortfolioName(1, PortfolioType.flexible);
    assertEquals(expectedResult, log.toString());
  }

}
