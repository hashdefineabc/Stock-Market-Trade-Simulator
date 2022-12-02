package view;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TickerWeightPanel extends JPanel {

  private JTextField tickerNameTextField;
  private JTextField weightTextField;

  public TickerWeightPanel() {
    super();
    this.setLayout(new FlowLayout());

    //ticker Name panel

    JPanel tickerNamePanel = new JPanel();
    JLabel tickerNameLabel = new JLabel("Ticker Name: ");
    tickerNameTextField = new JTextField(15);
    tickerNamePanel.add(tickerNameLabel);
    tickerNamePanel.add(tickerNameTextField);

    //Weight for stock panel

    JPanel weightPanel = new JPanel();
    JLabel weightLabel = new JLabel("Weight for this stock (%) ");
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
