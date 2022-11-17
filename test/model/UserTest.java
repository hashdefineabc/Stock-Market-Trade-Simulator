package model;


import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class to check the user functionalities.
 */
public class UserTest {

  User user;

  @Before
  public void setUp() {
    user = new User("./resources/testPortfolio");
  }

  @Test
  public void testStocks() {
    IstockModel stock = new Stock("AAPL", 5.0, 10.0, 2000.0, LocalDate.now(), Operation.BUY);
    assertEquals("AAPL", stock.getTickerName());
  }

  @Test
  public void testStocksNumUnits() {
    IstockModel stock = new Stock("AAPL", 5.0, 10.0, 2000.0, LocalDate.now(), Operation.BUY);
    assertEquals("5.0", stock.getNumOfUnits().toString());
  }

  @Test
  public void testIsTickerValid() {
    List<IstockModel> stockList = new ArrayList<>();
    IstockModel s = new Stock("AAPL", 5.0, 10.0, 2000.0, LocalDate.now(), Operation.BUY);
    stockList.add(s);
    s = new Stock("MSFT", 5.0, 10.0, 2000.0, LocalDate.now(), Operation.BUY);
    stockList.add(s);
    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);

    assertEquals(true, user.isTickerValid("AAPL"));
  }

  @Test
  public void testIsTickerInvalid() {
    assertEquals(false, user.isTickerValid("zzz"));
  }

  @Test
  public void testGetFixedPortfoliosCreated() {
    String expected = "[testFixedPortfolio1.csv, testFixed1.csv]";
    assertEquals(expected, user.getPortfolioNamesCreated(PortfolioType.fixed).toString());
  }

  @Test
  public void testGetFlexiblePortfoliosCreated() {
    String expected = "[testFlexiblePortfolio1.csv, testFlex1.csv]";
    assertEquals(expected, user.getPortfolioNamesCreated(PortfolioType.flexible).toString());
  }
  @Test
  public void testSavePortFolio() {
    assertEquals(true, user.checkIfFileExists("testFlex1", PortfolioType.flexible));
  }


}