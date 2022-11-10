package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to implement the user's functionality for the stock market application.
 */
public class User implements IUserInterface {

<<<<<<< HEAD:src/model/User.java
  public String username;
  public List<PortfolioModel> portfoliosList;
=======
  private List<PortfolioModel> portfoliosList;
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
  List<String> fileNamesFromSystem;

  List<String> nasdaqTickerNames;
  private String folderPath;
  private File file;


  /**
   * Constructor for user class.
   * it takes username and initializes the fields of this class
   */

  public User() {

    portfoliosList = new ArrayList<>();
    fileNamesFromSystem = new ArrayList<>();
    nasdaqTickerNames = new ArrayList<>();

    String userDirectory = new File("").getAbsolutePath();
    this.folderPath = userDirectory + File.separator + "PortFolioComposition";

    file = new File(folderPath);
    this.createFolder();
    loadExistingPortFolios(); //initially there are zero portfolios for a user
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
  public void loadExistingPortFolios() {
    this.portfoliosList.clear();
    this.retrieveFileNames();
    if (this.fileNamesFromSystem.size() == 0) {
      return;
<<<<<<< HEAD:src/model/User.java
    Portfolio p;
    for (String portfolioName: this.fileNamesFromSystem) { //take files from system.
=======
    }
    Portfolio p;
    for (String portfolioName : this.fileNamesFromSystem) { //take files from system.
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
      //add stocks to the portfolio by reading csv.
      String filePath = this.folderPath + "/" + portfolioName;

      List<String[]> listOfStocks = this.readCSVFromSystem(filePath);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      List<IstockModel> stockList = new ArrayList<>();
      for (String[] stockDetails : listOfStocks) {
        try {
          Stock s = Stock.getBuilder()
                  .tickerName(stockDetails[0])
                  .numOfUnits(Integer.valueOf(stockDetails[1]))
                  .date(LocalDate.parse(stockDetails[2], formatter))
                  .build();
          stockList.add(s);
        } catch (ArrayIndexOutOfBoundsException e) {
            // do nothing
        }
      }
      p = new Portfolio(portfolioName, stockList); //create a portfolio object.
      //add this portfolio to list
      this.portfoliosList.add(p);
    }
  }

  @Override
  public void createPortFolioFromFile() {
    this.loadExistingPortFolios();
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
<<<<<<< HEAD:src/model/User.java
  public void CreateNewPortfolio(PortfolioModel newPortfolio) {
=======
  public void createNewPortfolio(PortfolioModel newPortfolio) {
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
    portfoliosList.add(newPortfolio);
  }


  /**
   * Returns the list of portfolios of a particular user.
   *
   * @return
   */

  @Override
  public List<String> getPortfolioNamesCreated() {
    this.loadExistingPortFolios();
    List<String> portfolioNames = new ArrayList<>();
    List<PortfolioModel> portfolioObjects = this.portfoliosList;
    for (PortfolioModel p : portfolioObjects) {
      portfolioNames.add(p.getNameOfPortFolio());
    }
    return portfolioNames;
  }

<<<<<<< HEAD:src/model/User.java
  @Override
  public List<PortfolioModel> getPortfoliosCreated_Objects() {
=======
  private List<PortfolioModel> getPortfoliosCreatedObjects() {
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
    return this.portfoliosList;
  }


  private void createFolder() {
    if (!Files.exists(Path.of(this.folderPath))) {
      file.mkdir();
    }
  }

  private List<String> retrieveFileNames() { //TODO:load only if extension is csv
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
    this.retrieveFileNames(); // updating the fileNamesFromSystem list.
    return this.fileNamesFromSystem.contains(fileName + ".csv");
  }

  @Override
  public void savePortfolioToFile(PortfolioModel newPortfolio) {
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
  public List<String[]> displayStocksOfPortFolio(int portfolioIndex) {
<<<<<<< HEAD:src/model/User.java
    PortfolioModel toDisplay = this.getPortfoliosCreated_Objects().get(portfolioIndex - 1);
=======
    PortfolioModel toDisplay = this.getPortfoliosCreatedObjects().get(portfolioIndex - 1);
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
    List<String[]> stocksToDisplay = toDisplay.toListOfString();
    return stocksToDisplay;
  }

  @Override
  public double calculateValueOfPortfolio(int portfolioIndexForVal, LocalDate date) {
<<<<<<< HEAD:src/model/User.java
    PortfolioModel toCalcVal = this.getPortfoliosCreated_Objects().get(portfolioIndexForVal - 1);
=======
    PortfolioModel toCalcVal = this.getPortfoliosCreatedObjects().get(portfolioIndexForVal - 1);
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
    double val = toCalcVal.valueOfPortfolio(date);
    return val;
  }

  @Override
  public boolean createPortfolioManually(String portfolioName, List<String[]> stockList) {
    List<IstockModel> stockListToAdd = new ArrayList<>();
<<<<<<< HEAD:src/model/User.java
    for (String[] singleStock: stockList) {
=======
    for (String[] singleStock : stockList) {
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
      Stock newStock = Stock.getBuilder()
              .tickerName(singleStock[0])
              .numOfUnits(Integer.valueOf(singleStock[1]))
              .build();
      stockListToAdd.add(newStock);
    }
    PortfolioModel newPortfolio = new Portfolio(portfolioName, stockListToAdd);
<<<<<<< HEAD:src/model/User.java
    this.CreateNewPortfolio(newPortfolio);
=======
    this.createNewPortfolio(newPortfolio);
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
    this.savePortfolioToFile(newPortfolio);
    return this.checkIfFileExists(portfolioName);
  }

<<<<<<< HEAD:src/model/User.java
  @Override
  public void setFolderPath(String folderPath) {
    this.folderPath = folderPath;
  }

  @Override
  public void buySell() {

  }

  @Override
  public void calculateCostBasis() {

  }

  @Override
  public void displayChart() {

  }
=======
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:src/Model/User.java
}