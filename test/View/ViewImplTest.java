package View;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import Controller.ControllerImpl;
import Model.User;

import static org.junit.Assert.*;

public class ViewImplTest {

  OutputStream out;
  ViewInterface view;

  @Before
  public void setup() {
    out = new ByteArrayOutputStream();
    view = new ViewImpl(new PrintStream(out));
  }

  @Test
  public void testDisplayMenu() {
    view.displayMenu();
    String expectedMenu = "***********************************\n" + "\t1. Create a new Portfolio\n" + "\t2. Retrieve Portfolio\n" + "\t3. Check value of a Portfolio\n" + "\t4. Exit the application.\n" + "Pick one of the options\n";
    assertEquals(expectedMenu, out.toString());
  }


}