package view;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UploadFromFileGUIView extends JFrame {
  public UploadFromFileGUIView() {

  }

  public void setPopUp() {
    JOptionPane.showMessageDialog(UploadFromFileGUIView.this, "Saved Successfully", "Yayy", JOptionPane.INFORMATION_MESSAGE);
  }

  public void addActionListener(ActionListener listener) {

  }

  public File FilePopUp() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "csv files", "csv");
    fchooser.setFileFilter(filter);
    File f = null;
    int retvalue = fchooser.showOpenDialog(UploadFromFileGUIView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      f = fchooser.getSelectedFile();
//      fileOpenDisplay.setText(f.getAbsolutePath());
    }
    return f;
  }

  public void showError(String s) {
    JOptionPane.showMessageDialog(UploadFromFileGUIView.this, s, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
