package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *The HomeView represents the home page of the application.
 * It extends the JFrame class in order to design the buttons for the applications' operations.
 * It provides the user options to:
 * 1) create a new portfolio
 * 2) view the composition of a portfolio
 * 3) buy/sell stocks from a portfolio.
 * 4) calculate the cost basis of a portfolio.
 * 5) calculate the value of a portfolio.
 * 6) invest in  a portfolio by specifying the weights.
 * 7) apply dollar cost averaging to a portfolio.
 *
 */
public class HomeView extends JFrame {

  private JButton create;
  private JButton invest;
  private JButton dca;
  private JButton exit;
  private JButton buyStocks;
  private JButton sellStocks;
  private JButton costBasis;
  private JButton value;
  private JButton composition;
  private JButton uploadPortfolio;
  private JButton displayChart;

  /**
   * Creates a new instance of the HomeView class.
   * @param s = the title/name we want to specify for this class.
   */

  public HomeView(String s) {
    super(s);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(10, 1));

    // Create a new Portfolio
    create = new JButton("Create a new Portfolio");
    create.setActionCommand("create");
    panel.add(create);


    // Buy stocks
    buyStocks = new JButton("Buy Stocks");
    buyStocks.setActionCommand("buyStocks");
    panel.add(buyStocks);

    // Sell stocks
    sellStocks = new JButton("Sell Stocks");
    sellStocks.setActionCommand("sellStocks");
    panel.add(sellStocks);


    // cost basis
    costBasis = new JButton("Check Cost Basis");
    costBasis.setActionCommand("costBasis");
    panel.add(costBasis);

    // value
    value = new JButton("Check Value");
    value.setActionCommand("valueButtonHome");
    panel.add(value);


    // composition
    composition = new JButton("View Composition");
    composition.setActionCommand("compositionButtonHome");
    panel.add(composition);

    // upload portfolio from file
    uploadPortfolio = new JButton("Upload a portfolio from file");
    uploadPortfolio.setActionCommand("uploadButtonHome");
    panel.add(uploadPortfolio);

    //invest in a portfolio by specifying weights
    invest = new JButton("Invest by percentage");
    invest.setActionCommand("investButtonHome");
    panel.add(invest);

    //set dollar cost averaging for a portfolio
    dca = new JButton("Set Dollar Cost Averaging for a portfolio");
    dca.setActionCommand("dcaButtonHomeMain");
    panel.add(dca);

    //display chart
    displayChart = new JButton("Display performance chart");
    displayChart.setActionCommand("displayChartHome");
    panel.add(displayChart);


    // Exit
    JPanel exitbuttonPanel = new JPanel();

    exit = new JButton("Exit");
    exit.setActionCommand("exit");
    exitbuttonPanel.add(exit);

    this.getContentPane().add(panel);
    this.add(exitbuttonPanel, BorderLayout.PAGE_END);
    this.setLocation(100, 100);
    this.setVisible(true);
    this.pack();
  }


  /**
   * The ActionListener is notified whenever you click on the button or menu item displayed on the
   * screen.
   * Here this listener is added for all the buttons on the view.
   * @param listener = listener object for this class.
   */

  public void addActionListener(ActionListener listener) {
    create.addActionListener(listener);
    invest.addActionListener(listener);
    dca.addActionListener(listener);
    exit.addActionListener(listener);
    buyStocks.addActionListener(listener);
    sellStocks.addActionListener(listener);
    costBasis.addActionListener(listener);
    value.addActionListener(listener);
    composition.addActionListener(listener);
    uploadPortfolio.addActionListener(listener);
    displayChart.addActionListener(listener);
  }
}
