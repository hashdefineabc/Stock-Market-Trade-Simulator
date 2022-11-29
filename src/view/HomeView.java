package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class HomeView extends JFrame {

  private JButton create;
  private JButton exit;
  private JButton buyStocks;
  private JButton sellStocks;
  private JButton costBasis;

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

    // Exit
    exit = new JButton("Exit");
    exit.setActionCommand("exit");
    panel.add(exit);

    this.getContentPane().add(panel);
    this.setLocation(200, 200);
    this.setVisible(true);
    this.pack();
  }
  public void addActionListener(ActionListener listener) {
    create.addActionListener(listener);
    exit.addActionListener(listener);
    buyStocks.addActionListener(listener);
    sellStocks.addActionListener(listener);
    costBasis.addActionListener(listener);
  }
}
