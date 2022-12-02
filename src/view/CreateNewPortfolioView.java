package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 *The CreateNewPortfolioView represents the create page of the application.
 * It extends the JFrame class in order to design the buttons for the create operation.
 *This lets the user create a new portfolio by entering the details of it.
 * All the buttons and textfields necessary to take input from the user are implemented here.
 */
public class CreateNewPortfolioView extends JFrame {

  private JLabel label;
  private JTextField portfolioName;
  private JButton createPortfolio;
  private JButton home;
  private JPanel panel;
  private JLabel popUpMsg;
  JPanel buttonsPanel = new JPanel();

  /**
   * Creates a new  instance of 'createportfolio' class
   * @param title = the title we want this frame to display
   */
  public CreateNewPortfolioView(String title) {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    panel = new JPanel();
    popUpMsg = new JLabel("");

    label = new JLabel("Name of Portfolio: ");
    portfolioName = new JTextField(15);

    createPortfolio = new JButton("Create Portfolio");
    createPortfolio.setActionCommand("createPortfolio");

    home = new JButton("Home");
    home.setActionCommand("homeFromCreatePortfolio");

    panel.add(label);
    panel.add(portfolioName);
    panel.add(createPortfolio);

    buttonsPanel.add(createPortfolio);
//    buttonsPanel.add(buyStocks);
    buttonsPanel.add(home);

    this.add(panel, BorderLayout.CENTER);
    this.add(buttonsPanel, BorderLayout.PAGE_END);

    this.add(panel);
    this.setVisible(true);
    this.pack();
  }

  /**
   * The ActionListener is notified whenever you click on the button or menu item displayed on the
   * screen.
   * Here this listener is added for all the buttons on the view.
   * @param listener = listener object for this class.
   */

  public void addActionListener(ActionListener listener) {
    createPortfolio.addActionListener(listener);
    home.addActionListener(listener);
//    buyStocks.addActionListener(listener);
  }

  /**
   * Method to get the portfolio name from the user.
   * @return the user input.
   */
  public List<String> getInput() {
    List<String> inp = new ArrayList<>();
    inp.add(portfolioName.getText());
    return inp;
  }

  /**
   * If the user enters some wrong input, this popUp will be displayed to the user.
   * @param message = warning msg to the user to enter a valid input.
   */
  public void setPopUp(String message) {
    JOptionPane.showMessageDialog(CreateNewPortfolioView.this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    popUpMsg.setText(message);
  }

  /**
   * Clearing the textField  after the user's input is accepted.
   */

  public void clearField() {
    portfolioName.setText("");
  }
}
