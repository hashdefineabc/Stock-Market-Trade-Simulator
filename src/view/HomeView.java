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

    //invest in a portfolio by specifying weights
    invest = new JButton("Invest by percentage");
    invest.setActionCommand("invest");
    panel.add(invest);

    //set dollar cost averaging for a portfolio
    dca = new JButton("Set Dollar Cost Averaging for a portfolio");
    dca.setActionCommand("dca");
    panel.add(dca);

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
    invest.addActionListener(listener);
    dca.addActionListener(listener);
    exit.addActionListener(listener);
    buyStocks.addActionListener(listener);
    sellStocks.addActionListener(listener);
  }
}
