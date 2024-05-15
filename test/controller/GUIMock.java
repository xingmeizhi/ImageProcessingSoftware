package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import model.ILayer;
import view.CollageGUIView;

/**
 * Mock GUI that tests if the view is doing the right things when buttons are pressed.
 */
public class GUIMock extends JFrame implements CollageGUIView {

  public AbstractButton loadButton;
  public AbstractButton newProjectButton;
  public AbstractButton saveProjectButton;
  public AbstractButton saveImageButton;
  public AbstractButton addLayerButton;
  public AbstractButton addImageToLayerButton;
  public AbstractButton filterButton;
  public ILayer layer1;
  private Appendable appendable;


  /**
   * GUIMock Constructor.
   * @param appendable appendable
   */
  public GUIMock(Appendable appendable) {
    loadButton = new JButton("Load Project");
    saveImageButton = new JButton("Save Image");
    saveProjectButton = new JButton("Save Project");
    addLayerButton = new JButton("Add Layer");
    addImageToLayerButton = new JButton("Add Image To Layer");
    filterButton = new JButton("Green-Component");
    newProjectButton = new JButton("New Project");
    this.appendable = appendable;


  }

  @Override
  public void renderMessage(String message) {
    try {
      appendable.append(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void renderError(String message) {
    try {
      appendable.append(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void reset() {
    try {
      appendable.append("Buttons were reset.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void features(Features features) {
    loadButton.addActionListener(event -> features.loadProject(
            "projects/testingcontroller.collage"));
    saveProjectButton.addActionListener(event -> features.saveProject(
            "."));
    saveImageButton.addActionListener(event -> features.saveImage(
            "."));
    addLayerButton.addActionListener(event -> features.addLayer("layer1"));
    addImageToLayerButton.addActionListener(event -> features.addImageToLayer(layer1,
            "res/testing/blue+brigthen-luma.ppm",0,0));
    filterButton.addActionListener(event -> features.setFilter("layer1",
            "green-component"));
    newProjectButton.addActionListener(event -> features.runCommand(Command.newProject));
  }

  /**
   * Displays an image, if the provided layer name is not null and not in the list, add
   * it to the list of image names. Sets the layer being displayed to the selected layer.
   *
   * @param project the image of the current project
   * @param layers  the list of layers in the project
   */
  @Override
  public void displayLayer(BufferedImage project, List<ILayer> layers) {
    try {
      appendable.append("Received Layer: " + project.getData() + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }




  @Override
  public int getIntPopUp(String title) {
    try {
      appendable.append(title);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public String getStringPopUp(String title) {
    return null;
  }

  @Override
  public void updateLayerButtons() {
    try {
      appendable.append("Layer buttons were updated.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
