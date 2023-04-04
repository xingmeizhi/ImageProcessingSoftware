package view;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;

import controller.Features;
import model.ILayer;

public interface CollageGUIView  {

  /**
   * Renders a message.
   * @param message the message to be rendered.
   */
  void renderMessage(String message);

  /**
   * Renders an error.
   * @param message the error to be rendered.
   */
  void renderError(String message);

  /**
   * Resets all the values of the buttons.
   */
  void reset();

  /**
   * Adds the Action Listeners for the buttons in the view.
   * @param features the features
   */
  void features(Features features);

  /**
   * Displays an image, if the provided layer name is not null and not in the list, add
   * it to the list of image names. Sets the layer being displayed to the selected layer.
   *
   * @param project the image of the current project
   * @param layers the list of layers in the project
   */
  void displayLayer(BufferedImage project, List<ILayer> layers);


  /**
   * Pop up has a text box and gets the int value from the value the user enters in the text box.
   * @param title Prompt for the user
   * @return the int entered by the user
   */
  int getIntPopUp(String title);

  /**
   * Pop up has a text box and gets the String value from the value the user enters in the text box.
   * @param title Prompt for the user
   * @return the String entered by the user
   */
  String getStringPopUp(String title);

  /**
   * Everytime a new layer is added, it is put into the JScrollPane of layers.
   * @param name the name of the layer
   */
  void addtoLayerScroll(Features features, String name);

  /**
   * Set the size of the canvas.
   * @param width the width of canvas
   * @param height the height of canvas
   */
  void setCanvasSize(int width, int height);

  /**
   * Update the size of canvas.
   */
  void updateCanvasSize();

}
