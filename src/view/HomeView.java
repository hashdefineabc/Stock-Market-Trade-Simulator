package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class HomeView extends JFrame {

  private JButton create;
  private JButton invest;
  private JButton dca;
  private JButton exit;

  public HomeView(String s) {
    super(s);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());

    // Create a new Portfolio
    create = new JButton("Create a new Portfolio");
    create.setActionCommand("create");
    panel.add(create);

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
    this.setVisible(true);
    this.pack();
  }
  public void addActionListener(ActionListener listener) {
    create.addActionListener(listener);
    invest.addActionListener(listener);
    dca.addActionListener(listener);
    exit.addActionListener(listener);

  }
}
