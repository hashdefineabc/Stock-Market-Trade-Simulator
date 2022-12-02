package view;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
