package Model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class user {

  String username;
  List<portfolio> portfoliosList;
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















  protected void createFolder() {
    if (!Files.exists(Path.of(this.folderPath))) {
      file.mkdir();
      System.out.println("Folder created successfully");
    }
    else{
      System.out.println("Folder already exists.");
    }
  }

  protected String[] retrieveFileNames() {
    return file.list();
  }


  public void savePortfolioToFile(portfolio newPortfolio) {
  }
}