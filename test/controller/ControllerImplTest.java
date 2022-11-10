package Controller;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import model.IUserInterface;
import model.PortfolioModel;

import static org.junit.Assert.assertEquals;

public class ControllerImplTest {

  class MockModel implements IUserInterface {

    private StringBuilder log;
    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void loadExistingPortFolios() {

    }

    @Override
    public void createPortFolioFromFile() {

    }

    @Override
    public void CreateNewPortfolio(PortfolioModel newPortfolio) {

    }

    @Override
    public List<String> getPortfolioNamesCreated() {
      return null;
    }

    @Override
    public List<PortfolioModel> getPortfoliosCreated_Objects() {
      return null;
    }


    @Override
    public Boolean checkIfFileExists(String fileName) {
      log.append("fileName provided is : " + fileName);
      return null;
    }

    @Override
    public void savePortfolioToFile(PortfolioModel newPortfolio) {

    }

    @Override
    public boolean isTickerValid(String tickerNameFromUser) {
      log.append("tickerName provided = "+tickerNameFromUser);
      return false;
    }

    @Override
    public String getFolderPath() {
      return null;
    }

    @Override
    public List<String[]> displayStocksOfPortFolio(int portfolioIndex) {
      return null;
    }

    @Override
    public double calculateValueOfPortfolio(int portfolioIndex, LocalDate date) {
      return 0;
    }

    @Override
    public boolean createPortfolioManually(String portfolioName, List<String[]> stockList) {
      return false;
    }

    @Override
    public void setFolderPath(String folderPath) {

    }
  }

  @Test
  public void testControllerUserModelConnection() {

    String fileName = "p1";

    StringBuilder log = new StringBuilder();
    IUserInterface user = new MockModel(log);
    String expectedResult = "fileName provided is : " + fileName;
    user.checkIfFileExists(fileName);
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTickerNameProvided() {
    String tickerName = "AAPL";

    StringBuilder log = new StringBuilder();
    IUserInterface user = new MockModel(log);
    String expectedResult = "tickerName provided = " + tickerName;
    user.isTickerValid(tickerName);
    assertEquals(expectedResult, log.toString());
  }
}
