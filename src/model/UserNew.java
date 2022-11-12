package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserNew implements IUserInterfaceNew{

  private List<IFixedPortfolio> fixedPortfolios;
  private List<IFlexiblePortfolio> flexiblePortfolios;

  @Override
  public void createNewPortfolio(String portfolioName, List<String[]> stockList, String typeofPortfolio) {
    List<IstockModelNew> stockListToAdd = new ArrayList<>();
    for (String[] singleStock : stockList) {
      NewStock newStock = NewStock.getBuilder()
              .tickerName(singleStock[0])
              .numOfUnits(Integer.valueOf(singleStock[1]))
              .commission(Double.valueOf(singleStock[2]))
              .buyingPrice(Double.valueOf(singleStock[3]))
              .buyDate(singleStock[4]).build();

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
  }


  @Override
  public void loadExistingPortfolios(String portfolioType) {
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
      List<IstockModelNew> stockList = new ArrayList<>();
      for (String[] stockDetails : listOfStocks) {
          NewStock s = NewStock.getBuilder()
                  .tickerName(stockDetails[0])
                  .numOfUnits(Integer.valueOf(stockDetails[1]))
                  .commission(Double.valueOf(stockDetails[2]))
                  .buyingPrice(Double.valueOf(stockDetails[3]))
                  .buyDate(stockDetails[4])
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
  public List<IstockModelNew> displayStocksOfPortFolio(int portfolioIndex, String portfolioType ) {
    return null;
  }

  @Override
  public double calculateValueOfPortfolio(int portfolioIndex, LocalDate date) {
    return 0;
  }

  @Override
  public double calculateCostBasisOfPortfolio(int portfolioIndex) {
    return 0;
  }

  @Override
  public void addStocksToAPortfolio(int portfolioIndex) {

  }

  @Override
  public void sellStocksFromAPortfolio(int portfolioIndex) {

  }

  private List<String> retrieveFileNames(String filePath) {
    File file = new File(filePath);
    String[] fileNames = file.list();
    List<String> fileNamesFromSystem = null;
    for (String fileName : fileNames) {
      if (fileName.contains(".csv")) {
        fileNamesFromSystem.add(fileName);
      }
    }
    return fileNamesFromSystem;
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
}
