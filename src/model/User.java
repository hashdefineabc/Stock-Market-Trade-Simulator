package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.Math;

/**
 * Class to implement the user's functionality for the stock market application.
 * User model is the main model of our application.
 * It contains the lists of fixed and flexible portfolios.
 * A user is allowed to create one or more portfolios with shares of one or more stock.
 * User can create two types of portfolios, fixed portfolios and flexible portfolios.
 * Once fixed portfolios are created, shares cannot be added or removed from the portfolio.
 * The flexible portfolios have the ability to get modified, i.e., we can buy or sell stocks from
 * a flexible portfolio even after creating the portfolio.
 * User can examine the composition of both fixed and flexible portfolios.
 * User can determine the total value of a portfolio on a certain date.
 * User can persist a portfolio so that it can be saved and loaded from the files.
 *<p>
 * For flexible portfolios, user can purchase a specific number of shares of a specific stock
 * on a specified date, and add them to the portfolio
 * They can sell a specific number of shares of a specific stock on a specified date
 * from a given flexible portfolio.
 *</p>
 * <p>
 * User can determine the cost basis that is the total amount of money invested in a portfolio
 * by a specific date. This includes all the purchases made in that portfolio till that date.
 *</p>
 * <p>
 * User can determine the value of a portfolio on a specific date.
 * The value for a portfolio before the date of its first purchase is 0.
 *</p>
 * <p>
 * Each transaction in flexible portfolio has a commission value associated with it.
 * Commission value is also used in determining the cost basis of the portfolio.
 *</p>
 */
public class User implements IUserInterface {

  /**
   * The Nasdaq ticker names.
   * We support NASDAQ top 25 stocks in our application.
   */
  //minor change
  List<String> nasdaqTickerNames;
  private String folderPath;
  private String fixedPFPath;
  private String flexiblePFPath;
  private String investmentInstrPath;
  private String dcaInstrPath;
  private File file;
  private String apiKey = "RWI9HAQXNXJQQSJI";

  private List<IFixedPortfolio> fixedPortfolios;
  private List<IFlexiblePortfolio> flexiblePortfolios;


  /**
   * Constructor for user class.
   * it takes user directory where the user wants to store all the portfolios created and
   * initializes the fields of this class
   *
   * @param userDirectory the user directory where the user wants to store the portfolios created.
   */
  public User(String userDirectory) {

    this.fixedPortfolios = new ArrayList<>();
    this.flexiblePortfolios = new ArrayList<>();
    nasdaqTickerNames = new ArrayList<>();

    if (userDirectory == null) {
      userDirectory = new File("").getAbsolutePath();
      this.folderPath = userDirectory + File.separator + "PortFolioComposition";
    }
    else {
      this.folderPath = userDirectory;
    }
    this.fixedPFPath = this.folderPath + File.separator + "FixedPortfolios";
    this.flexiblePFPath = this.folderPath + File.separator + "FlexiblePortfolios";
    this.investmentInstrPath = this.folderPath + File.separator + "InvestmentInstructions";
    this.dcaInstrPath = this.folderPath + File.separator + "DCAInstructions";

    file = new File(folderPath);
    this.createFolder();
    this.loadExistingPortFolios(PortfolioType.fixed);
    this.updateFlexiblePortFolios(InvestmentType.InvestByWeights);
    this.updateFlexiblePortFolios(InvestmentType.DCA);
    this.loadExistingPortFolios(PortfolioType.flexible);
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
      }
      while (row != null);
      csvReader.close();
    }
    catch (Exception e) {
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
  public boolean createNewPortfolio(String portfolioName, List<String[]> stockList,
                                    PortfolioType typeofPortfolio) {
    List<String[]> dataToWrite = null;
    List<IstockModel> stockListToAdd = new ArrayList<>();

    for (String[] singleStock : stockList) {
      Stock newStock = Stock.getBuilder()
              .tickerName(singleStock[0])
              .numOfUnits(Double.valueOf(singleStock[1]))
              .transactionDate(LocalDate.parse((singleStock[2])))
              .commission(Double.valueOf(singleStock[3]))
              .transactionPrice(Double.valueOf(singleStock[4]))
              .buyOrSell(Operation.valueOf(singleStock[5]))
              .build();

      stockListToAdd.add(newStock);
    }

    //sort the stockList as per date
    stockListToAdd = this.sortStockListDateWise(stockListToAdd);

    if (typeofPortfolio.equals(PortfolioType.fixed)) {
      IFixedPortfolio newFixedPortfolio = new FixedPortfolio(portfolioName, stockListToAdd);
      this.fixedPortfolios.add(newFixedPortfolio);
      dataToWrite = newFixedPortfolio.toListOfString();
      this.savePortfolioToFile(dataToWrite, newFixedPortfolio.getNameOfPortFolio(),typeofPortfolio);
    }
    else if (typeofPortfolio.equals(PortfolioType.flexible)) {
      IFlexiblePortfolio newFlexPortfolio = new FlexiblePortfolio(portfolioName, stockListToAdd);
      this.flexiblePortfolios.add(newFlexPortfolio);
      dataToWrite = newFlexPortfolio.toListOfString();
      this.savePortfolioToFile(dataToWrite, newFlexPortfolio.getNameOfPortFolio(),typeofPortfolio);
    }
    return this.checkIfFileExists(portfolioName,typeofPortfolio);
  }

  @Override
  public Double calculateCostBasisOfPortfolio(int portfolioIndex, PortfolioType portfolioType,
                                              LocalDate costBasisDate) {
    Double costBasis = 0.0;
    if (portfolioType.equals(PortfolioType.fixed)) {
      IFixedPortfolio toCalcCostBasis = this.fixedPortfolios.get(portfolioIndex - 1);
      costBasis = toCalcCostBasis.calculateCostBasis(costBasisDate);
    }
    else if (portfolioType.equals(PortfolioType.flexible)) {
      IFlexiblePortfolio toCalcCostBasis = this.flexiblePortfolios.get(portfolioIndex - 1);
      costBasis = toCalcCostBasis.calculateCostBasis(costBasisDate);

      if (costBasisDate.isAfter(LocalDate.now())) {
        String instrFile = this.retrieveInstr(toCalcCostBasis.getNameOfPortFolio(),
                InvestmentType.InvestByWeights);
        if (!instrFile.equals("")) {
          costBasis += toCalcCostBasis.calculateCostBasisForFuture(costBasisDate,
                  InvestmentType.InvestByWeights, this.investmentInstrPath
                          + File.separator + instrFile);
        }

        String dcaFile = this.retrieveInstr(toCalcCostBasis.getNameOfPortFolio(),
                InvestmentType.DCA);
        if (!dcaFile.equals("")) {
          costBasis += toCalcCostBasis.calculateCostBasisForFuture(costBasisDate,
                  InvestmentType.DCA, this.dcaInstrPath + File.separator + dcaFile);
        }

      }
    }
    return costBasis;
  }


  @Override
  public void loadExistingPortFolios(PortfolioType portfolioType) {
    List<String> fileNamesFromSystem =  new ArrayList<>();
    String filePath = null;
    if (portfolioType.equals(PortfolioType.fixed)) {
      this.fixedPortfolios.clear();
      fileNamesFromSystem =  this.retrieveFileNames(portfolioType);
      filePath = this.fixedPFPath;
    }
    else if (portfolioType.equals(PortfolioType.flexible)) {
      this.flexiblePortfolios.clear();
      fileNamesFromSystem =  this.retrieveFileNames(portfolioType);
      filePath = this.flexiblePFPath;
    }

    if (fileNamesFromSystem.size() == 0) {
      return;
    }

    for (String portfolioName : fileNamesFromSystem) { //take files from system.
      List<String[]> listOfStocks = this.readCSVFromSystem(filePath
              + File.separator + portfolioName);
      List<IstockModel> stockList = new ArrayList<>();


      for (String[] stockDetails : listOfStocks) {
        Stock s = Stock.getBuilder()
                .tickerName(stockDetails[0])
                .numOfUnits(Double.valueOf(stockDetails[1]))
                .transactionDate(LocalDate.parse(stockDetails[2]))
                .commission(Double.valueOf(stockDetails[3]))
                .transactionPrice(Double.valueOf(stockDetails[4]))
                .buyOrSell(Operation.valueOf(stockDetails[5]))
                .build();
        stockList.add(s);
      }

      if (portfolioType.equals(PortfolioType.fixed)) {
        IFixedPortfolio fip = new FixedPortfolio(portfolioName, stockList);
        this.fixedPortfolios.add(fip);
      }
      else if (portfolioType.equals(PortfolioType.flexible)) {
        IFlexiblePortfolio flp = new FlexiblePortfolio(portfolioName, stockList);
        this.flexiblePortfolios.add(flp);
      }
    }
  }

  @Override
  public void createPortFolioFromFile(PortfolioType portfolioType) {
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
  public List<String> getPortfolioNamesCreated(PortfolioType portFolioType) {

    List<String> portfolioNames = new ArrayList<>();

    if (portFolioType.equals(PortfolioType.fixed)) {
      this.loadExistingPortFolios(portFolioType);
      List<IFixedPortfolio> portfolioObjects = this.fixedPortfolios;
      for (IFixedPortfolio p : portfolioObjects) {
        portfolioNames.add(p.getNameOfPortFolio());
      }
    }
    else if (portFolioType.equals(PortfolioType.flexible)) {
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
    //creating InvestmentInstructions folder
    file = new File(this.investmentInstrPath);
    if (!Files.exists(Path.of(this.investmentInstrPath))) {
      file.mkdir();
    }
    //creating DCAInstructions folder
    file = new File(this.dcaInstrPath);
    if (!Files.exists(Path.of(this.dcaInstrPath))) {
      file.mkdir();
    }

  }

  private List<String> retrieveFileNames(PortfolioType portfolioType) {
    List<String> fileNamesFromSystem = new ArrayList<>();
    if (portfolioType.equals(PortfolioType.fixed)) {
      file = new File(this.fixedPFPath);
    }
    else if (portfolioType.equals(PortfolioType.flexible)) {
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
  public Boolean checkIfFileExists(String fileName, PortfolioType portfolioType) {
    List<String> fileNamesFromSystem = this.retrieveFileNames(portfolioType);
    return fileNamesFromSystem.contains(fileName + ".csv");
  }

  @Override
  public void savePortfolioToFile(List<String[]> dataToWrite, String portfolioName,
                                  PortfolioType portfolioType) {

    try {
      this.createCSV(dataToWrite, portfolioName, portfolioType);
    } catch (Exception e) {
      System.out.print("CSV was not created\n");
    }
  }


  private void createCSV(List<String[]> dataToWrite, String portFolioName,
                         PortfolioType portfolioType) {
    File csvOutputFile = null;
    if (portfolioType.equals(PortfolioType.fixed)) {
      csvOutputFile = new File(this.fixedPFPath + File.separator + portFolioName + ".csv");
    } else if (portfolioType.equals(PortfolioType.flexible)) {
      csvOutputFile = new File(this.flexiblePFPath + File.separator
              + portFolioName + ".csv");
    }

    try {
      PrintWriter pw = new PrintWriter(csvOutputFile);
      dataToWrite.stream().map(this::convertToCSV).forEach(pw::println);
      pw.close();
    } catch (Exception e) {
      System.out.print("Error creating a csv\n");
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
  public List<IstockModel> displayStocksOfPortFolio(int portfolioIndex, PortfolioType portfolioType,
                                                    LocalDate dateForComposition) {
    if (portfolioType.equals(PortfolioType.fixed)) {
      IFixedPortfolio toDisplay = this.fixedPortfolios.get(portfolioIndex - 1);
      return toDisplay.getStocksInPortfolio(dateForComposition);
    }
    else if (portfolioType.equals(PortfolioType.flexible)) {
      IFlexiblePortfolio toDisplay = this.flexiblePortfolios.get(portfolioIndex - 1);
      return toDisplay.getStocksInPortfolio(dateForComposition);
    }
    return null;
  }

  @Override
  public Double calculateValueOfPortfolio(int portfolioIndexForVal, LocalDate date,
                                          PortfolioType portfolioType) {
    Double val = 0.0;
    if (portfolioType.equals(PortfolioType.fixed)) {
      IFixedPortfolio toCalcVal = this.fixedPortfolios.get(portfolioIndexForVal - 1);
      val = toCalcVal.calculateValue(date);
    }
    else if (portfolioType.equals(PortfolioType.flexible)) {
      IFlexiblePortfolio toCalcVal = this.flexiblePortfolios.get(portfolioIndexForVal - 1);
      val = toCalcVal.calculateValue(date);
    }
    return val;
  }

  @Override
  public Double getStockPriceFromDB(String tickerNameFromUser, LocalDate transactionDate) {
    Double result = 0.0;
    FileReader file = null;
    String fileName = "./resources/stockData/" + File.separator + tickerNameFromUser + ".csv";

    try {
      file = new FileReader(fileName);
    } catch (FileNotFoundException e) {
      updateStockFile(tickerNameFromUser);
    }

    try {
      file = new FileReader(fileName);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found yet !!!!!!!!!!!");
    }

    try (BufferedReader br = new BufferedReader(file)) {
      String line = br.readLine();
      /* check if the date for which the value is to be determined is greater than
          the latest date that exists in our file.
      */
      String[] attributes = line.split(",");
      line = br.readLine();
      attributes = line.split(",");
      String timeStamp = attributes[0];
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      try {
        LocalDate dt = LocalDate.parse(timeStamp, formatter);
        if (dt.compareTo(transactionDate) < 0) { // date > latest (first) timestamp in file
          updateStockFile(tickerNameFromUser);
        }
        BufferedReader br1 = new BufferedReader(new FileReader(fileName));
        String line1 = br1.readLine();
        line1 = br1.readLine();
        while (line1 != null) {
          attributes = line1.split(",");
          timeStamp = attributes[0];
          if (timeStamp.equals(transactionDate.toString())) {
            result += Double.parseDouble(attributes[4]);
            return result;
          }
          line1 = br1.readLine();
        }
      } catch (Exception e) {
        // do nothing
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  @Override
  public Map<LocalDate, String> calculateChart(int option, int portfolioIndexForVal,
                                               PortfolioType portfolioType) {
    Map<LocalDate, String> chart = new LinkedHashMap<>();
    if (portfolioType.equals(PortfolioType.fixed)) {
      IFixedPortfolio toDisplayChart = this.fixedPortfolios.get(portfolioIndexForVal - 1);
      chart = toDisplayChart.calculateChartValues(option);
    }
    else if (portfolioType.equals(PortfolioType.flexible)) {
      IFlexiblePortfolio toDisplayChart = this.flexiblePortfolios.get(portfolioIndexForVal - 1);
      chart = toDisplayChart.calculateChartValues(option);
    }

    return chart;
  }

  @Override
  public String getPortfolioName(int portfolioIndex, PortfolioType portfolioType) {
    if (portfolioType.equals(PortfolioType.fixed)) {
      return this.fixedPortfolios.get(portfolioIndex - 1).getNameOfPortFolio();
    }
    else {
      return this.flexiblePortfolios.get(portfolioIndex - 1).getNameOfPortFolio();
    }

  }

  @Override
  public Double getScale(int portfolioIndex, PortfolioType portfolioType) {

    if (portfolioType.equals(PortfolioType.fixed)) {
      IFixedPortfolio fp = this.fixedPortfolios.get(portfolioIndex - 1);
      return fp.getScale();
    }
    else {
      IFlexiblePortfolio fp = this.flexiblePortfolios.get(portfolioIndex - 1);
      return fp.getScale();
    }

  }


  private void updateStockFile(String tickerName) {
    try {
      URL url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerName + "&apikey=" + apiKey + "&datatype=csv");

      String filePath = "./resources/stockData" + File.separator + tickerName + ".csv";
      new FileWriter(filePath, false).close();

      List<String[]> row = new ArrayList<>();

      InputStream in = null;
      StringBuilder output = new StringBuilder();

      in = url.openStream();

      try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
        String line = br.readLine();

        while (line != null) {
          String[] attributes = line.split(",");
          row.add(attributes);
          line = br.readLine();
        }
        try (PrintWriter pw = new PrintWriter(filePath)) {
          row.stream()
                  .map(this::convertToCSV)
                  .forEach(pw::println);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<IstockModel> sortStockListDateWise(List<IstockModel> stockList) {
    stockList.sort((s1,s2) -> s1.getTransactionDate().compareTo(s2.getTransactionDate()));
    return stockList;
  }

  /**
   * Gets value of a portfolio on a specific date.
   *
   * @param tickerNameFromUser the ticker name for which the value needs to be computed.
   * @param transactionDate    the transaction date on which the value needs to be determined.
   * @return the value on date
   */
  public Double getValueOnDate(String tickerNameFromUser, LocalDate transactionDate) {
    Double transactionValue = this.getStockPriceFromDB(tickerNameFromUser, transactionDate);
    LocalTime currentTime = LocalTime.now();
    if (transactionDate.equals(LocalDate.now())
            && currentTime.isBefore(LocalTime.of(16,0))) {
      transactionValue = this.getStockPriceFromDB(tickerNameFromUser,
              transactionDate.minusDays(1));
    }
    while (transactionValue == 0.0) {
      // Market was closed on transactionDate, so considering price of previous date."
      transactionValue = this.getStockPriceFromDB(tickerNameFromUser,
              transactionDate.minusDays(1));
      transactionDate = transactionDate.minusDays(1);
    }
    return transactionValue;
  }

  @Override
  public void updateFlexiblePortFolios(InvestmentType investmentType ) {
    //get list of all flexible portfolios
    for (IFlexiblePortfolio flp:this.flexiblePortfolios) {
      //check if instructions exist for this portfolio
      String instrFile = this.retrieveInstr(flp.getNameOfPortFolio(),investmentType);
      if (!instrFile.equals("")) {
        //flp.executeInstructions(instrFile);
        if (investmentType.equals(InvestmentType.InvestByWeights)) {
          this.executeInstructionsForFlexPortfolio(flp, this.investmentInstrPath
                  + File.separator + instrFile, InvestmentType.InvestByWeights);
        }
        else if (investmentType.equals(InvestmentType.DCA)) {
          this.executeInstructionsForFlexPortfolio(flp, this.dcaInstrPath
                  + File.separator + instrFile, InvestmentType.DCA);
        }

      }
    }
    this.loadExistingPortFolios(PortfolioType.flexible);
  }

  @Override
  public boolean validateNumUnits(String numUnits) {
    Double numUnitsDouble = Double.valueOf(numUnits);
    Long numOfUnitsInt = Math.round(numUnitsDouble);
    if (numUnitsDouble <= 0.0) {
      return false;
    } else if ((double) numOfUnitsInt != numUnitsDouble) {
      return false;
    }
    return true;
  }

  @Override
  public boolean validateCommissionValue(String commVal) {
    Double commValDouble = Double.valueOf(commVal);
    if (commValDouble <= 0.0) {
      return false;
    }
    return true;
  }

  private void executeInstructionsForFlexPortfolio(IFlexiblePortfolio flp,
                                                   String instrFile,
                                                   InvestmentType investmentType) {

    String line = "";
    String splitBy = ",";
    Integer numSharesBought = 0;
    Double numShares = 0.0;

    try {
      BufferedReader br = new BufferedReader(new FileReader(instrFile));
      Double amount = Double.parseDouble(br.readLine().split(splitBy)[1]);
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate startDate = LocalDate.parse(br.readLine().split(splitBy)[1],dateFormat);
      LocalDate endDate = LocalDate.parse(br.readLine().split(splitBy)[1],dateFormat);
      Integer daysToInvest = Integer.parseInt(br.readLine().split(splitBy)[1]);
      Double commission = Double.parseDouble(br.readLine().split(splitBy)[1]);
      LocalDate lastTxnDate = LocalDate.parse(br.readLine().split(splitBy)[1],dateFormat);
      HashMap<String, Double> weights = new HashMap<>();

      if (lastTxnDate.plusDays(daysToInvest).isEqual(LocalDate.now())) {
        while ((line = br.readLine()) != null) {
          weights.put(line.split(splitBy)[0], Double.parseDouble(line.split(splitBy)[1]));
        }
        br.close();
        for(Map.Entry<String,Double> stockWeight: weights.entrySet()) {
          String stockName = stockWeight.getKey();
          Double moneyToInvest = (amount * stockWeight.getValue()) / 100.00;
          Double priceOfSingleShare = this.getStockPriceFromDB(stockName,LocalDate.now());
          if (investmentType.equals(InvestmentType.InvestByWeights)) {
            numSharesBought = (int) (moneyToInvest/priceOfSingleShare);
            numShares = Double.valueOf(numSharesBought);
          }
          else if (investmentType.equals(InvestmentType.DCA)) {
            numShares = (moneyToInvest/priceOfSingleShare);
          }

          Stock newStock = Stock.getBuilder().tickerName(stockName)
                  .numOfUnits(numShares)
                  .transactionDate(LocalDate.now())
                  .commission(commission)
                  .transactionPrice(priceOfSingleShare)
                  .buyOrSell(Operation.BUY).build();
          flp.addOrSellStocks(newStock);
        }
        List<String[]> dataToWrite = flp.toListOfString();
        this.savePortfolioToFile(dataToWrite, flp.getNameOfPortFolio().strip()
                .split(".csv")[0], PortfolioType.flexible);

        /*if (investmentType.equals(InvestmentType.InvestByWeights)) {
          File oldFile = new File(instrFile);
          File newFile = new File(instrFile.replace(".csv","executed.csv"));
          oldFile.renameTo(newFile);
        }*/

      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }





  private String retrieveInstr(String portfolioName,InvestmentType investmentType) {
    if (investmentType.equals(InvestmentType.InvestByWeights)) {
      file = new File(this.investmentInstrPath);
      String[] fileNames = file.list();
      for (String fileName : fileNames) {
        if (fileName.contains(portfolioName) && !fileName.contains("executed")) {
          return fileName;
        }
      }
    }
    else if (investmentType.equals(InvestmentType.DCA)) {
      file = new File(this.dcaInstrPath);
      String[] fileNames = file.list();
      for (String fileName : fileNames) {
        if (fileName.contains(portfolioName)) {
          return fileName;
        }
      }
    }

    return "";
  }

  @Override
  public LocalDate calculateTxns(LocalDate strategyStart, LocalDate strategyEnd,
                                 Integer daysToInvest, HashMap<String,Double> weights,
                                 double amount, Double commission, int portfolioIndex,
                                 InvestmentType investmentType) {

    LocalDate nextInvestDate = strategyStart;
    LocalDate realEndDate = null;
      int compare = LocalDate.now().compareTo(strategyEnd);
      if (compare == 1) {
        realEndDate = strategyEnd;
      } else if (compare == -1 || compare == 0) {
        realEndDate = LocalDate.now();
      }
    IFlexiblePortfolio flp = this.flexiblePortfolios.get(portfolioIndex - 1);
    Double numSharesBought = 0.0;

    while (nextInvestDate.isBefore(realEndDate)
            || nextInvestDate.isEqual(realEndDate)) {
      for (Map.Entry <String,Double> stockWeight : weights.entrySet()) {
        String stockName = stockWeight.getKey();
        Double moneyToInvest = (amount * stockWeight.getValue()) / 100.00;
        Double priceOfSingleShare = this.getStockPriceFromDB(stockName,nextInvestDate);
        if (investmentType.equals(InvestmentType.DCA)) {
          //numSharesBought = (moneyToInvest/priceOfSingleShare); //allowing fractional shares.
          numSharesBought = Math.round(Double.valueOf( (moneyToInvest/priceOfSingleShare))
                  * 1000d) / 1000d;
        }
        else if (investmentType.equals(InvestmentType.InvestByWeights)) {
          int numShares = (int) (moneyToInvest/priceOfSingleShare); //not allowing fractionalshares.
          numSharesBought = Double.valueOf(numSharesBought);
        }

        Stock newStock = Stock.getBuilder().tickerName(stockName)
                .numOfUnits(numSharesBought)
                .transactionDate(nextInvestDate)
                .commission(commission)
                .transactionPrice(priceOfSingleShare)
                .buyOrSell(Operation.BUY).build();
        flp.addOrSellStocks(newStock);
      }

      if (investmentType.equals(InvestmentType.InvestByWeights)) {
        daysToInvest = 1;
      }
      nextInvestDate = nextInvestDate.plusDays(daysToInvest);

    }
    List<String[]> dataToFile = flp.toListOfString();
    this.savePortfolioToFile(dataToFile,flp.getNameOfPortFolio().strip()
            .split(".csv")[0], PortfolioType.flexible);

    return nextInvestDate.minusDays(daysToInvest);

    }

  @Override
  public void acceptStrategyFromUser(int portfolioIndex, Double amount, Double comm,
                                     LocalDate startDate, LocalDate endDate,
                                     HashMap<String,Double> weights,
                                     InvestmentType investmentType, Integer daysToInvest,
                                     LocalDate lastTxnDate) {

    List<String[]> dataToWrite = this.getDataToWrite(amount, comm, startDate, endDate, weights,
            daysToInvest, lastTxnDate);
    this.saveInstrToFile(this.getPortfolioName(portfolioIndex,PortfolioType.flexible), dataToWrite,
            investmentType);
  }

  @Override
  public void saveInstrToFile(String portfolioName, List<String[]> dataToWrite,
                              InvestmentType investmentType)
  {
    File csvOutputFile = null;
    if (investmentType.equals(InvestmentType.InvestByWeights)) {
      csvOutputFile = new File(this.investmentInstrPath + File.separator
              + portfolioName); //TODO: replace this when file writing is moved to controller
    }
    else if (investmentType.equals(InvestmentType.DCA)) {
      csvOutputFile = new File(this.dcaInstrPath + File.separator
              + portfolioName); //TODO: replace this when file writing is moved to controller
    }

    try {
      PrintWriter pw = new PrintWriter(csvOutputFile);
      dataToWrite.stream().map(this::convertToCSV).forEach(pw::println);
      pw.close();
    } catch (Exception e) {
      System.out.print("Error creating a csv\n");
    }
  }


  private List<String[]> getDataToWrite(Double amount, Double comm,
                                        LocalDate startDate, LocalDate endDate,
                                        HashMap<String,Double> weights, Integer daysToInvest,
                                        LocalDate lastTxnDate) {
    List<String[]> answer = new ArrayList<>();
    String[] amt = new String[2];
    amt[0] = "AMOUNT";
    amt[1] = amount.toString();
    answer.add(amt);
    String[] startDt = new String[2];
    startDt[0] = "START_DATE";
    startDt[1] = startDate.toString();
    answer.add(startDt);
    String[] endDt = new String[2];
    endDt[0] = "END_DATE";
    endDt[1] =  endDate.toString();
    answer.add(endDt);
    String[] days = new String[2];
    days[0] = "DAYS_TO_INVEST";
    days[1] =  daysToInvest.toString();
    answer.add(days);
    String[] commission = new String[2];
    commission[0] = "COMMISSION";
    commission[1] = comm.toString();
    answer.add(commission);
    String[] lastDate = new String[2];
    lastDate[0] = "LAST_TXN_DATE";
    lastDate[1] = lastTxnDate.toString();
    answer.add(lastDate);

    for (Map.Entry<String,Double> element : weights.entrySet()) {
      String[] weight = new String[2];
      weight[0] = element.getKey().toString();
      weight[1] = element.getValue().toString();
      answer.add(weight);
    }


    return answer;
  }


}

