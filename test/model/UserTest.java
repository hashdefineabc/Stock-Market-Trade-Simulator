package model;


import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserTest {


  IUserInterface testUser;

  @Before
  public void setUp() {
    String userDirectory = new File("").getAbsolutePath();
    String folderPath = userDirectory + File.separator + "testPortfolio";
    File file = new File(folderPath);
    if (!Files.exists(Path.of(folderPath))) {
      file.mkdir();
    }
    List<String[]> stockList = new ArrayList<>();
    String[] s = new String[]{"AAPL", "5", LocalDate.of(2022, 10, 26).toString()};
    stockList.add(s);
    s = new String[]{"MSFT", "5", LocalDate.of(2022, 10, 26).toString()};
    stockList.add(s);
    testUser = new User("testUser");
    testUser.setFolderPath(folderPath);
    testUser.createPortfolioManually("Portfolio 1", stockList);
  }


  @Test
  public void testStocks() {
    IstockModel stock = new Stock("AAPL", 5, LocalDate.now());
//    assertEquals();
  }

  @Test
  public void testIsTickerValid() {
    IUserInterface user = new User("new user");
    List<IstockModel> stockList = new ArrayList<>();
    Stock s = new Stock("AAPL", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    s = new Stock("MSFT", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    PortfolioModel portfolio = new Portfolio("Portfolio 1", stockList);

    user.CreateNewPortfolio(portfolio);
    assertEquals(true, user.isTickerValid("AAPL"));
  }

  @Test
  public void testCreateNewPortfolio() {

    IUserInterface user = new User("User 1");
    List<IstockModel> stockList = new ArrayList<>();
    Stock s = new Stock("AAPL", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    s = new Stock("MSFT", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    PortfolioModel portfolio = new Portfolio("Portfolio 1", stockList);

    List<String> portfoliosCreated = user.getPortfolioNamesCreated();
    int previousSize =  portfoliosCreated.size();
    user.CreateNewPortfolio(portfolio);
    portfoliosCreated = user.getPortfolioNamesCreated();
    assertEquals(previousSize+1, portfoliosCreated.size());

  }

  @Test
  public void testGetPortfoliosCreated() {
    System.out.println(testUser.getPortfolioNamesCreated());
  }

  @Test
  public void testPortfolioComposition() {

  }

}