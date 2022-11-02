package Model;

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
public class User implements IUserInterface{

  public String username;
  public List<portfolioModel> portfoliosList;
  List<String> fileNamesFromSystem;

  List<String> nasdaqTickerNames;
  public String folderPath;
  private String userDirectory;
  private File file;
  public User(String userName) {
    this.username = userName;
    portfoliosList = new ArrayList<>();
    fileNamesFromSystem = new ArrayList<>();
    nasdaqTickerNames = new ArrayList<>();

    userDirectory = new File("").getAbsolutePath();
    this.folderPath = userDirectory + File.separator + "PortFolioComposition";

    file = new File(folderPath);
    this.createFolder();
    loadExistingPortFolios(); //initially there are zero portfolios for a user
    loadNasdaqTickerNames();

    //todo create function to load the portfolios that are already created in the previous session
  }

  private void loadNasdaqTickerNames() {
    try {
      BufferedReader csvReader = new BufferedReader(new FileReader("NASDAQ_tickernames.csv"));
      String row = "";
      while ( !row.equals(null)) {
        row = csvReader.readLine();
        row = row.strip().split(",")[0];
        this.nasdaqTickerNames.add(row);
      }
      csvReader.close();
    } catch(Exception e) {

    }
  }

  @Override
  public boolean isTickerValid(String tickerName) {
    if (this.nasdaqTickerNames.contains(tickerName)) {
      return true;
    }
    return false;
  }

  @Override
  public String getFolderPath() {
    return this.folderPath;
  }

  @Override
  public void loadExistingPortFolios() {
    this.portfoliosList.clear();
    this.retrieveFileNames();
    if(this.fileNamesFromSystem.size() == 0)
      return;
    portfolio p;
    for (String portfolioName: this.fileNamesFromSystem) { //take files from system.
      //add stocks to the portfolio by reading csv.
      String filePath = this.folderPath + "/" + portfolioName;

      List<String[]> listOfStocks = this.readCSVFromSystem(filePath);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // has to be capital M for month
      List<IstockModel> stockList = new ArrayList<>();
      for (String[] stockDetails: listOfStocks) {
        try {
          stock s = stock.getBuilder()
                  .tickerName(stockDetails[0])
                  .numOfUnits(Integer.valueOf(stockDetails[1]))
                  .date(LocalDate.parse(stockDetails[2], formatter))
                  .build();
          stockList.add(s);
        }
        catch (ArrayIndexOutOfBoundsException e) {

        }
      }
      p = new portfolio(portfolioName, stockList); //create a portfolio object.
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

  /**
  creates a new portfolio
   */
  @Override
  public void CreateNewPortfolio(portfolioModel newPortfolio) {
    portfoliosList.add(newPortfolio);
  }


  /**
   * Returns the list of portfolios of a particular user
   *
   * @return
   */

  @Override
  public List<String> getPortfolioNamesCreated() {
    List<String> portfolioNames = new ArrayList<>();
    List<portfolioModel> portfolioObjects = this.portfoliosList;
    for (portfolioModel p : portfolioObjects) {
      portfolioNames.add(p.getNameOfPortFolio());
    }
    return portfolioNames;
  }

  @Override
  public List<portfolioModel> getPortfoliosCreated_Objects() {
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
    for (String fileName: fileNames){
      if (fileName.contains(".csv")) {
        this.fileNamesFromSystem.add(fileName);
      }
    }
    return this.fileNamesFromSystem;
  }

  @Override
  public Boolean checkIfFileExists(String fileName) {
    this.retrieveFileNames(); // updating the fileNamesFromSystem list.
    if (this.fileNamesFromSystem.contains(fileName + ".csv")) {
      return true;
    }
    return false;
  }

  @Override
  public void savePortfolioToFile(portfolioModel newPortfolio) {
    List<String[]> dataToWrite = newPortfolio.toListOfString();

    try {
      this.createCSV(dataToWrite, newPortfolio.getNameOfPortFolio());
    } catch(Exception e) {
      System.out.println("CSV was not created");
    }
  }


  private void createCSV(List<String[]> dataToWrite, String portFolioName) {
    File csvOutputFile = new File(this.folderPath + File.separator + portFolioName + ".csv");
    try {
      PrintWriter pw = new PrintWriter(csvOutputFile);
      dataToWrite.stream().map(this::convertToCSV).forEach(pw::println);
      pw.close();
    }
    catch (Exception e) {
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
    portfolioModel toDisplay = this.getPortfoliosCreated_Objects().get(portfolioIndex - 1);
    List<String[]> stocksToDisplay = toDisplay.toListOfString();
    return stocksToDisplay;
  }

  @Override
  public double calculateValueOfPortfolio(int portfolioIndexForVal, LocalDate date) {
    portfolioModel toCalcVal = this.getPortfoliosCreated_Objects().get(portfolioIndexForVal - 1);
    double val = toCalcVal.valueOfPortfolio(date);
    return val;
  }

  @Override
  public boolean createPortfolioManually(String portfolioName, List<String[]> stockList) {
    List<IstockModel> stockListToAdd = new ArrayList<>();
    for (String[] singleStock: stockList) {
      stock newStock = stock.getBuilder()
              .tickerName(singleStock[0])
              .numOfUnits(Integer.valueOf(singleStock[1]))
              .build();
      stockListToAdd.add(newStock);
    }
    portfolioModel newPortfolio = new portfolio(portfolioName, stockListToAdd);
    this.CreateNewPortfolio(newPortfolio);
    this.savePortfolioToFile(newPortfolio);
    if (this.checkIfFileExists(portfolioName)) {
      return true;
    }
    return false;
  }

  @Override
  public void setFolderPath(String folderPath) {
    this.folderPath = folderPath;
  }
}