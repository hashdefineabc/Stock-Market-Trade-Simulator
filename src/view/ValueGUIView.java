package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

/**
 *The ValueGUIView represents the value calculation feature of the application.
 * It extends the JFrame class in order to design the buttons for the create operation.
 *This lets the user create a new portfolio by entering the details of it.
 * All the buttons and textfields necessary to take input from the user are implemented here.
 */
public class ValueGUIView extends JFrame {
  private JComboBox portfolioNameComboBox;
  private JComboBox monthComboBox;
  private JComboBox dateComboBox;
  private JComboBox yearComboBox;
  private int selectedPortfolioIndex;
  private JLabel popUpMsg;
  private JButton viewValueButton;
  private JButton cancelButton;

  /**
   * Creates a new  instance of 'ValueGUIView' class
   * @param title = the title we want this frame to display
   */

  public ValueGUIView(String title) {
    super(title);
    //Portfolio name panel
    JPanel portfolioNamePanel = new JPanel();
    JLabel portfolioNameLabel = new JLabel("Portfolio Name: ");

    portfolioNameComboBox = new JComboBox();
    portfolioNamePanel.add(portfolioNameLabel);
    portfolioNamePanel.add(portfolioNameComboBox);

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        selectedPortfolioIndex = portfolioNameComboBox.getSelectedIndex();
      }
    };
    portfolioNameComboBox.addActionListener(actionListener);

    //date panel

    JPanel datePanel = new JPanel();
    JLabel dateOfTransactionLabel = new JLabel("Date of Transaction: ");
    JLabel yearLabel = new JLabel("Year:");
    JLabel monthLabel = new JLabel("Month:");
    JLabel dateLabel = new JLabel("Date:");

    ActionListener listener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        CalculateDate.setDate(monthComboBox, dateComboBox);
      }
    };

    //handle month
    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08",
              "09", "10", "11", "12"};
    monthComboBox = new JComboBox(months);
    monthComboBox.addActionListener(listener);

    //handle date
    String[] dates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
            , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
            , "28", "29", "30", "31"};
    dateComboBox = new JComboBox(dates);

    //handle year
    String[] years = new String[30];
    int j = 0;
    for (int i = 2022; i > 2006; i = i - 1) {
      years[j] = Integer.toString(i);
      j = j + 1;
    }
    yearComboBox = new JComboBox(years);

    datePanel.add(dateOfTransactionLabel);
    datePanel.add(monthLabel);
    datePanel.add(monthComboBox);
    datePanel.add(dateLabel);
    datePanel.add(dateComboBox);
    datePanel.add(yearLabel);
    datePanel.add(yearComboBox);

    JPanel buttonPanel = new JPanel();
    viewValueButton = new JButton("View Value");
    viewValueButton.setActionCommand("viewValueButton");

    cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand("cancelFromValue");

    buttonPanel.add(viewValueButton);
    buttonPanel.add(cancelButton);

    popUpMsg = new JLabel("");
    JPanel popUpPanel = new JPanel();
    popUpPanel.add(popUpMsg);

    JPanel valueWholePanel = new JPanel();
    valueWholePanel.setLayout(new GridLayout(3, 1));
    valueWholePanel.add(portfolioNamePanel);
    valueWholePanel.add(datePanel);

    this.add(valueWholePanel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
    this.pack();


  }

  public void setPopUp(String message) {
    JOptionPane.showMessageDialog(ValueGUIView.this, message, "Value",
            JOptionPane.INFORMATION_MESSAGE);
    popUpMsg.setText(message);
  }

  public void addActionListener(ActionListener listener) {
    viewValueButton.addActionListener(listener);
    cancelButton.addActionListener(listener);
  }

  public String[] getInput() {
    String[] input = new String[2];

    String date = yearComboBox.getSelectedItem().toString() + "-" +
            monthComboBox.getSelectedItem().toString() + "-" +
            dateComboBox.getSelectedItem().toString();

    input[0] = date;

    return input;
  }

  /**
   * gettting the user input for the selected portfolio.
   * @return = user choice
   */
  public int getSelectedPortfolioIndex() {
    return selectedPortfolioIndex;
  }


  /**
   * letting the user know that theere was some error in the strategy
   * @param message = msg to be displayed to the user.
   */

  public void setErrorPopUp(String message) {
    JOptionPane.showMessageDialog(ValueGUIView.this, message, "Value", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * updating the existing portfolios as per the strategies.
   * @param existingPortfolios = list of existing portfolios.
   */
  public void updateExistingPortfoliosList(List<String> existingPortfolios) {
    DefaultComboBoxModel tempComboBox = new DefaultComboBoxModel();
    for (String portfolio : existingPortfolios) {
      tempComboBox.addElement(portfolio);
    }

    this.portfolioNameComboBox.setModel(tempComboBox);
  }
}
