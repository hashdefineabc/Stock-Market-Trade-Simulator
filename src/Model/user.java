package Model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

class user {
  String username;
  private String folderPath;
  private File file;
  public user() {
    this.folderPath = "C:\\Users\\anush\\OneDrive\\Desktop\\PortFolioComposition"; //TODO change to dynamic path
    file = new File(folderPath);
    this.createFolder();
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


}