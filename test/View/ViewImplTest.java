package View;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import Controller.ControllerImpl;
import Model.User;

import static org.junit.Assert.*;

public class ViewImplTest {

  @Test
  public void testDisplayMenu() {
    InputStream input = null;
    try {
      input = new FileInputStream("viewInputTesting.txt");
    } catch (FileNotFoundException e) {
      fail("Input file not found");
    }
    User u = new User();
    ViewInterface view = new ViewImpl(input); //this input stream can be used by view as well as junit tests.
    ControllerImpl controller = new ControllerImpl(u,view);
    controller.go();
  }

}