package view;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;

public class DisplayChartGUIView extends JFrame {
  private JButton prevWeekButton;
  private JButton prevMonthButton;
  private JButton prevYearButton;
  private JButton back;
  private JComboBox portfolioNameComboBox;
  int selectedPortfolioIndex;

  public DisplayChartGUIView() {
    super("Display Chart");

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

    JPanel buttons = new JPanel();
    buttons.setLayout(new FlowLayout());

    // prev week
    prevWeekButton = new JButton("Previous Week Performance");
    prevMonthButton = new JButton("Previous Month Performance");
    prevYearButton = new JButton("Previous Year Performance");

    prevWeekButton.setActionCommand("prevWeekPerformance");
    prevMonthButton.setActionCommand("prevMonthPerformance");
    prevYearButton.setActionCommand("prevYearPerformance");

    buttons.add(prevWeekButton);
    buttons.add(prevMonthButton);
    buttons.add(prevYearButton);

    JPanel buttonPanel = new JPanel();

    back = new JButton("Back");
    back.setActionCommand("backFromDisplayChart");
    buttonPanel.add(back);

    this.add(portfolioNamePanel, BorderLayout.PAGE_START);
    this.add(buttons, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
    this.pack();
  }

  public void addActionListener(ActionListener listener) {
    back.addActionListener(listener);
    prevWeekButton.addActionListener(listener);
    prevMonthButton.addActionListener(listener);
    prevYearButton.addActionListener(listener);
  }

  public int getSelectedPortfolioIndex() {
    return selectedPortfolioIndex;
  }

  public void updateExistingPortfoliosList(List<String> existingPortfolios) {
    DefaultComboBoxModel tempComboBox = new DefaultComboBoxModel();
    for (String portfolio : existingPortfolios) {
      tempComboBox.addElement(portfolio);
    }

    this.portfolioNameComboBox.setModel(tempComboBox);

  }
}
