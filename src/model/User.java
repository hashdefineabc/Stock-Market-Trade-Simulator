package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to implement the user's functionality for the stock market application.
 */
public class User implements IUserInterface {

  List<String> nasdaqTickerNames;
  private String folderPath;
  private String fixedPFPath;
  private String flexiblePFPath;
  private File file;

  private List<IFixedPortfolio> fixedPortfolios;
  private List<IFlexiblePortfolio> flexiblePortfolios;


  /**
   * Constructor for user class.
   * it takes username and initializes the fields of this class
   */

  public User() {

    this.fixedPortfolios = new ArrayList<>();
    this.flexiblePortfolios = new ArrayList<>();
    nasdaqTickerNames = new ArrayList<>();

    String userDirectory = new File("").getAbsolutePath();
    this.folderPath = userDirectory + File.separator + "PortFolioComposition";
    this.fixedPFPath = this.folderPath + File.separator + "FixedPortfolios";
    this.flexiblePFPath = this.folderPath + File.separator + "FlexiblePortfolios";

    file = new File(folderPath);
    this.createFolder();
    this.loadExistingPortFolios("fixed");
    this.loadExistingPortFolios("flexible");
    try {
      this.loadNasdaqTickerNames();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void loadNasdaqTickerNames() throws FileNotFoundException {
    try {
      BufferedReader csvReader = new BufferedReader(new FileReader("./resources/Nasdaq_top25.csv"));
      String row = "";
      row = csvReader.readLine();
      do {
        row = row.strip().split(",")[0];
        this.nasdaqTickerNames.add(row);
        row = csvReader.readLine();
      } while (row != null);
      csvReader.close();
      } catch (Exception e) {
        throw new FileNotFoundException("Nasdaq ticker names file not found");
    }
  }

  @Override
  public boolean isTickerValid(String tickerName) {
    return this.nasdaqTickerNames.contains(tickerName);
  }

  @Override
  public String getFixedPFPath() {
    return this.fixedPFPath;
  }

  @Override
  public String getFlexPFPath() {
    return this.flexiblePFPath;
  }



  @Override
  public boolean createNewPortfolio(String portfolioName, List<String[]> stockList, String typeofPortfolio) {
    List<String[]> dataToWrite = null;
    List<IstockModel> stockListToAdd = new ArrayList<>();

    if (typeofPortfolio.equals("fixed")) {

      for (String[] singleStock : stockList) {
        Stock newStock = Stock.getBuilder()
                .tickerName(singleStock[0])
                .numOfUnits(Double.valueOf(singleStock[1]))
                .transactionDate(LocalDate.parse(singleStock[2])).build();

        stockListToAdd.add(newStock);
      }

      IFixedPortfolio newFixedPortfolio = new FixedPortfolio(portfolioName, stockListToAdd);
      this.fixedPortfolios.add(newFixedPortfolio);
      dataToWrite = newFixedPortfolio.toListOfString();
      this.savePortfolioToFile(dataToWrite, newFixedPortfolio.getNameOfPortFolio(),typeofPortfolio);
    }
    else if (typeofPortfolio.equals("flexible")) {

      for (String[] singleStock : stockList) {
        Stock newStock = Stock.getBuilder()
                .tickerName(singleStock[0])
                .numOfUnits(Double.valueOf(singleStock[1]))
                .commission(Double.valueOf(singleStock[2]))
                .transactionPrice(Double.valueOf(singleStock[3]))
                .transactionDate(LocalDate.parse(singleStock[4])).build();

        stockListToAdd.add(newStock);
      }

      IFlexiblePortfolio newFlexPortfolio = new FlexiblePortfolio(portfolioName, stockListToAdd);
      this.flexiblePortfolios.add(newFlexPortfolio);
      dataToWrite = newFlexPortfolio.toListOfString();
      this.savePortfolioToFile(dataToWrite, newFlexPortfolio.getNameOfPortFolio(),typeofPortfolio);
    }

    return this.checkIfFileExists(portfolioName,typeofPortfolio);
  }

  @Override
  public Double calculateCostBasisOfPortfolio(int portfolioIndex, String portfolioType) {
    Double costBasis = 0.0;
    if(portfolioType.equals("fixed")) {
      IFixedPortfolio toCalcCostBasis = this.fixedPortfolios.get(portfolioIndex - 1);
      costBasis = toCalcCostBasis.calculateCostBasis();
    }
    else if (portfolioType.equals("flexible")) {
      IFlexiblePortfolio toCalcCostBasis = this.flexiblePortfolios.get(portfolioIndex - 1);
      costBasis = toCalcCostBasis.calculateCostBasis();
    }
    return costBasis;
  }


  @Override
  public void loadExistingPortFolios(String portfolioType) {
    List<String> fileNamesFromSystem =  new ArrayList<>();
    String filePath = null;
    if (portfolioType.equals("fixed")) {
      this.fixedPortfolios.clear();
      fileNamesFromSystem =  this.retrieveFileNames(portfolioType);
      filePath = this.fixedPFPath;
    }
    else if (portfolioType.equals("flexible")) {
      this.flexiblePortfolios.clear();
      fileNamesFromSystem =  this.retrieveFileNames(portfolioType);
      filePath = this.flexiblePFPath;
    }

    if (fileNamesFromSystem.size() == 0) {
      return;
    }

    for (String portfolioName : fileNamesFromSystem) { //take files from system.
      List<String[]> listOfStocks = this.readCSVFromSystem(filePath + File.separator + portfolioName);
      List<IstockModel> stockList = new ArrayList<>();

      if (portfolioType.equals("fixed")) {
        for (String[] stockDetails : listOfStocks) {
          Stock s = Stock.getBuilder() //TODO: check if we can replace with interface name
                  .tickerName(stockDetails[0])
                  .numOfUnits(Double.parseDouble(stockDetails[1]))
                  .transactionDate(LocalDate.parse(stockDetails[2]))
                  .build();
          stockList.add(s);
        }
        IFixedPortfolio fip = new FixedPortfolio(portfolioName, stockList); //create a portfolio object.
        this.fixedPortfolios.add(fip);
      }
      else if (portfolioType.equals("flexible")) {
        for (String[] stockDetails : listOfStocks) {
          Stock s = Stock.getBuilder() //TODO: check if we can replace with interface name
                  .tickerName(stockDetails[0])
                  .numOfUnits(Double.valueOf(stockDetails[1]))
                  .transactionDate(LocalDate.parse(stockDetails[2]))
                  .commission(Double.valueOf(stockDetails[3]))
                  .transactionPrice(Double.valueOf(stockDetails[4]))
                  .build();
          stockList.add(s);
        }
        IFlexiblePortfolio flp = new FlexiblePortfolio(portfolioName, stockList); //create a portfolio object.
        this.flexiblePortfolios.add(flp);
      }
    }
  }

  @Override
  public void createPortFolioFromFile(String portfolioType) {
      this.loadExistingPortFolios(portfolioType);
  }

  private List<String[]> readCSVFromSystem(String filePath) {

    List<String[]> listOfStocks = new ArrayList<String[]>();
    String line = "";
    String splitBy = ",";

    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      while ((line = br.readLine()) != null) {
        listOfStocks.add(line.split(splitBy));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return listOfStocks;
  }

  @Override
  public List<String> getPortfolioNamesCreated(String portFolioType) {

    List<String> portfolioNames = new ArrayList<>();

    if(portFolioType == "fixed"){
      this.loadExistingPortFolios(portFolioType);
      List<IFixedPortfolio> portfolioObjects = this.fixedPortfolios;
      for (IFixedPortfolio p : portfolioObjects) {
        portfolioNames.add(p.getNameOfPortFolio());
      }
    }
    else if(portFolioType == "flexible"){
      this.loadExistingPortFolios(portFolioType);
      List<IFlexiblePortfolio> portfolioObjects = this.flexiblePortfolios;
      for (IFlexiblePortfolio p : portfolioObjects) {
        portfolioNames.add(p.getNameOfPortFolio());
      }
    }

    return portfolioNames;
  }

  @Override
  public List<IFixedPortfolio> getFixedPortfoliosCreatedObjects() {
    return this.fixedPortfolios;
  }

  @Override
  public List<IFlexiblePortfolio> getFlexiblePortfoliosCreatedObjects() {
    return this.flexiblePortfolios;
  }



  private void createFolder() {
    //creating main folder
    file = new File(this.folderPath);
    if (!Files.exists(Path.of(this.folderPath))) {
      file.mkdir();
    }
    //creating fixed portfolios folder
    file = new File(this.fixedPFPath);
    if (!Files.exists(Path.of(this.fixedPFPath))) {
      file.mkdir();
    }
    //creating flexible portfolios folder
    file = new File(this.flexiblePFPath);
    if (!Files.exists(Path.of(this.flexiblePFPath))) {
      file.mkdir();
    }
  }

  private List<String> retrieveFileNames(String portfolioType) {
    List<String> fileNamesFromSystem = new ArrayList<>();
    if (portfolioType == "fixed") {
      file = new File(this.fixedPFPath);
    }
    else if (portfolioType == "flexible") {
      file = new File(this.flexiblePFPath);
    }
    String[] fileNames = file.list();
    for (String fileName : fileNames) {
      if (fileName.contains(".csv")) {
        fileNamesFromSystem.add(fileName);
      }
    }
    return fileNamesFromSystem;
  }

  @Override
  public Boolean checkIfFileExists(String fileName, String portfolioType) {
    List<String> fileNamesFromSystem = this.retrieveFileNames(portfolioType); // updating the fileNamesFromSystem list.
    return fileNamesFromSystem.contains(fileName + ".csv");
  }

  @Override
  public void savePortfolioToFile(List<String[]> dataToWrite, String portfolioName, String portfolioType) {

    try {
      this.createCSV(dataToWrite, portfolioName, portfolioType);
    } catch (Exception e) {
      System.out.println("CSV was not created");
    }
  }


  private void createCSV(List<String[]> dataToWrite, String portFolioName, String portfolioType) {
    File csvOutputFile = null;
    switch(portfolioType) {
      case "Fixed":
        csvOutputFile = new File(this.fixedPFPath + File.separator + portFolioName + ".csv");
        break;
      case "Flexible":
        csvOutputFile = new File(this.flexiblePFPath + File.separator + portFolioName + ".csv");
        break;
    }
    try {
      PrintWriter pw = new PrintWriter(csvOutputFile);
      dataToWrite.stream().map(this::convertToCSV).forEach(pw::println);
      pw.close();
    } catch (Exception e) {
      System.out.println("Error creating a csv");
    }
  }

  private String convertToCSV(String[] data) {
    return Stream.of(data)
            .map(this::escapeSpecialCharacters)
            .collect(Collectors.joining(","));
  }

  private String escapeSpecialCharacters(String data) {
    String escapedData = data.replaceAll("\\R", " ");
    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
      data = data.replace("\"", "\"\"");
      escapedData = "\"" + data + "\"";
    }
    return escapedData;
  }

  @Override
  public List<IstockModel> displayStocksOfPortFolio(int portfolioIndex, String portfolioType) {
    if (portfolioType.equals("fixed")) {
      IFixedPortfolio toDisplay = this.fixedPortfolios.get(portfolioIndex - 1);
      return toDisplay.getStocksInPortfolio();
    }
    else if (portfolioType.equals("flexible")) {
      IFlexiblePortfolio toDisplay = this.flexiblePortfolios.get(portfolioIndex - 1);
      return toDisplay.getStocksInPortfolio();
    }
    return null;
  }

  @Override
  public Double calculateValueOfPortfolio(int portfolioIndexForVal, LocalDate date, String portfolioType) {
    Double val = 0.0;
    if(portfolioType.equals("fixed")) {
      IFixedPortfolio toCalcVal = this.fixedPortfolios.get(portfolioIndexForVal - 1);
      val = toCalcVal.calculateValue(date);
    }
    else if (portfolioType.equals("flexible")) {
      IFlexiblePortfolio toCalcVal = this.flexiblePortfolios.get(portfolioIndexForVal - 1);
      val = toCalcVal.calculateValue(date);
    }
    return val;
  }

  @Override
  public void addStocksToAPortfolio(int portfolioIndex) {

  }


  @Override
  public void sellStocksFromAPortfolio(int portfolioIndex) {

  }

  @Override
  public void buySell() {

  }

  @Override
  public void displayChart() {

  }

}