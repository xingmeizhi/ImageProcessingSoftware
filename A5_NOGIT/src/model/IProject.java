package model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This class represents a project interface.
 */
public interface IProject {
  /**
   * Add a layer to the current project.
   *
   * @param name name of the layer
   * @throws IllegalArgumentException if the layer given is null
   */
  void addLayer(String name) throws IllegalArgumentException;

  /**
   * Remove a layer to the current project.
   *
   * @param layer a layer to be removed
   * @throws IllegalArgumentException if the given layer is null
   */
  void removeLayer(ILayer layer) throws IllegalArgumentException;

  /**
   * Get the list of layers in the current project.
   *
   * @return a list of layers in the current project
   */
  List<ILayer> getLayers();

  /**
   * Get the width of the project.
   *
   * @return the width of the project
   */
  int getWidth();

  /**
   * Get the height of the project.
   *
   * @return the height of the project
   */
  int getHeight();

  /**
   * Get the layer with the given name.
   *
   * @param name the name of the layer
   * @return the ILayer object with given name
   */
  ILayer getLayerByName(String name);

  /**
   * Gets the layer on the bottom of the project for reverting the project.
   * @return the bottom layer
   */
  ILayer getBottomLayer();

  /**
   * Return a merged image of given range of layers.
   * @param startIndex the start index of the layers arraylist
   * @param endIndex the end index of the layers arraylist
   * @return a merged image of given range of layers
   */
  ImageImpl mergeLayersInRange(int startIndex, int endIndex);

  /**
   * Get the image of the project as a buffered image.
   * @return buffered image of current project
   */
  BufferedImage getProjectImage(List<ILayer> layers);
}
