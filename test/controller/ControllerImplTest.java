//package controller;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import model.FixedPortfolio;
//import model.IFlexiblePortfolio;
//import model.IUserInterface;
//import model.IstockModel;
//import model.IFixedPortfolio;
//import model.PortfolioType;
//import model.Stock;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Class to test all the functions of the controller.
// */
//public class ControllerImplTest {
//
//  StringBuilder log;
//  IUserInterface user;
//
//  @Before
//  public void setUp() {
//    log = new StringBuilder();
//    user = new MockModel(log);
//  }
//
//  class MockModel implements IUserInterface {
//
//    private StringBuilder log;
//
//    public MockModel(StringBuilder log) {
//      this.log = log;
//    }
//
//    public void loadExistingPortFolios() {
//      log.append("loadExistingPortFolios method is called");
//    }
//
//    public void createPortFolioFromFile() {
//      log.append("createPortFolioFromFile method is called");
//    }
//
//    public void CreateNewPortfolio(IFixedPortfolio newPortfolio) {
//      log.append("CreateNewPortfolio method is called with " + newPortfolio.getNameOfPortFolio());
//    }
//
//    public List<String> getPortfolioNamesCreated() {
//      log.append("getPortfolioNamesCreated method is called");
//      return null;
//    }
//
//    public List<IFixedPortfolio> getPortfoliosCreatedObjects() {
//      return null;
//    }
//
//
//    public Boolean checkIfFileExists(String fileName) {
//      log.append("fileName provided is : " + fileName);
//      return null;
//    }
//
//    public void savePortfolioToFile(IFixedPortfolio newPortfolio) {
//      log.append("savePortfolioToFile is provided with the portfolio: " +
//              newPortfolio.getNameOfPortFolio());
//    }
//
//    /**
//     * Method to load the existing portfolios created by this user.
//     *
//     * @param portfolioType
//     */
//    @Override
//    public void loadExistingPortFolios(PortfolioType portfolioType) {
//
//    }
//
//    @Override
//    public void createPortFolioFromFile(PortfolioType portfolioType) {
//
//    }
//
//    /**
//     * Method to get the names of the portfolios created.
//     *
//     * @param portfolioType
//     * @return the list of these portfolionames.
//     */
//    @Override
//    public List<String> getPortfolioNamesCreated(PortfolioType portfolioType) {
//      return null;
//    }
//
//    /**
//     * Method to get the list of portfolio objects created.
//     *
//     * @return the list of portfolios.
//     */
//    @Override
//    public List<IFixedPortfolio> getFixedPortfoliosCreatedObjects() {
//      return null;
//    }
//
//    @Override
//    public List<IFlexiblePortfolio> getFlexiblePortfoliosCreatedObjects() {
//      return null;
//    }
//
//    /**
//     * Method to check if a particular file exists in the system.
//     *
//     * @param fileName      the file name to check
//     * @param portfolioType
//     * @return if file is found or not.
//     */
//    @Override
//    public Boolean checkIfFileExists(String fileName, PortfolioType portfolioType) {
//      return null;
//    }
//
//    /**
//     * Method to save the created portfolio to a csv file.
//     *
//     * @param dataToWrite
//     * @param portfolioName
//     * @param portfolioType
//     */
//    @Override
//    public void savePortfolioToFile(List<String[]> dataToWrite, String portfolioName, PortfolioType portfolioType) {
//
//    }
//
//    @Override
//    public boolean isTickerValid(String tickerNameFromUser) {
//      log.append("tickerName provided = " + tickerNameFromUser);
//      return false;
//    }
//
//    /**
//     * Method to get the folder path where all the files will be stored.
//     *
//     * @return string = the intended folder path.
//     */
//    @Override
//    public String getFixedPFPath() {
//      return null;
//    }
//
//    @Override
//    public String getFlexPFPath() {
//      return null;
//    }
//
//    @Override
//    public List<IstockModel> displayStocksOfPortFolio(int portfolioIndex, PortfolioType typeofPortfolio, LocalDate dateForComposition) {
//      return null;
//    }
//
//    /**
//     * Method to calculate the value of a portfolio.
//     *
//     * @param portfolioIndex  the portfolio index for which we need to calculate the value
//     * @param date            the date for which we need to calculate the value.
//     * @param typeofPortfolio
//     * @return the Double total value of the portfolio.
//     */
//    @Override
//    public Double calculateValueOfPortfolio(int portfolioIndex, LocalDate date, PortfolioType typeofPortfolio) {
//      return null;
//    }
//
//    /**
//     * Method to create a portfolio manually by taking stock wise input.
//     *
//     * @param portfolioName   the portfolio name
//     * @param stockList       the stocks  in this portfolio.
//     * @param typeofPortfolio
//     * @return boolean = if this portfolio was created or not.
//     */
//    @Override
//    public boolean createNewPortfolio(String portfolioName, List<String[]> stockList, PortfolioType typeofPortfolio) {
//      return false;
//    }
//
//    @Override
//    public Double calculateCostBasisOfPortfolio(int portfolioIndex, PortfolioType portfolioType, LocalDate costBasisDate) {
//      return null;
//    }
//
//    @Override
//    public void addStocksToAPortfolio(int portfolioIndex) {
//
//    }
//
//    @Override
//    public void sellStocksFromAPortfolio(int portfolioIndex) {
//
//    }
//
//    @Override
//    public String getFolderPath() {
//
//      log.append("getFolderPath is called");
//      return null;
//    }
//
//    @Override
//    public List<String[]> displayStocksOfPortFolio(int portfolioIndex) {
//      log.append("displayStocksOfPortFolio is provided with " + portfolioIndex);
//      return null;
//    }
//
//    @Override
//    public Double calculateValueOfPortfolio(int portfolioIndex, LocalDate date) {
//      log.append("calculateValueOfPortfolio is provided with portfolioIndex = " + portfolioIndex +
//              " and date = " + date);
//      return 0.0;
//    }
//
//    @Override
//    public boolean createPortfolioManually(String portfolioName, List<String[]> stockList) {
//      log.append("createPortfolioManually is provided with " + portfolioName);
//      return false;
//    }
//
//    //todo
//
//    @Override
//    public void buySell() {
//
//    }
//
//    @Override
//    public void calculateCostBasis() {
//
//    }
//
//    @Override
//    public void displayChart() {
//
//    }
//
//    @Override
//    public Double getStockPriceFromDB(String tickerNameFromUser, LocalDate transactionDate) {
//      return null;
//    }
//
//    @Override
//    public Map<LocalDate, String> CalculateChart(int option, int portfolioIndexForVal, PortfolioType portfolioType) {
//      return null;
//    }
//
//    @Override
//    public Double getScale(int portfolioIndexForVal, PortfolioType portfolioType) {
//      return null;
//    }
//  }
//
//  @Test
//  public void testControllerUserModelConnection() {
//
//    String fileName = "p1";
//    String expectedResult = "fileName provided is : " + fileName;
//    user.checkIfFileExists(fileName);
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testTickerNameProvided() {
//    String tickerName = "AAPL";
//    String expectedResult = "tickerName provided = " + tickerName;
//    user.isTickerValid(tickerName);
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testLoadExistingPortFolios() {
//
//    user.loadExistingPortFolios();
//    String expectedResult = "loadExistingPortFolios method is called";
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testcreatePortFolioFromFile() {
//
//    user.createPortFolioFromFile();
//    String expectedResult = "createPortFolioFromFile method is called";
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testgetPortfolioNamesCreated() {
//    user.getPortfolioNamesCreated();
//    String expectedResult = "getPortfolioNamesCreated method is called";
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testgetPortfoliosCreated_Objects() {
//    user.getPortfolioNamesCreated();
//    String expectedResult = "getPortfoliosCreated_Objects method is called";
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testdisplayStocksOfPortFolio() {
//    user.displayStocksOfPortFolio(2);
//    String expectedResult = "displayStocksOfPortFolio is provided with " + 2;
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testgetFolderPath() {
//    user.getFolderPath();
//    String expectedResult = "getFolderPath is called";
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testcreatePortfolioManually() {
//    List<String[]> ls = new ArrayList<>();
//    user.createPortfolioManually("Portfolio 1", ls);
//    String expectedResult = "createPortfolioManually is provided with " + "Portfolio 1";
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testcalculateValueOfPortfolio() {
//    user.calculateValueOfPortfolio(2, LocalDate.of(2022, 10, 26));
//    String expectedResult = "calculateValueOfPortfolio is provided with portfolioIndex = " + "2" +
//            " and date = " + "2022-10-26";
//    assertEquals(expectedResult, log.toString());
//  }
//
//
//  @Test
//  public void testsavePortfolioToFile() {
//    List<IstockModel> stockList = new ArrayList<>();
//    Stock s = new Stock("AAPL", 5.0, commission, transactionPrice, LocalDate.of(2022, 10,
//            26), buyOrSell);
//    stockList.add(s);
//    s = new Stock("MSFT", 5.0, commission, transactionPrice, LocalDate.of(2022, 10,
//            26), buyOrSell);
//    stockList.add(s);
//    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);
//
//    user.savePortfolioToFile(portfolio);
//    String expectedResult = "savePortfolioToFile is provided with the portfolio: " +
//            portfolio.getNameOfPortFolio();
//    assertEquals(expectedResult, log.toString());
//  }
//
//  @Test
//  public void testCreateNewPortfolio() {
//    List<IstockModel> stockList = new ArrayList<>();
//    Stock s = new Stock("AAPL", 5.0,
//            commission, transactionPrice, LocalDate.of(2022, 10, 26), buyOrSell);
//    stockList.add(s);
//    s = new Stock("MSFT", 5.0,
//            commission, transactionPrice, LocalDate.of(2022, 10, 26), buyOrSell);
//    stockList.add(s);
//    IFixedPortfolio portfolio = new FixedPortfolio("Portfolio 1", stockList);
//
//    user.CreateNewPortfolio(portfolio);
//    String expectedResult = "CreateNewPortfolio method is called with " +
//            portfolio.getNameOfPortFolio();
//    assertEquals(expectedResult, log.toString());
//  }
//}
