package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class CostBasisGUIView extends JFrame {
  private JComboBox portfolioNameComboBox;
  private JLabel portfolioNameLabel;

  private JLabel dateOfTransactionLabel;

  private JComboBox monthComboBox;
  private JComboBox dateComboBox;
  private JComboBox yearComboBox;
  private int selectedPortfolioIndex;
  private JLabel popUpMsg;
  private JButton viewCostBasisButton;
  private JButton cancelButton;


  public CostBasisGUIView(String title) {
    super(title);
    //Portfolio name panel
    JPanel portfolioNamePanel = new JPanel();
    portfolioNameLabel = new JLabel("Portfolio Name: ");

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
    dateOfTransactionLabel = new JLabel("Date of Transaction: ");
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
    for (int i = 2022; i > 2006; i=i-1) {
      years[j] = Integer.toString(i);
      j = j+1;
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
    viewCostBasisButton = new JButton("View Cost Basis");
    viewCostBasisButton.setActionCommand("viewCostBasisButton");

    cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand("cancelFromCostBasis");

    buttonPanel.add(viewCostBasisButton);
    buttonPanel.add(cancelButton);

    popUpMsg = new JLabel("");
    JPanel popUpPanel = new JPanel();
    popUpPanel.add(popUpMsg);

    JPanel costBasisWholePanel = new JPanel();
    costBasisWholePanel.setLayout(new GridLayout(3, 1));
    costBasisWholePanel.add(portfolioNamePanel);
    costBasisWholePanel.add(datePanel);

    this.add(costBasisWholePanel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//    this.setPreferredSize(new Dimension(450, 500));
    this.setVisible(true);
    this.pack();


  }

  public void setPopUp(String message) {
    JOptionPane.showMessageDialog(CostBasisGUIView.this, message, "Cost Basis", JOptionPane.INFORMATION_MESSAGE);
    popUpMsg.setText(message);
  }

  public void addActionListener(ActionListener listener) {
    viewCostBasisButton.addActionListener(listener);
    cancelButton.addActionListener(listener);
  }

  public String[] getInput() {
    String[] input = new String[2];

    String date = yearComboBox.getSelectedItem().toString() + "-" +
            monthComboBox.getSelectedItem().toString() + "-"+
            dateComboBox.getSelectedItem().toString();

    input[0] = date;

    return input;
  }

  public int getSelectedPortfolioIndex() {
    return selectedPortfolioIndex;
  }

  public void setErrorPopUp(String message) {
    JOptionPane.showMessageDialog(CostBasisGUIView.this, message, "Cost Basis", JOptionPane.ERROR_MESSAGE);
  }

  public void updateExistingPortfoliosList(List<String> existingPortfolios) {
    DefaultComboBoxModel tempComboBox = new DefaultComboBoxModel();
    for(String portfolio: existingPortfolios) {
      tempComboBox.addElement(portfolio);
    }

    this.portfolioNameComboBox.setModel(tempComboBox);
  }
}
