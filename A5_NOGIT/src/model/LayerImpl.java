package model;

import model.filter.IFilter;

/**
 * This class represents a LayerImpl class that
 * has all the methods relate to layer.
 */
public class LayerImpl implements ILayer {
  private String name;
  private ImageImpl image;
  private IFilter filter;
  private int x;
  private int y;
  private boolean hasImage;


  /**
   * Setter for x.
   *
   * @param x the x-coordinate of the layer
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Setter for y.
   *
   * @param y the y-coordinate of the layer
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Getter for x.
   *
   * @return the x-coordinate of the layer
   */
  public int getX() {
    return x;
  }

  /**
   * Getter for y.
   *
   * @return the y-coordinate of the layer
   */
  public int getY() {
    return y;
  }


  /**
   * Setter for image.
   *
   * @param image the image to be set.
   */
  @Override
  public void setImage(ImageImpl image) {
    this.image = image;
    this.hasImage = true;
  }

  /**
   * Constructs an LayerImpl class with given name, width, height
   * and a default max value of 255.
   *
   * @param name   the name of the layer
   * @param width  the width of the image in the layer
   * @param height the height of the image in the layer
   */
  public LayerImpl(String name, int width, int height) {
    this.name = name;
    this.x = 0;
    this.y = 0;
    this.image = new ImageImpl(width, height, 255);
  }




  /**
   * Get the name of the layer.
   *
   * @return the name of the layer
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Get the filter applies to the layer.
   *
   * @return the filter applies to the layer
   */
  @Override
  public IFilter getFilter() {
    return this.filter;
  }

  /**
   * Apply a filter to the layer.
   *
   * @param filter the filter to be applied
   */
  @Override
  public void setFilter(IFilter filter) {
    this.filter = filter;
  }

  /**
   * Add an image to the layer with the given offset.
   *
   * @param image the image to be added
   * @param x     the x-coordinate of the layer
   * @param y     the y-coordinate of the layer
   * @throws IllegalArgumentException if the given image is null
   *                                  OR the x and y are invalid
   */
  @Override
  public void addImage(ImageImpl image, int x, int y) throws IllegalArgumentException {
    if (x < 0 || y < 0 || x + image.getWidth() > this.image.getWidth()
            || y + image.getHeight() > this.image.getHeight()) {
      throw new IllegalArgumentException("Invalid x or y");
    }
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");
    }

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Pixel pixel = image.getPixel(j, i);
        this.image.setPixel(x + j, y + i, pixel);
      }
    }
  }


  /**
   * Get the Image on the layer.
   *
   * @return the image on the layer
   */
  @Override
  public IImage getImage() {
    if (this.filter == null) {
      return this.image;
    } else {
      return this.filter.apply(this.image);
    }
  }

  /**
   * Checks if the layer has an image.
   *
   * @return true if the layer has an image, false otherwise
   */
  public boolean hasImage() {
    return hasImage;
  }
}
