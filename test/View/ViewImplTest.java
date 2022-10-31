package View;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import Controller.ControllerImpl;
import Model.User;

import static org.junit.Assert.*;

public class ViewImplTest {

  @Test
  public void testDisplayMenu() {
    InputStream input = null;
    String inputForOption1 = "1";
    input = new ByteArrayInputStream(inputForOption1.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    User u = new User();
    ViewInterface view = new ViewImpl(input, new PrintStream(out)); //this input stream can be used by view as well as junit tests.
    ControllerImpl controller = new ControllerImpl(u,view);
    controller.showMenuOnView();
    String expectedMenu = "\t1. Create a new Portfolio\n" + "\t2. Retrieve Portfolio\n"
            + "\t3. Check value of a Portfolio\n" +"\t4. Exit the application.\n" + "\tPick one of the options\n";

    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testTakeStockInput() {
    String inputForTest = "GOOG\n5\n";
    InputStream input = new ByteArrayInputStream(inputForTest.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    User u = new User();
    ViewInterface view = new ViewImpl(input, new PrintStream(out));
    ControllerImpl controller = new ControllerImpl(u, view);
    controller.takeStockInputFromView();
    String expectedOutput = "Enter the ticker name:\n" + "Enter the number of units purchased:\n";
    assertEquals(expectedOutput, out.toString());
  }

}