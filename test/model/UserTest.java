package model;


import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    IstockModel stock = new Stock("AAPL", 5.0, 10.0,
            2000.0, LocalDate.now(), Operation.BUY);
    assertEquals("AAPL", stock.getTickerName());
  }

  @Test
  public void testStocksNumUnits() {
    IstockModel stock = new Stock("AAPL", 5.0, 10.0,
            2000.0, LocalDate.now(), Operation.BUY);
    assertEquals("5.0", stock.getNumOfUnits().toString());
  }

  @Test
  public void testIsTickerValid() {
    List<IstockModel> stockList = new ArrayList<>();
    IstockModel s = new Stock("AAPL", 5.0, 10.0, 2000.0,
            LocalDate.now(), Operation.BUY);
    stockList.add(s);
    s = new Stock("MSFT", 5.0, 10.0, 2000.0,
            LocalDate.now(), Operation.BUY);
    stockList.add(s);
    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);

    assertEquals(true, user.isTickerValid("AAPL"));
  }

  @Test
  public void testIsTickerInvalid() {
    assertEquals(false,
            user.isTickerValid("zzz"));
  }

  @Test
  public void testGetFixedPortfoliosCreated() {
    String expected = "[testFixedPortfolio1.csv, testFixed1.csv]";
    assertEquals(expected, user.getPortfolioNamesCreated(PortfolioType.fixed).toString());
  }

  @Test
  public void testGetFlexiblePortfoliosCreated() {
    String expected = "[CostBasis1.csv, testFlex1.csv, testFlexiblePortfolio1.csv]";
    assertEquals(expected, user.getPortfolioNamesCreated(PortfolioType.flexible).toString());
  }

  @Test
  public void testSavePortFolio() {
    assertEquals(true, user.checkIfFileExists("testFlex1", PortfolioType.flexible));
  }

  @Test
  public void testCostBasisForBeforeCreation_InvestByWeights() {
    assertEquals("0.0",user.calculateCostBasisOfPortfolio(1,
            PortfolioType.flexible,
            LocalDate.parse("2022-11-01")).toString());
  }

  @Test
  public void testCostBasisAfterPurchase_InvestByWeights() {
    assertEquals("4524.1",user.calculateCostBasisOfPortfolio(1,
            PortfolioType.flexible,
            LocalDate.parse("2022-11-23")).toString());
  }

  @Test
  public void testCostBasisForFuture_InvestByWeights() {
    assertEquals("5526.1",user.calculateCostBasisOfPortfolio(1,
            PortfolioType.flexible,
            LocalDate.parse("2022-12-21")).toString());
  }

  @Test
  public void testInvestStrategyBeforeToday() {
    List<String[]> stockList = new ArrayList<>();
    String[] stock = new String[] {"MSFT", "10.0", "2022-11-17", "2.0", "241.68", "BUY"};
    stockList.add(stock);
    user.createNewPortfolio("InvestTestBefore",stockList,PortfolioType.flexible);
    int pfIndex = 0;
    for (IFlexiblePortfolio flp:user.getFlexiblePortfoliosCreatedObjects()) {
      if (flp.getNameOfPortFolio().equals("InvestTestBefore")) {
        pfIndex = user.getFlexiblePortfoliosCreatedObjects().indexOf(flp) + 1;
      }
    }
    HashMap<String, Double> weights =  new HashMap<>();
    weights.put("GOOG",100.0);
    user.calculateTxns(LocalDate.parse("2022-10-17"), LocalDate.parse("2022-10-17"),
            0, weights, 2000.0, 2.0, pfIndex,
            InvestmentType.InvestByWeights);
    user.acceptStrategyFromUser(pfIndex,2000.0,2.0, LocalDate.parse("2022-10-17"),
            LocalDate.parse("2022-10-17"), weights, InvestmentType.InvestByWeights,
            0, LocalDate.parse("2022-10-17"));

    assertEquals("4335.62", user.calculateCostBasisOfPortfolio(pfIndex,
            PortfolioType.flexible, LocalDate.parse("2022-11-30")).toString());

    File file = new File("./resources/testPortfolio/FlexiblePortfolios/" +
            "InvestTestBefore.csv");
    file.delete();

    file = new File("./resources/testPortfolio/InvestmentInstructions/" +
            "InvestTestBefore");
    file.delete();

  }



}