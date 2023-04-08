package model;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class represents a CollageModel interface
 * that contains all the methods relate to users'
 * command.
 */
public interface CollageModel {
  /**
   * Creates a project with given width and height.
   *
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @return project that is created
   */
  IProject createProject(int width, int height);


  /**
   * Load a project with given file path.
   * @param filepath a file path to be loaded
   * @throws IOException if there's an error loading data
   */
  IProject load(String filepath) throws IOException;


  /**
   * Save the current project to given file path.
   * @param filepath a file path to save project
   * @throws IOException if there's an error saving data
   */
  void save(String filepath) throws IOException;

  /**
   * Add image to the current layer with given offset.
   *
   * @param layer     the layer to be added
   * @param imageName the name of the image
   * @param x         the x-coordinate of the layer
   * @param y         the y-coordinate of the layer
   * @throws FileNotFoundException if the file is not found
   */
  void addImageToLayer(ILayer layer, String imageName, int x, int y) throws IOException;

  /**
   * Sets the filter to the given layer.
   *
   * @param layerName  the layer to be set.
   * @param filterName the name of filter to be set.
   */
  void setFilter(String layerName, String filterName);

  /**
   * Save the final rendered image to the given path.
   *
   * @throws IOException if there's an error writing file
   */
  void saveImage(String filename) throws IOException;



}
