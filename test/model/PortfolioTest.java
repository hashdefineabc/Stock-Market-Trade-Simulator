package model;

import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class to test how a portfolio works.
 */
public class PortfolioTest {

  @Test
  public void testCreateNewFixedPortfolio() {
    User user = new User("./resources/testPortfolio");
    List<String[]> stockList = new ArrayList<>();

    String[] stock = new String[] {"AAPL", "100.0", "2022-11-15", "0.0", "1000", "BUY"};
    stockList.add(stock);
    stock = new String[]{"MSFT", "200.0", "2022-10-26", "0.0", "2000", "BUY"};
    stockList.add(stock);
    user.createNewPortfolio("testFixedPortfolio1", stockList, PortfolioType.fixed);

    File newFixedPortfolioPath = new File("./resources/testPortfolio/FixedPortfolios/testFixedPortfolio1.csv");
    boolean exists = newFixedPortfolioPath.exists();
    assertEquals(true, exists);

  }

  @Test
  public void testCreateNewFlexiblePortfolio() {
    User user = new User("./resources/testPortfolio");
    List<String[]> stockList = new ArrayList<>();

    String[] stock = new String[] {"AAPL", "100.0", "2022-11-15", "10.0", "1000", "BUY"};
    stockList.add(stock);
    stock = new String[]{"MSFT", "200.0", "2022-10-26", "20.0", "2000", "BUY"};
    stockList.add(stock);
    user.createNewPortfolio("testFlexiblePortfolio1", stockList, PortfolioType.flexible);

    File newFixedPortfolioPath = new File("./resources/testPortfolio/FlexiblePortfolios/testFlexiblePortfolio1.csv");
    boolean exists = newFixedPortfolioPath.exists();
    assertEquals(true, exists);

  }

  /**
   * Test value of portfolio.
   */
  @Test
  public void testValueOfPortfolio() {
    List<IstockModel> stockList = new ArrayList<>();

    Stock s = Stock.getBuilder()
            .tickerName("AAPL")
            .numOfUnits(Double.valueOf(100))
            .transactionDate(LocalDate.parse("2022-11-15"))
            .commission(Double.valueOf(10))
            .transactionPrice(Double.valueOf(1000))
            .buyOrSell(Operation.valueOf("BUY"))
            .build();

    stockList.add(s);
    s = new Stock("MSFT", 5.0, 10.0, 1000.0, LocalDate.of(2022, 10, 26), Operation.BUY);
    stockList.add(s);
    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);
    assertEquals("1857.75", String.valueOf(portfolio.calculateValue(LocalDate.of(2022, 10, 27))));
  }

  /**
   * Test value of empty portfolio.
   */
  @Test
  public void testValueOfEmptyPortfolio() {
    List<IstockModel> stockList = new ArrayList<>();
    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);
    assertEquals("0.0", String.valueOf(portfolio.calculateValue(LocalDate.of(2022, 10, 27))));
  }

  /**
   * Test get name of port folio.
   */
  @Test
  public void testGetNameOfPortFolio() {
    List<IstockModel> stockList = new ArrayList<>();
    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);
    assertEquals("Portfolio 1", portfolio.getNameOfPortFolio());
  }

  /**
   * Create portfolio without a name throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetNameOfPortFolioEmpty() {
    List<IstockModel> stockList = new ArrayList<>();
    IFixedPortfolio portfolio = new FixedPortfolio("", stockList);
  }

  /**
   * Create portfolio without stocks.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCreatePortfolioWithEmptyStocks() {
    List<IstockModel> stockList = new ArrayList<>();
    IFixedPortfolio portfolio = new FixedPortfolio("", stockList);
  }

  /**
   * Create portfolio.
   */
  @Test
  public void testCreatePortfolio() {
    List<IstockModel> stockList = new ArrayList<>();
    IUserInterface user = new User();
//    Stock s = new Stock("AAPL", 5.0, commission, transactionPrice, LocalDate.of(2022, 10, 26), buyOrSell);
//    stockList.add(s);
//    s = new Stock("MSFT", 25.0, commission, transactionPrice, LocalDate.of(2022, 10, 26), buyOrSell);
//    stockList.add(s);
    int previous = user.getPortfolioNamesCreated(PortfolioType.fixed).size();
    IFixedPortfolio portfolio = new FixedPortfolio("", stockList);
    assertEquals(previous + 1, user.getPortfolioNamesCreated(PortfolioType.fixed).size());
  }

  /**
   * Test to list of string.
   */
  @Test
  public void testToListOfString() {
    List<IstockModel> stockList = new ArrayList<>();
//    Stock s = new Stock("AAPL", 5.0, commission, transactionPrice, LocalDate.of(2022, 10, 26), buyOrSell);
//    stockList.add(s);
//    s = new Stock("MSFT", 25.0, commission, transactionPrice, LocalDate.of(2022, 10, 26), buyOrSell);
//    stockList.add(s);
    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);

    List<String[]> expectedList = new ArrayList<>();
    String[] stock = {"AAPL", "5", "2022-10-26"};
    expectedList.add(stock);
    String[] stock2 = {"MSFT", "25", "2022-10-26"};
    expectedList.add(stock2);

    assertEquals("AAPL", portfolio.toListOfString().get(0)[0]);
    assertEquals("5", portfolio.toListOfString().get(0)[1]);
    assertEquals("2022-10-26", portfolio.toListOfString().get(0)[2]);
    assertEquals("MSFT", portfolio.toListOfString().get(1)[0]);
    assertEquals("25", portfolio.toListOfString().get(1)[1]);
    assertEquals("2022-10-26", portfolio.toListOfString().get(1)[2]);
  }

}