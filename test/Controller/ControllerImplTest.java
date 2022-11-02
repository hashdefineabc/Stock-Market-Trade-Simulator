package Controller;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.IUserInterface;
import Model.IstockModel;
import Model.User;
import Model.portfolio;
import Model.portfolioModel;
import Model.stock;

import static org.junit.Assert.assertEquals;

public class ControllerImplTest {

  class MockUserModel implements IUserInterface {

    private StringBuilder log;
    public MockUserModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void loadExistingPortFolios() {

    }

    @Override
    public void createPortFolioFromFile() {

    }

    /**
     * creates a new portfolio
     *
     * @param newPortfolio
     */
    public void CreateNewPortfolio(portfolioModel newPortfolio) {
      log.append("newPortfolio received : " + newPortfolio.getNameOfPortFolio());
//      log.append("List of Stocks are ");
//      for(IstockModel stock: stocksList) {
//        log.append(stock.getTickerName()+" "+stock.getNumOfUnits()+" "+stock.getDate());
//      }

    }

    @Override
    public List<portfolioModel> getPortfoliosCreated() {
      return null;
    }

    @Override
    public Boolean checkIfFileExists(String fileName) {
      return null;
    }

    @Override
    public void savePortfolioToFile(portfolioModel newPortfolio) {

    }

    @Override
    public List<portfolioModel> getportfoliosList() {
      return null;
    }

    @Override
    public boolean isTickerValid(String tickerNameFromUser) {
      return false;
    }

    @Override
    public String getFolderPath() {
      return null;
    }
  }

  @Test
  public void testCreateNewPortfolio() {

    IstockModel newStock = new stock("AAPL", 5, LocalDate.now());
    List<IstockModel> stockList = new ArrayList<>();
    stockList.add(newStock);
    portfolioModel newPortfolioModel = new portfolio("p1", stockList);
    StringBuilder log = new StringBuilder();

    IUserInterface user = new MockUserModel(log);
    assertEquals("newPortfolio received : ", "");
  }


  class MockPortfolioModel implements portfolioModel{

    @Override
    public double valueOfPortfolio(LocalDate date) {
      return 0;
    }

    @Override
    public String getNameOfPortFolio() {
      return null;
    }

    @Override
    public List<String[]> toListOfString() {
      return null;
    }

    @Override
    public List<IstockModel> getStocks() {
      return null;
    }
  }

  class MockStockModel implements IstockModel{

    private StringBuilder log;

    public MockStockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public String getTickerName() {
      return null;
    }

    @Override
    public Integer getNumOfUnits() {
      return null;
    }

    @Override
    public LocalDate getDate() {
      return null;
    }

  }

  @Test
  public void testGetTickerName() {
    StringBuffer out = new StringBuffer();
    IstockModel istockModel = new stock("AAPL", 5, LocalDate.now());
    StringBuilder log = new StringBuilder(); //log for mock model
    MockStockModel mock = new MockStockModel(log);
    mock.getTickerName();
    assertEquals("Input: 3 4\nInput: 8 9\n", log.toString()); //inputs reached the model correctly
    assertEquals("1234321\n1234321\n",out.toString()); //output of model transmitted correctly
  }

}
