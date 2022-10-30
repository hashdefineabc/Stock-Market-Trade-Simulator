package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

public class user {

  String username;
  List<portfolio> portfoliosList;
  List<String> fileNamesFromSystem;
  private String folderPath;
  private File file;
  public user() {
    portfoliosList = new ArrayList<>();
    fileNamesFromSystem = new ArrayList<>();

    this.folderPath = "/Users/manasamanjunath/Desktop/stocksPDP"; //TODO change to dynamic path
    file = new File(folderPath);
    this.createFolder();
    loadExistingPortFolios(); //initially there are zero portfolios for a user

    //todo create function to load the portfolios that are already created in the previous session
  }

  public void loadExistingPortFolios() {
    this.fileNamesFromSystem = this.retrieveFileNames();
    if(this.fileNamesFromSystem == null)
      return;
    portfolio p;
    for (String portfolioName: this.fileNamesFromSystem) { //take files from system.
      p = new portfolio(portfolioName); //create a portfolio object.
      //add stocks to the portfolio by reading csv.
      String filePath = this.folderPath + "/" + portfolioName;

      List<String[]> listOfStocks = this.readCSVFromSystem(filePath);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
      for (String[] stockDetails: listOfStocks) {
        stock s = stock.getBuilder()
                .tickerName(stockDetails[0])
                .numOfUnits(Integer.valueOf(stockDetails[1]))
                .date(LocalDate.parse(stockDetails[2],formatter))
                .build();
        p.addStocks(s);
      }
      //add this portfolio to list
      this.portfoliosList.add(p);
    }
  }

  public List<String[]> readCSVFromSystem(String filePath) {

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
  public void CreateNewPortfolio(portfolio newPortfolio) {
    portfoliosList.add(newPortfolio);
  }


  /**
   * Returns the list of portfolios of a particular user
   *
   * @return
   */

  public List<portfolio> getPortfoliosCreated() {

    return this.portfoliosList;

//    String[] files = this.retrieveFileNames();
//    for (int i = 0; i < files.length; i++) {
//      System.out.println(files[i]);
//      this.portfolios.add(files[i]);
//    }
//    return this.portfolios;
  }

//  public List<String> getPortfolioNames() {
//    List<String> answer = null;
//    for(portfolio portfolio: portfoliosList){
//      answer.add(portfolio.getNameOfPortFolio());
//    }
//    return answer;
//  }

  private void createFolder() {
    if (!Files.exists(Path.of(this.folderPath))) {
      file.mkdir();
      System.out.println("Folder created successfully");
    }
    else{
      System.out.println("Folder already exists.");
    }
  }

  private List<String> retrieveFileNames() {
    String[] fileNames = file.list();
    for (String fileName: fileNames){
      this.fileNamesFromSystem.add(fileName);
    }

    return this.fileNamesFromSystem;

  }

  public Boolean checkIfFileExists(String fileName) {
    if (this.fileNamesFromSystem.contains(fileName)) {
      return true;
    }
    return false;
  }


  public void savePortfolioToFile(portfolio newPortfolio) {
    List<String[]> dataToWrite = newPortfolio.toListOfString();

    try {
      this.createCSV(dataToWrite, newPortfolio.nameOfPortFolio);
    } catch(Exception e) {
      //TODO:handle this
    }
  }

  public void createCSV(List<String[]> dataToWrite, String portFolioName) throws IOException {
    File csvOutputFile = new File(portFolioName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      dataToWrite.stream().map(this::convertToCSV).forEach(pw::println);
    }
  }

  public String convertToCSV(String[] data) {
    return Stream.of(data)
            .map(this::escapeSpecialCharacters)
            .collect(Collectors.joining(","));
  }


  public String escapeSpecialCharacters(String data) {
    String escapedData = data.replaceAll("\\R", " ");
    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
      data = data.replace("\"", "\"\"");
      escapedData = "\"" + data + "\"";
    }
    return escapedData;
  }
}