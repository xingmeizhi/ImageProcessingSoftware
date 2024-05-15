package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import model.CollageModel;
import model.CollageModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class represents a test for CollageControllerImpl class.
 */
public class CollageControllerImplTest {


  CollageModel model = new CollageModelImpl();

  private CollageController controller = new CollageControllerImpl(model);

  // http://www.java2s.com/example/java-api/java/lang/system/setout-1-4.html
  // Example usage for java.lang System setOut
  private ByteArrayOutputStream outContent;


  @Before
  public void setup() {
    CollageModel model = new CollageModelImpl();

    controller = new CollageControllerImpl(model);
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void testCreateProject() throws IOException {
    String input = "new-project 1000 1000\nquit";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "project created!\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }

  @Test
  public void testAddLayer() throws IOException {
    String input = "new-project 1000 1000\nadd-layer layer1\n" +
            "quit";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "project created!\n" +
            "Please enter command: \n" +
            "Layer added!\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }

  @Test
  public void testAddLayerWithoutProject() throws IOException {
    String input = "add-layer layer3\nquit\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "Please create a new project first\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }

  @Test
  public void testSaveProject() throws IOException {
    String input = "new-project 1000 1000\nsave-project\nquit";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "project created!\n" +
            "Please enter command: \n" +
            "Project saved!\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }

  @Test
  public void testLoadProject() throws IOException {
    String input = "new-project 1000 1000\nsave-project\nload-project\nquit";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "project created!\n" +
            "Please enter command: \n" +
            "Project saved!\n" +
            "Please enter command: \n" +
            "Project loaded!\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }

  @Test
  public void testUnknownCommand() throws IOException {
    String input = "usdadad\nquit";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "Unknown command.\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }

  @Test
  public void testSetFilter() throws IOException {
    String input = "new-project 1000 1000\nadd-layer layer1\nset-filter layer1 blue-component\n" +
            "quit";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "project created!\n" +
            "Please enter command: \n" +
            "Layer added!\n" +
            "Please enter command: \n" +
            "Filter set!\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }

  @Test
  public void testAddImage() throws IOException {
    String input = "new-project 1000 1000\nadd-layer layer1\nset-filter layer1 blue-component\n" +
            "add-image-to-layer layer1 tako.ppm 100 50\nquit";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "project created!\n" +
            "Please enter command: \n" +
            "Layer added!\n" +
            "Please enter command: \n" +
            "Filter set!\n" +
            "Please enter command: \n" +
            "Image added successfully!\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }

  @Test
  public void testSaveImage() throws IOException {
    String input = "new-project 1000 1000\nadd-layer layer1\nset-filter layer1 blue-component\n" +
            "add-image-to-layer layer1 tako.ppm 100 50\nsave-image tako-eevee.ppm\nquit";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.run();
    assertEquals("Please enter command: \n" +
            "project created!\n" +
            "Please enter command: \n" +
            "Layer added!\n" +
            "Please enter command: \n" +
            "Filter set!\n" +
            "Please enter command: \n" +
            "Image added successfully!\n" +
            "Please enter command: \n" +
            "Image saved!\n" +
            "Please enter command: \n" +
            "quit\n", outContent.toString());
  }


}