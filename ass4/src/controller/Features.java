package controller;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.List;

import model.ILayer;

public interface Features {

  /**
   * Loads an image into the GUI.
   * @param projectPath the path to reach the project
   */
  void loadProject(String projectPath);

  /**
   * Reload the project to implement any changes.
   */
  void reload();

  /**
   * Add a layer with given name to the current project.
   * @param name the name of the layer to be added
   */
  void addLayer(String name);

  //TODO: implement this
  /**
   * Reverts a project back to its original transparent state.
   */
  void revertProject();

  void run(Command command);

  /**
   * Save the image to the given path.
   * @param pathname the name of the path
   */
  void saveImage(String pathname);

  /**
   * Save the project to the given filepath.
   * @param filepath filepath to be saved
   */
  void saveProject(String filepath);

  /**
   * Add image to the current layer with given offset.
   *
   * @param layer     the layer to be added
   * @param imagepath the file path of the image
   * @param x         the x-coordinate of the layer
   * @param y         the y-coordinate of the layer
   * @throws FileNotFoundException if the file is not found
   */
  void addImageToLayer(ILayer layer,String imagepath, int x, int y);

  /**
   * Return if there's an active project.
   * @return if there's an active project
   */
  boolean hasActiveProject();

  /**
   * Get the layer with the given name.
   * @param name the name of the layer to be found
   * @return the layer with given name
   */
  ILayer getLayerByName(String name);

  /**
   * Get the list of layers of the current project.
   * @return list of layers of the current project
   */
  List<ILayer> getProjectLayers();

  /**
   * Set the filter with given filtername and layername.
   * @param layerName the name of the layer to be set
   * @param filterName the filter to set
   */
  void setFilter(String layerName, String filterName);

  /**
   * Get the conbined image as a buffered image.
   * @param layers the layers that contain image to be get
   * @return combined bufferedimage
   */
  BufferedImage getCombinedImage(List<ILayer> layers);


}
