package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

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

    // Exit
    exit = new JButton("Exit");
    exit.setActionCommand("exit");
    panel.add(exit);

    this.getContentPane().add(panel);
    this.setLocation(100, 100);
    this.setVisible(true);
    this.pack();
  }
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
  }
}
