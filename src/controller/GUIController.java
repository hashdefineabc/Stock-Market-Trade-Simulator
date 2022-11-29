package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import controller.commands.BuySell;
import model.IFlexiblePortfolio;
import model.IUserInterface;
import model.Operation;
import model.PortfolioType;
import model.Stock;
import view.BuySellStocksView;
import view.CostBasisGUIView;
import view.CreateNewPortfolioView;
import view.HomeView;

public class GUIController implements IController, ActionListener {

  private IUserInterface user;
  private HomeView home;
  private CreateNewPortfolioView createNewPortfolioView;
  private Map<String, Runnable> actionMap;

  private BuySellStocksView buySellStock;
  private CostBasisGUIView costBasisView;
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
      if(stockDetails.equals(null)) {
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

    return actionMap;
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
        costBasisView.setPopUp("Cost Basis on " + costBasisDate + " is " + costBasis);
      }
      catch (Exception e) {
        costBasisView.setErrorPopUp("Cost Basis couldn't be generated!!!");
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

    if(Objects.equals(buySellStock.getInput()[3], "")) {
      buySellStock.setPopUp("Enter commission value");
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
    home.setLocation((createNewPortfolioView).getLocation());
    (createNewPortfolioView).dispose();
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
