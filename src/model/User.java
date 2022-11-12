package model;

import java.io.BufferedReader;
import java.io.File;
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

  List<String> fileNamesFromSystem;

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
    fileNamesFromSystem = new ArrayList<>();
    nasdaqTickerNames = new ArrayList<>();

    String userDirectory = new File("").getAbsolutePath();
    this.folderPath = userDirectory + File.separator + "PortFolioComposition";
    this.fixedPFPath = this.folderPath + File.separator + "FixedPortfolios";
    this.flexiblePFPath = this.folderPath + File.separator + "FlexiblePortfolios";

    file = new File(folderPath);
    this.createFolder();
    loadExistingPortFolios("Fixed"); //initially there are zero portfolios for a user
    loadNasdaqTickerNames();
    //todo create function to load the portfolios that are already created in the previous session
  }

  private void loadNasdaqTickerNames() {
    try {
      BufferedReader csvReader = new BufferedReader(new FileReader("./resources/Nasdaq_top25.csv"));
      String row = "";
      while (row != null) {
        row = csvReader.readLine();
        row = row.strip().split(",")[0];
        this.nasdaqTickerNames.add(row);
      }
      csvReader.close();
    } catch (Exception e) {
        //do nothing
    }
  }

  @Override
  public boolean isTickerValid(String tickerName) {
    return this.nasdaqTickerNames.contains(tickerName);
  }

  @Override
  public String getFolderPath() {
    return this.folderPath;
  }


  @Override
  public boolean createNewPortfolio(String portfolioName, List<String[]> stockList, String typeofPortfolio) {
    List<IstockModel> stockListToAdd = new ArrayList<IstockModel>();
    for (String[] singleStock : stockList) {
      Stock newStock = Stock.getBuilder()
              .tickerName(singleStock[0])
              .numOfUnits(Integer.valueOf(singleStock[1]))
              .commission(Double.valueOf(singleStock[2]))
              .buyingPrice(Double.valueOf(singleStock[3]))
              .buyDate(LocalDate.parse(singleStock[4])).build();

      stockListToAdd.add(newStock);
    }

    switch (typeofPortfolio) {
      case "Fixed":
        IFixedPortfolio newFixedPortfolio = new FixedPortfolio(portfolioName, stockListToAdd);
        this.fixedPortfolios.add(newFixedPortfolio);
        break;
      case "Flexible":
        IFlexiblePortfolio newFlexPortfolio = new FlexiblePortfolio(portfolioName, stockListToAdd);
        this.flexiblePortfolios.add(newFlexPortfolio);
        break;
    }

    //TODO: save this portfolio to a csv file
    //TODO: check if this file exists..
    return true;
  }

  @Override
  public double calculateCostBasisOfPortfolio(int portfolioIndex) {
    return 0;
  }


  @Override
  public void loadExistingPortFolios(String portfolioType) {
    List<String> fileNamesFromSystem =  null;
    String filePath = null;
    switch (portfolioType) {
      case "Fixed":
        this.fixedPortfolios.clear();
        String existingpfpath = null;
        String fixedpffolderpath = null;
        fileNamesFromSystem =  this.retrieveFileNames(existingpfpath); //TODO: replace existingpfpath with real path
        filePath = fixedpffolderpath;
        break;
      case "Flexible":
        this.flexiblePortfolios.clear();
        String existingpath = null;
        String flexiblepffolderpath = null;
        fileNamesFromSystem =  this.retrieveFileNames(existingpath); //TODO: replace existingpfpath with real path

        break;
    }

    if (fileNamesFromSystem.size() == 0) {
      return;
    }


    for (String portfolioName : fileNamesFromSystem) { //take files from system.
      List<String[]> listOfStocks = this.readCSVFromSystem(filePath + "/" + portfolioName);
      List<IstockModel> stockList = new ArrayList<>();
      for (String[] stockDetails : listOfStocks) {
        Stock s = Stock.getBuilder() //TODO: check if we can replace with interface name
                .tickerName(stockDetails[0])
                .numOfUnits(Integer.valueOf(stockDetails[1]))
                .commission(Double.valueOf(stockDetails[2]))
                .buyingPrice(Double.valueOf(stockDetails[3]))
                .buyDate(LocalDate.parse(stockDetails[4]))
                .build();
        stockList.add(s);
      }
      switch (portfolioType) {
        case "Fixed":
          IFixedPortfolio fip = new FixedPortfolio(portfolioName, stockList); //create a portfolio object.
          this.fixedPortfolios.add(fip);
          break;
        case "Flexible":
          IFlexiblePortfolio flp = new FlexiblePortfolio(portfolioName, stockList); //create a portfolio object.
          this.flexiblePortfolios.add(flp);
          break;
      }
    }
  }

  @Override
  public void createPortFolioFromFile() {
    this.loadExistingPortFolios("Fixed");
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
  public void createPortFolioFromFile(String typeofPortfolio) {

  }

  /**
   * Returns the list of portfolios of a particular user.
   *
   * @return
   */

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
  public List<IFixedPortfolio> getPortfoliosCreatedObjects() {
    return this.fixedPortfolios;
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

  private List<String> retrieveFileNames(String portfolioType) { //TODO:load only if extension is csv
    String[] fileNames = file.list();
    this.fileNamesFromSystem.clear();
    for (String fileName : fileNames) {
      if (fileName.contains(".csv")) {
        this.fileNamesFromSystem.add(fileName);
      }
    }
    return this.fileNamesFromSystem;
  }

  @Override
  public Boolean checkIfFileExists(String fileName) {
    this.retrieveFileNames("Fixed"); // updating the fileNamesFromSystem list.
    return this.fileNamesFromSystem.contains(fileName + ".csv");
  }

  @Override
  public void savePortfolioToFile(IFixedPortfolio newPortfolio) {
    List<String[]> dataToWrite = newPortfolio.toListOfString();

    try {
      this.createCSV(dataToWrite, newPortfolio.getNameOfPortFolio());
    } catch (Exception e) {
      System.out.println("CSV was not created");
    }
  }


  private void createCSV(List<String[]> dataToWrite, String portFolioName) {
    File csvOutputFile = new File(this.folderPath + File.separator + portFolioName + ".csv");
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
    IFixedPortfolio toDisplay = this.getPortfoliosCreatedObjects().get(portfolioIndex - 1);
    List<String[]> stocksToDisplay = toDisplay.toListOfString();
    //return stocksToDisplay;
    return null;
  }

  @Override
  public double calculateValueOfPortfolio(int portfolioIndexForVal, LocalDate date, String typeofPortfolio) {
    IFixedPortfolio toCalcVal = this.getPortfoliosCreatedObjects().get(portfolioIndexForVal - 1);
    double val = toCalcVal.calculateValue(date);
    return val;
  }

  @Override
  public void addStocksToAPortfolio(int portfolioIndex) {

        }

  public boolean createPortfolioManually(String portfolioName, List<String[]> stockList) {
    List<IstockModel> stockListToAdd = new ArrayList<>();
    for (String[] singleStock : stockList) {
      Stock newStock = Stock.getBuilder()
              .tickerName(singleStock[0])
              .numOfUnits(Integer.valueOf(singleStock[1]))
              .build();
      stockListToAdd.add(newStock);
    }
    IFixedPortfolio newPortfolio = new FixedPortfolio(portfolioName, stockListToAdd);
    this.savePortfolioToFile(newPortfolio);
    return this.checkIfFileExists(portfolioName);
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