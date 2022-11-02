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

public class User implements IUserInterface{

  String username;
  private List<portfolioModel> portfoliosList;
  List<String> fileNamesFromSystem;

  List<String> nasdaqTickerNames;
  public String folderPath;
  private String userDirectory;
  private File file;
  public User() {
    portfoliosList = new ArrayList<>();
    fileNamesFromSystem = new ArrayList<>();
    nasdaqTickerNames = new ArrayList<String>();

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
      p = new portfolio(portfolioName); //create a portfolio object.
      //add stocks to the portfolio by reading csv.
      String filePath = this.folderPath + "/" + portfolioName;

      List<String[]> listOfStocks = this.readCSVFromSystem(filePath);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // has to be capital M for month
      for (String[] stockDetails: listOfStocks) {
        try {
          stock s = stock.getBuilder()
                  .tickerName(stockDetails[0])
                  .numOfUnits(Integer.valueOf(stockDetails[1]))
                  .date(LocalDate.parse(stockDetails[2], formatter))
                  .build();
          p.addStocks(s);
        }
        catch (ArrayIndexOutOfBoundsException e) {

        }
      }
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

  /*
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
  public List<portfolioModel> getPortfoliosCreated() {
    return this.portfoliosList;
  }


  private void createFolder() {
    if (!Files.exists(Path.of(this.folderPath))) {
      file.mkdir();
      System.out.println("Folder created successfully");
    }
  }

  private List<String> retrieveFileNames() {
    String[] fileNames = file.list();
    this.fileNamesFromSystem.clear();
    for (String fileName: fileNames){
      this.fileNamesFromSystem.add(fileName);
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

  @Override
  public List<portfolioModel> getportfoliosList() {
    return this.portfoliosList;
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

}