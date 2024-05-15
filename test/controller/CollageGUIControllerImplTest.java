package controller;

import org.junit.Test;

import java.io.IOException;

import model.CollageModel;
import model.CollageModelImpl;



import static org.junit.Assert.assertEquals;

/**
 * Testing class for the GUI Controller. Uses a GUIMock for testing.
 */
public class CollageGUIControllerImplTest {

  CollageModel model = new CollageModelImpl();
  StringBuilder actual = new StringBuilder();
  GUIMock view = new GUIMock(actual);
  CollageController controller = new CollageControllerGUI(model, view);

  String layerInfo = "IntegerInterleavedRaster: " +
          "width = 800 " +
          "height = 600 " +
          "#Bands = 4 " +
          "xOff = 0 " +
          "yOff = 0 " +
          "dataOffset[0] 0";

  /**
   * Test to check if the project throws an error when an invalid height and width (none) are input.
   */
  @Test
  public void testCreateProjectWithInvalidHeightAndWidth() {
    try {
      controller.run();
      StringBuilder expected = new StringBuilder();
      view.newProjectButton.doClick();
      expected.append("Welcome ");
      expected.append("Enter Width");
      expected.append("Enter Height");
      expected.append("Invalid height or width");
      assertEquals(expected.toString(), actual.toString());

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Tests that the filter button does what it is supposed to an throws no errors.
   */
  @Test
  public void testFilter() {
    try {
      controller.run();
      StringBuilder expected = new StringBuilder();
      view.loadButton.doClick();
      view.filterButton.doClick();
      expected.append("Welcome ");
      expected.append("Buttons were reset.");
      expected.append("Received Layer: " + layerInfo + "\n");
      expected.append("Received Layer: " + layerInfo + "\n");
      expected.append("Layer buttons were updated.");
      expected.append("Received Layer: " + layerInfo + "\n");

      assertEquals(expected.toString(), actual.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Tests that the load button does what it is supposed to and throws no errors.
   */
  @Test
  public void testLoadProject() {
    try {
      controller.run();
      StringBuilder expected = new StringBuilder();
      view.loadButton.doClick();
      expected.append("Welcome ");
      expected.append("Buttons were reset.");
      expected.append("Received Layer: " + layerInfo + "\n");
      expected.append("Received Layer: " + layerInfo + "\n");
      expected.append("Layer buttons were updated.");
      assertEquals(expected.toString(), actual.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Tests that an error is thrown when trying to save a project with an invalid file path.
   */
  @Test
  public void testSaveProject() {
    try {
      controller.run();
      StringBuilder expected = new StringBuilder();
      view.loadButton.doClick();
      view.saveProjectButton.doClick();
      expected.append("Welcome ");
      expected.append("Buttons were reset.");
      expected.append("Received Layer: " + layerInfo + "\n");
      expected.append("Received Layer: " + layerInfo + "\n");
      expected.append("Layer buttons were updated.");
      expected.append("Error when writing file.");
      assertEquals(expected.toString(), actual.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Tests that an error is thrown when trying to save an image with an invalid file path.
   */
  @Test
  public void testSaveImage() {
    try {
      controller.run();
      StringBuilder expected = new StringBuilder();
      view.loadButton.doClick();
      view.saveImageButton.doClick();
      expected.append("Welcome ");
      expected.append("Buttons were reset.");
      expected.append("Received Layer: " + layerInfo + "\n");
      expected.append("Received Layer: " + layerInfo + "\n");
      expected.append("Layer buttons were updated.");
      expected.append("Error when writing file.");
      assertEquals(expected.toString(), actual.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
