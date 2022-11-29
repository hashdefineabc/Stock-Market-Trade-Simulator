package view;

import java.awt.*;

import javax.swing.*;

import controller.GUIController;

public class InvestByWeightView extends JFrame {

  private JLabel enterAmount;
  private JLabel enterComm;
  private JTextField amount;
  private JTextField commission;
  private JPanel amountPanel;

  public InvestByWeightView(String title) {
    super(title);
    amountPanel = new JPanel();
    enterAmount = new JLabel("Enter the amount to invest in USD");
    enterComm = new JLabel("Enter the commission in USD");

    amount = new JTextField(15);
    commission = new JTextField(15);
    amountPanel.add(enterAmount);
    amountPanel.add(amount);
    amountPanel.add(enterComm);
    amountPanel.add(commission);

    this.add(amountPanel, BorderLayout.CENTER);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
    this.pack();
  }

  public void addActionListener(GUIController guiController) {

  }
}
