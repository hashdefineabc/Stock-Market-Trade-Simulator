package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import controller.commands.BuySell;
import controller.commands.DCA;
import model.IFlexiblePortfolio;
import model.IUserInterface;
import model.InvestmentType;
import model.IstockModel;
import model.Operation;
import model.PortfolioType;
import model.Stock;
import view.BuySellStocksView;
import view.CompositionGUIView;
import view.CostBasisGUIView;
import view.CreateNewPortfolioView;
import view.DCAGuiView;
import view.DisplayStocks;
import view.HomeView;
import view.InvestByWeightView;
import view.UploadFromFileGUIView;
import view.ValueGUIView;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

public class GUIController implements IController, ActionListener {

  private IUserInterface user;
  private HomeView home;
  private CreateNewPortfolioView createNewPortfolioView;
  private Map<String, Runnable> actionMap;

  private BuySellStocksView buySellStock;
  private CostBasisGUIView costBasisView;
  private ValueGUIView valueView;
  private CompositionGUIView compositionView;
  private UploadFromFileGUIView uploadFromFileGUIView;
  private DisplayStocks displayComposition;
  private InvestByWeightView investView;
  private DCAGuiView dcaView;
  List<String[]> stockList;
  List<String> existingPortfolios;

  public GUIController(IUserInterface user, HomeView view) {
    this.user = user;
    this.home = view;
    home.addActionListener(this);
  }

  @Override
  public void go() {
    actionMap = initializeMap();
  }

  private void CreatingPortfolio(Map<String, Runnable> actionMap) {
    stockList = new ArrayList<>();
    actionMap.put("create", () -> {
      createNewPortfolioView = new CreateNewPortfolioView("create portfolio");
      disposeHomeSetCreateFrame(createNewPortfolioView, home, this);
    });

    actionMap.put("createPortfolio", () -> {
      String portfolioName = createNewPortfolioView.getInput().get(0);
      if (portfolioName.length() == 0) {
        createNewPortfolioView.setPopUp("Enter portfolio name");
        return;
      }

//      String[] s = this.takeStockInput();
//      if(s.equals(null))
//        return;
//      stockList.add(s);

      try {
        if (user.createNewPortfolio(portfolioName, stockList, PortfolioType.flexible)) {
          createNewPortfolioView.setPopUp("Portfolio " + portfolioName + " created successfully");
          createNewPortfolioView.clearField();
        } else {
          createNewPortfolioView.setPopUp("Portfolio name already exists. Try again");
        }
      }
      catch (IllegalArgumentException e) {
        createNewPortfolioView.setPopUp("Portfolio was not created, Try again");
      }
    });
    actionMap.put("homeFromCreatePortfolio", () -> {
      home = new HomeView("Home");
      disposeCreateWindowSetHome(home, createNewPortfolioView, this);
    });

  }

  private void buyingStocks(Map<String, Runnable> actionMap) {
    actionMap.put("buyStocks", () -> {
      buySellStock = new BuySellStocksView("Buy Stocks");
      buySellStock.setBuyOrSell(true); // buy operation

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      buySellStock.updateExistingPortfoliosList(existingPortfolios);

//      String[] s = this.takeStockInput();
//      if(s.equals(null))
//        return;
//      stockList.add(s);

      //hide home and display buystock
      buySellStock.addActionListener(this);
      buySellStock.setLocation(home.getLocation());
      home.dispose();
    });
  }

  private void sellingStocks(Map<String, Runnable> actionMap) {
    actionMap.put("sellStocks", () -> {
      buySellStock = new BuySellStocksView("Sell Stocks");
      buySellStock.setBuyOrSell(false); // sell operation

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      buySellStock.updateExistingPortfoliosList(existingPortfolios);

//      String[] s = this.takeStockInput();
//      if(s.equals(null))
//        return;
//      stockList.add(s);

      //hide home and display sellStock
      buySellStock.addActionListener(this);
      buySellStock.setLocation(home.getLocation());
      home.dispose();
    });
  }

  private void saveStock(Map<String, Runnable> actionMap) {
    BuySell buySellCmd = new BuySell(user);
    actionMap.put("saveStock", () -> {
      String[] stockDetails = new String[6];
      stockDetails = this.takeStockInput();
      if(stockDetails == null) {
        return;
      }
      stockDetails[5] = buySellStock.getBuyOrSell() ? Operation.BUY.toString() : Operation.SELL.toString();

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      buySellStock.updateExistingPortfoliosList(existingPortfolios);

      int portfolioIndex = buySellStock.getSelectedPortfolioIndex();
      List<String[]> dataToWrite = null;

      try {
        if ((stockDetails[5] == Operation.SELL.toString()) && (!buySellCmd.validateSellOperation(portfolioIndex+1, stockDetails))) {
          buySellStock.setPopUp("Cannot sell stock");
          buySellStock.clear();
          return;
        }
      } catch (Exception e) {
        return;
      }

      try {
        if(!user.validateNumUnits(stockDetails[1])) {
          buySellStock.setPopUp("Number of units cannot be negative or fractional");
          buySellStock.clear();
          return;
        }
      } catch (Exception e) {
        return;
      }

      if(!user.validateCommissionValue(stockDetails[3])) {
        buySellStock.setPopUp("Commission value cannot be negative");
        buySellStock.clear();
        return;
      }


      try {
        Stock newStock = Stock.getBuilder().tickerName(stockDetails[0])
                .numOfUnits(Double.valueOf(stockDetails[1]))
                .transactionDate(LocalDate.parse((stockDetails[2])))
                .commission(Double.valueOf(stockDetails[3]))
                .transactionPrice(Double.valueOf(stockDetails[4]))
                .buyOrSell(Operation.valueOf(stockDetails[5])).build();


        IFlexiblePortfolio pf = user.getFlexiblePortfoliosCreatedObjects().get(portfolioIndex);
        pf.addOrSellStocks(newStock);
        dataToWrite = pf.toListOfString();
        user.savePortfolioToFile(dataToWrite, pf.getNameOfPortFolio().strip()
                .split(".csv")[0], PortfolioType.flexible);
        buySellStock.displaySuccess("Transaction Successful");
        buySellStock.clear();
      } catch (IllegalArgumentException e) {
        throw new RuntimeException(e);
      }


    });
  }

  private void doneFromInvestWeights(Map<String, Runnable> actionMap) {
    actionMap.put("doneFromInvestWeights", () -> {
      String[] investInput = new String[13];
      HashMap<String,Double> weights = new HashMap<>();
      investInput = this.takeInvestInput(weights);
      if(investInput == null) {
        return;
      }

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      investView.updateExistingPortfoliosList(existingPortfolios);

      int portfolioIndex = investView.getSelectedPortfolioIndex() + 1;

      Double amount = Double.valueOf(investInput[0]);
      Double commissionValue = Double.valueOf(investInput[1]);
      LocalDate dateToBuy = LocalDate.parse(investInput[2]);

      LocalDate lastTxnDate =  user.calculateTxns(dateToBuy,dateToBuy,0,
              weights,amount,commissionValue,portfolioIndex, InvestmentType.InvestByWeights);

      user.acceptStrategyFromUser(portfolioIndex,amount,commissionValue,dateToBuy,dateToBuy,weights,
              InvestmentType.InvestByWeights,0,lastTxnDate);
      investView.setSuccessMsg("Instructions saved for this Portfolio! Money will be invested as per "
              + "them");
      investView.clear();
    });
  }

  private void doneFromDCA(Map<String, Runnable> actionMap) {
    actionMap.put("doneFromDCA", () -> {
      String[] dcaInput = new String[14];
      HashMap<String,Double> weights = new HashMap<>();
      dcaInput = this.takeDCAInput(weights);
      if(dcaInput == null) {
        return;
      }
      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      dcaView.updateExistingPortfoliosList(existingPortfolios);

      int portfolioIndex = dcaView.getSelectedPortfolioIndex();
      portfolioIndex++;

      Double amount = Double.valueOf(dcaInput[0]);
      Double commissionValue = Double.valueOf(dcaInput[1]);
      LocalDate strategyStart = LocalDate.parse(dcaInput[2]);
      LocalDate strategyEnd = LocalDate.parse(dcaInput[3]);
      Integer daysToInvest = Integer.valueOf(dcaInput[4]);

      LocalDate lastTxnDate = user.calculateTxns(strategyStart,strategyEnd,daysToInvest,weights,
              amount,commissionValue,portfolioIndex, InvestmentType.DCA);
      user.acceptStrategyFromUser(portfolioIndex,amount,commissionValue,strategyStart,strategyEnd,weights,
              InvestmentType.DCA, daysToInvest, lastTxnDate);
      dcaView.setSuccessMsg("Instructions saved for this Portfolio! Money will be invested as per "
              + "them");
      dcaView.clear();
    });
  }
  private void cancelFromBuy(Map<String, Runnable> actionMap) {
    actionMap.put("cancelFromBuy", () -> {
      home = new HomeView("Home");

      //hide buy and display home
      home.addActionListener(this);
      home.setLocation(buySellStock.getLocation());
      this.buySellStock.dispose();
    });
  }

  private void cancelFromCostBasis(Map<String, Runnable> actionMap) {
    actionMap.put("cancelFromCostBasis", () -> {
      home = new HomeView("Home");

      //hide cost basis window and display home
      home.addActionListener(this);
      home.setLocation(costBasisView.getLocation());
      this.costBasisView.dispose();
    });
  }

  private void cancelFromInvestWeights(Map<String, Runnable> actionMap) {
    actionMap.put("cancelFromInvestWeights", () -> {
      home = new HomeView("Home");

      //hide invest weights and display home
      home.addActionListener(this);
      home.setLocation(investView.getLocation());
      this.investView.dispose();
    });
  }
  private void cancelFromDCA(Map<String, Runnable> actionMap) {
    actionMap.put("cancelFromDCA", () -> {
      home = new HomeView("Home");

      //hide invest weights and display home
      home.addActionListener(this);
      home.setLocation(dcaView.getLocation());
      this.dcaView.dispose();
    });
  }

  private void cancelFromValue(Map<String, Runnable> actionMap) {
    actionMap.put("cancelFromValue", () -> {
      home = new HomeView("Home");

      //hide cost basis window and display home
      home.addActionListener(this);
      home.setLocation(valueView.getLocation());
      this.valueView.dispose();
    });
  }

  private void cancelFromComposition(Map<String, Runnable> actionMap) {
    actionMap.put("cancelFromComposition", () -> {
      home = new HomeView("Home");

      //hide cost basis window and display home
      home.addActionListener(this);
      home.setLocation(compositionView.getLocation());
      this.compositionView.dispose();
    });
  }

  private void okFromDisplayStocks(Map<String, Runnable> actionMap) {
    actionMap.put("okFromDisplayStocks", () -> {
      compositionView = new CompositionGUIView("Composition");
      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      compositionView.updateExistingPortfoliosList(existingPortfolios);

      //hide cost basis window and display pick portfolio for composition view
      compositionView.addActionListener(this);
      compositionView.setLocation(displayComposition.getLocation());
      this.displayComposition.dispose();
    });
  }

  private Map<String, Runnable> initializeMap() {
    Map<String, Runnable> actionMap = new HashMap<>();

    actionMap.put("exit", () -> {
      System.exit(0);
    });

    CreatingPortfolio(actionMap);
    buyingStocks(actionMap);
    cancelFromBuy(actionMap);
    saveStock(actionMap);
    sellingStocks(actionMap);
    costBasisFromHomeButton(actionMap);
    viewCostBasisButton(actionMap);
    cancelFromCostBasis(actionMap);
    valueButtonHome(actionMap);
    viewValue(actionMap);
    cancelFromValue(actionMap);
    compositionButtonHome(actionMap);
    viewCompositionButton(actionMap);
    cancelFromComposition(actionMap);
    okFromDisplayStocks(actionMap);
    uploadFromHomeButton(actionMap);
    investButtonHome(actionMap);
    dcaButtonHome(actionMap);
    doneFromInvestWeights(actionMap);
    doneFromDCA(actionMap);
    cancelFromInvestWeights(actionMap);
    cancelFromDCA(actionMap);

    return actionMap;
  }

  private void investButtonHome(Map<String, Runnable> actionMap) {
    actionMap.put("investButtonHome", () -> {
      investView = new InvestByWeightView("Invest by weight");

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      investView.updateExistingPortfoliosList(existingPortfolios);

      int portfolioIndex = investView.getSelectedPortfolioIndex();

      //hide home and display invest
      investView.addActionListener(this);
      investView.setLocation(home.getLocation());
      home.dispose();
    });
  }

  private void dcaButtonHome(Map<String, Runnable> actionMap) {
    actionMap.put("dcaButtonHome", () -> {
      dcaView = new DCAGuiView("Dollar Cost Averaging");

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      dcaView.updateExistingPortfoliosList(existingPortfolios);

      int portfolioIndex = dcaView.getSelectedPortfolioIndex();

      //hide home and display dca
      dcaView.addActionListener(this);
      dcaView.setLocation(home.getLocation());
      home.dispose();
    });
  }

  private void viewCostBasisButton(Map<String, Runnable> actionMap) {
    actionMap.put("viewCostBasisButton", () -> {

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      costBasisView.updateExistingPortfoliosList(existingPortfolios);

      int portfolioIndex = costBasisView.getSelectedPortfolioIndex();

      String costBasisDate = costBasisView.getInput()[0];

      try {
        Double costBasis = user.calculateCostBasisOfPortfolio(portfolioIndex+1,
                PortfolioType.flexible, LocalDate.parse(costBasisDate));
        costBasisView.setPopUp("Cost Basis on " + costBasisDate + " is " + String.format("%.2f", costBasis) + " USD");
      }
      catch (Exception e) {
        costBasisView.setErrorPopUp("Cost Basis couldn't be calculated!!!\nPlease try again...");
      }
    });
  }

  private void viewValue(Map<String, Runnable> actionMap) {
    actionMap.put("viewValueButton", () -> {

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      valueView.updateExistingPortfoliosList(existingPortfolios);

      int portfolioIndex = valueView.getSelectedPortfolioIndex();

      String valueDate = valueView.getInput()[0];

      try {
        Double value = user.calculateValueOfPortfolio(portfolioIndex+1, LocalDate.parse(valueDate), PortfolioType.flexible);

        if (value == -1) {
          valueView.setErrorPopUp("Value cannot be calculated for a date prior to portfolio creation");
        } else {
          valueView.setPopUp("Value on " + valueDate + " is " + String.format("%.2f", value) + " USD");
        }
      }
      catch (Exception e) {
        valueView.setErrorPopUp("Value couldn't be calculated!!!\nPlease try again");
      }
    });
  }

  private void viewCompositionButton(Map<String, Runnable> actionMap) {
    actionMap.put("viewCompositionButton", () -> {

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      compositionView.updateExistingPortfoliosList(existingPortfolios);

      int portfolioIndex = compositionView.getSelectedPortfolioIndex();

      String compositionDate = compositionView.getInput()[0];

      try {
        List<IstockModel> stocksToDisplay = user.displayStocksOfPortFolio(portfolioIndex+1,
                PortfolioType.flexible, LocalDate.parse(compositionDate));
        if (stocksToDisplay.size() == 0) {
          compositionView.setPopUp("No stocks are present in the portfolio at this date");
        } else {
          displayComposition = new DisplayStocks(stocksToDisplay);
          displayComposition.addActionListener(this);
          displayComposition.setLocation(compositionView.getLocation());
          this.compositionView.dispose();
        }}
      catch (Exception e) {
        compositionView.setErrorPopUp("Composition cannot be viewed at the moment!!!\nPlease try again...");
      }
    });
  }

  private void costBasisFromHomeButton(Map<String, Runnable> actionMap) {
    actionMap.put("costBasis", () -> {
      costBasisView = new CostBasisGUIView("Cost Basis");

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      costBasisView.updateExistingPortfoliosList(existingPortfolios);

      //hide home and display costBasis
      costBasisView.addActionListener(this);
      costBasisView.setLocation(home.getLocation());
      home.dispose();
    });
  }

  private void valueButtonHome(Map<String, Runnable> actionMap) {
    actionMap.put("valueButtonHome", () -> {
      valueView = new ValueGUIView("Value");

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      valueView.updateExistingPortfoliosList(existingPortfolios);

      //hide home and display costBasis
      valueView.addActionListener(this);
      valueView.setLocation(home.getLocation());
      home.dispose();
    });
  }

  private void compositionButtonHome(Map<String, Runnable> actionMap) {
    actionMap.put("compositionButtonHome", () -> {
      compositionView = new CompositionGUIView("Composition");

      existingPortfolios = user.getPortfolioNamesCreated(PortfolioType.flexible);
      compositionView.updateExistingPortfoliosList(existingPortfolios);

      //hide home and display composition
      compositionView.addActionListener(this);
      compositionView.setLocation(home.getLocation());
      home.dispose();
    });
  }

  private void uploadFromHomeButton(Map<String, Runnable> actionMap) {
    actionMap.put("uploadButtonHome", () -> {

      uploadFromFileGUIView = new UploadFromFileGUIView();

      File fileUploaded = uploadFromFileGUIView.FilePopUp();

      if(fileUploaded == null){
        uploadFromFileGUIView.showError("File couldn't be uploaded");
        return;
      }

      Path source = null;
      try {
        source = fileUploaded.getCanonicalFile().toPath();
      } catch (IOException e) {
        uploadFromFileGUIView.showError("File couldn't be uploaded");
      }
      String sourceFileName = fileUploaded.getName();

      Path userDirectory = Path.of(new File("").getAbsolutePath());
      String folderPath = userDirectory + File.separator + "PortFolioComposition" + File.separator + "FlexiblePortfolios" + File.separator+sourceFileName;
      Path target = Path.of(folderPath);

      File newFile = new File(folderPath);
      if(newFile.exists()) {
        uploadFromFileGUIView.showError("File name already exists");
        return;
      }

      try {
        Files.copy(source, target, COPY_ATTRIBUTES);
      } catch (IOException e) {
        uploadFromFileGUIView.showError("File couldn't be uploaded");
      }
      uploadFromFileGUIView.setPopUp();

      uploadFromFileGUIView.addActionListener(this);
      uploadFromFileGUIView.setLocation(home.getLocation());
    });
  }

  private String[] takeInvestInput(HashMap<String, Double> tickerWeights) {
    String[] input = new String[13];
    input = investView.getInput();
    String amountFromUser = input[0];
    if(Objects.equals(amountFromUser, "")) {
      investView.setWarning("Enter amount");
      return null;
    }
    try {
      Double.parseDouble(amountFromUser);
    }
    catch (NumberFormatException e) {
      investView.setWarning("Please enter a valid amount");
      return null;
    }

    String commissionFromUser = input[1];
    if(Objects.equals(commissionFromUser, "")) {
      investView.setWarning("Enter commission");
      return null;
    }
    try {
      Double.parseDouble(commissionFromUser);
    }
    catch (NumberFormatException e) {
      investView.setWarning("Please enter a valid commission value");
      return null;
    }

    double[] weights = new double[5];
    int j=0;

    String tickerName = null;
    Double weight = null;
    int flag = 0;

    for(int i=3; i<13; i++) {
      if(i % 2 != 0) { //validate ticker Name
        if(Objects.equals(input[i], ""))
          break;
        if(!user.isTickerValid(input[i])) {
          investView.setWarning("Ticker name invalid");
          return null;
        }
        tickerName = input[i];
        flag = 0;
      }
      else if(i%2 == 0 ) { //validate weights
        if(!user.isDoubleValid(input[i])) {
          investView.setWarning("Enter a valid weight");
          return null;
        }
        weights[j++] = Double.valueOf(input[i]);
        weight = Double.valueOf(input[i]);
        flag = 1;
      }
      if(flag == 1)
        tickerWeights.put(tickerName, weight);
    }
    if(!user.validateWeightsForInvestment(weights)) {
      investView.setWarning("Total weights of all stocks should be 100...");
      return null;
    }

    return input;
  }

  private String[] takeDCAInput(HashMap<String, Double> tickerWeights) {
    String[] input = new String[15];
    input = dcaView.getInput();
    String amountFromUser = input[0];
    if(Objects.equals(amountFromUser, "")) {
      dcaView.setWarning("Enter amount");
      return null;
    }
    try {
      Double.parseDouble(amountFromUser);
    }
    catch (NumberFormatException e) {
      dcaView.setWarning("Please enter a valid amount");
      return null;
    }

    String commissionFromUser = input[1];
    if(Objects.equals(commissionFromUser, "")) {
      dcaView.setWarning("Enter commission");
      return null;
    }
    try {
      Double.parseDouble(commissionFromUser);
    }
    catch (NumberFormatException e) {
      dcaView.setWarning("Please enter a valid commission value");
      return null;
    }

    double[] weights = new double[5];
    int j=0;

    String tickerName = null;
    Double weight = null;
    int flag = 0;

    for(int i=5; i<15; i++) {
      if(i % 2 != 0) { //validate ticker Name
        if(Objects.equals(input[i], ""))
          break;
        if(!user.isTickerValid(input[i])) {
          dcaView.setWarning("Ticker name invalid");
          return null;
        }
        tickerName = input[i];
        flag = 0;
      }
      else if(i%2 == 0 ) { //validate weights
        if(!user.isDoubleValid(input[i])) {
          dcaView.setWarning("Enter a valid weight");
          return null;
        }
        weights[j++] = Double.valueOf(input[i]);
        weight = Double.valueOf(input[i]);
        flag = 1;
      }
      if(flag == 1)
        tickerWeights.put(tickerName, weight);
    }

    if(!user.validateWeightsForInvestment(weights)) {
      dcaView.setWarning("Total weights of all stocks should be 100...");
      return null;
    }

    return input;
  }

  private String[] takeStockInput() {
    String tickerNameFromUser = buySellStock.getInput()[0];
    if(Objects.equals(tickerNameFromUser, "")) {
      buySellStock.setPopUp("Enter ticker name");
      return null;
    }
    if(!user.isTickerValid(tickerNameFromUser)) {
      buySellStock.setPopUp("Ticker name invalid");
      buySellStock.clear();
      return null;
    }
    if(Objects.equals(buySellStock.getInput()[1], "")) {
      buySellStock.setPopUp("Enter number of units");
      return null;
    }
    try {
      Double.parseDouble(buySellStock.getInput()[1]);
    }
    catch (NumberFormatException e) {
      buySellStock.setPopUp("Please enter a valid number of units");
      return null;
    }

    if(Objects.equals(buySellStock.getInput()[3], "")) {
      buySellStock.setPopUp("Enter commission value");
      return null;
    }
    try {
      Double.parseDouble(buySellStock.getInput()[3]);
    }
    catch (NumberFormatException e) {
      buySellStock.setPopUp("Please enter a valid commission value");
      return null;
    }

    LocalDate transactionDate = LocalDate.parse(buySellStock.getInput()[2]);

    String[] s = new String[6];
    s[0] = tickerNameFromUser;
    s[1] = buySellStock.getInput()[1]; //num units
    s[2] = buySellStock.getInput()[2]; //transactionDate
    s[3] = buySellStock.getInput()[3]; //commission value

    Double transactionValue = user.getStockPriceFromDB(tickerNameFromUser, transactionDate);
    LocalTime currentTime = LocalTime.now();
    if (transactionDate.equals(LocalDate.now())
            && currentTime.isBefore(LocalTime.of(16, 0))) {
      buySellStock.setPopUp("Market is not closed today yet, "
              + "previous available closing price will be considered as your transaction price...");
      transactionValue = user.getStockPriceFromDB(tickerNameFromUser,
              transactionDate.minusDays(1));
      transactionDate = transactionDate.minusDays(1);
    }
    while (transactionValue == 0.0) {
//      buyStock.setPopUp("Market was closed on " + transactionDate + "\n Calculating price of previous date");
      transactionValue = user.getStockPriceFromDB(tickerNameFromUser, transactionDate.minusDays(1));
      transactionDate = transactionDate.minusDays(1);
    }
//    buyStock.setPopUp("Price of the stock considered is of the date " + transactionDate);
    s[4] = String.valueOf(transactionValue);
    s[5] = String.valueOf(Operation.BUY); //indicates shares are bought

    return s;
  }

  private void disposeCreateWindowSetHome(HomeView home, CreateNewPortfolioView createNewPortfolioView, ActionListener listener) {
    home.addActionListener(listener);
    home.setLocation(createNewPortfolioView.getLocation());
    createNewPortfolioView.dispose();
  }

  private void disposeHomeSetCreateFrame(CreateNewPortfolioView createFrame, HomeView home,
                                ActionListener listener) {
    createFrame.addActionListener(listener);
    (createFrame).setLocation(home.getLocation());
    home.dispose();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    actionMap.get(e.getActionCommand()).run();
  }
}
