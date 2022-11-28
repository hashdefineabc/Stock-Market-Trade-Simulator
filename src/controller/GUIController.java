package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import model.IUserInterface;
import model.Operation;
import model.PortfolioType;
import view.BuyStocksView;
import view.CreateNewPortfolioView;
import view.HomeView;

public class GUIController implements IController, ActionListener {

  private IUserInterface user;
  private HomeView home;
  private CreateNewPortfolioView createNewPortfolioView;
  private Map<String, Runnable> actionMap;

  private BuyStocksView buyStock;

  public GUIController(IUserInterface user, HomeView view) {
    this.user = user;
    this.home = view;
    home.addActionListener(this);
  }

  @Override
  public void go() {
    actionMap = initializeMap();
  }

  private void initializeCreatePortfolio(Map<String, Runnable> actionMap) {
    actionMap.put("create", () -> {
      createNewPortfolioView = new CreateNewPortfolioView("create portfolio");
      disposeHomeSetCreateFrame(createNewPortfolioView, home, this);
    });

    actionMap.put("createPortfolio", () -> {
      String portfolioName = createNewPortfolioView.getInput().get(0);
      if (portfolioName.length() == 0) {
        createNewPortfolioView.setPopUp("empty portfolio name");
        return;
      }
      try {
        List<String[]> stockList = new ArrayList<>();
        int i=0;
        do {
          String[] s = this.takeStockInput();
          stockList.add(s);
        }
        while (this.addMoreStocksFromView());
        if (user.createNewPortfolio(portfolioName, stockList, PortfolioType.flexible)) {
          createNewPortfolioView.setPopUp("Portfolio " + portfolioName + " created successfully");
          createNewPortfolioView.clearField();
        } else {
          createNewPortfolioView.setPopUp("Portfolio was not saved. Try again");
        }
      }
      catch (IllegalArgumentException e) {
        createNewPortfolioView.setPopUp(e.getMessage());
      }
    });
    actionMap.put("homeFromCreatePortfolio", () -> {
      home = new HomeView("Home");
      disposeCreateWindowSetHome(home, createNewPortfolioView, this);
    });

  }

  private Map<String, Runnable> initializeMap() {
    Map<String, Runnable> actionMap = new HashMap<>();

    actionMap.put("exit", () -> {
      System.exit(0);
    });

    initializeCreatePortfolio(actionMap);

    actionMap.put("buyStocks", () -> {
      buyStock = new BuyStocksView("Buy");
      buyStock.addActionListener(this);
      buyStock.setLocation((this.createNewPortfolioView).getLocation());
      (this.createNewPortfolioView).dispose();
    });


    return actionMap;
  }

  private boolean addMoreStocksFromView() {
    return false;
  }

  private String[] takeStockInput() {
    String tickerNameFromUser = buyStock.getInput()[0];
    Double numUnits = Double.valueOf(buyStock.getInput()[1]);
    Double commission = Double.valueOf(buyStock.getInput()[3]);
    LocalDate transactionDate = LocalDate.parse(buyStock.getInput()[2]);

    if(tickerNameFromUser.isEmpty() || numUnits == null || commission == null) {
      buyStock.setStatus("Please enter all values");
    }


    String[] s = new String[6];
    s[0] = tickerNameFromUser;
    s[1] = buyStock.getInput()[1]; //num units
    s[2] = buyStock.getInput()[2]; //transactionDate
    s[3] = buyStock.getInput()[3]; //commission value

    Double transactionValue = user.getStockPriceFromDB(tickerNameFromUser, transactionDate);
    LocalTime currentTime = LocalTime.now();
    if (transactionDate.equals(LocalDate.now())
            && currentTime.isBefore(LocalTime.of(16, 0))) {
      buyStock.setStatus("Market is not closed today yet, "
              + "previous available closing price will be considered as your transaction price...");
      transactionValue = user.getStockPriceFromDB(tickerNameFromUser,
              transactionDate.minusDays(1));
      transactionDate = transactionDate.minusDays(1);
    }
    while (transactionValue == 0.0) {
//      buyStock.setPopUp("Market was closed on " + transactionDate + "\n Calculating price of previous date");
      transactionValue = user.getStockPriceFromDB(tickerNameFromUser,
              transactionDate.minusDays(1));
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

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */

  @Override
  public void actionPerformed(ActionEvent e) {
    actionMap.get(e.getActionCommand()).run();
  }
}
