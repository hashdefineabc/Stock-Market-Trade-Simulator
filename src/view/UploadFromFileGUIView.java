package view;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *The UploadFromFileGUIView represents the uploading a portfolio using the file feature application.
 * It extends the JFrame class in order to design the buttons for the create operation.
 *This lets the user create a new portfolio by entering the details of it.
 * All the buttons and textfields necessary to take input from the user are implemented here.
 */

public class UploadFromFileGUIView extends JFrame {

  public void setPopUp() {
    JOptionPane.showMessageDialog(UploadFromFileGUIView.this,
            "Saved Successfully", "Yayy", JOptionPane.INFORMATION_MESSAGE);
  }

  public File filePopUp() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "csv files", "csv");
    fchooser.setFileFilter(filter);
    File f = null;
    int retvalue = fchooser.showOpenDialog(UploadFromFileGUIView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      f = fchooser.getSelectedFile();
    }
    return f;
  }

  public void showError(String s) {
    JOptionPane.showMessageDialog(UploadFromFileGUIView.this, s,
            "Error", JOptionPane.ERROR_MESSAGE);
  }
}
