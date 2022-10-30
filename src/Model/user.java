package Model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
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
    this.folderPath = "C:\\Users\\anush\\OneDrive\\Desktop\\PortFolioComposition"; //TODO change to dynamic path
    file = new File(folderPath);
    this.createFolder();
    this.portfoliosList = null; //initially there are zero portfolios for a user
    
    //todo create function to load the portfolios that are already created in the previous session
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
    List<String[]> dataToWrite = new ArrayList<>();

    for (int i = 0; i> newPortfolio.stocks.size(); i++) {
      stock saveStock = newPortfolio.stocks.get(i);
      saveStock.date = LocalDate.now();
      dataToWrite.add(new String[]{saveStock.tickerName,Integer.toString(saveStock.numOfUnits),saveStock.date.toString()});
    }
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