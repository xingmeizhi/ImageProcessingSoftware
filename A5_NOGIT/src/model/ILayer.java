package model;

import model.filter.IFilter;

/**
 * This class represents a layer interface.
 */
public interface ILayer {
  /**
   * Get the name of the layer.
   *
   * @return the name of the layer
   */
  String getName();

  /**
   * Get the filter applies to the layer.
   *
   * @return the filter applies to the layer
   */
  IFilter getFilter();

  /**
   * Apply a filter to the layer.
   *
   * @param filter the filter to be applied
   */
  void setFilter(IFilter filter);


  /**
   * Add an image to the layer with the given offset.
   *
   * @param image the image to be added
   * @param x     the x-coordinate of the layer
   * @param y     the y-coordinate of the layer
   * @throws IllegalArgumentException if the given image is null
   *                                  OR the x and y are invalid
   */
  void addImage(ImageImpl image, int x, int y) throws IllegalArgumentException;

  /**
   * Get the Image on the layer.
   *
   * @return the image on the layer
   */
  IImage getImage();

  /**
   * Getter for x.
   *
   * @return the x-coordinate of the layer
   */
  int getX();

  /**
   * Getter for y.
   *
   * @return the y-coordinate of the layer
   */
  int getY();

  /**
   * Setter for image.
   *
   * @param image the image to be set.
   */
  void setImage(ImageImpl image);

  /**
   * Setter for x.
   *
   * @param x the x-coordinate of the layer
   */
  void setX(int x);

  /**
   * Setter for y.
   *
   * @param y the y-coordinate of the layer
   */
  void setY(int y);

  /**
   * Checks if the layer has an image.
   *
   * @return true if the layer has an image, false otherwise
   */
  boolean hasImage();



}
