package view;

import java.awt.*;

import javax.swing.*;

public class TickerWeightPanel extends JPanel {

  private JPanel tickerNamePanel;
  private JLabel tickerNameLabel;
  private JTextField tickerNameTextField;

  private JPanel weightPanel;
  private JLabel weightLabel;
  private JTextField weightTextField;

  public TickerWeightPanel() {
    super();
    this.setLayout(new FlowLayout());

    //ticker Name panel

    tickerNamePanel = new JPanel();
    tickerNameLabel = new JLabel("Ticker Name: ");
    tickerNameTextField = new JTextField(15);
    tickerNamePanel.add(tickerNameLabel);
    tickerNamePanel.add(tickerNameTextField);

    //Weight for stock panel

    weightPanel = new JPanel();
    weightLabel = new JLabel("Weight for this stock (%) ");
    weightTextField = new JTextField(15);
    weightPanel.add(weightLabel);
    weightPanel.add(weightTextField);


    this.setLayout(new FlowLayout());
    this.add(tickerNamePanel);
    this.add(weightPanel);

    this.setVisible(true);
  }

  public String getTickerNameTextField() {
    return tickerNameTextField.getText();
  }

  public String getWeightTextField() {
    return weightTextField.getText();
  }

  public void clear() {
    tickerNameTextField.setText("");
    weightTextField.setText("");
  }
}
