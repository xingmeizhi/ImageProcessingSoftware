package model.filter;

import model.IImage;

/**
 * This class represents a normal filter with no change to image.
 */
public class NormalFilter implements IFilter {
  /**
   * Apply the filter to the given image.
   *
   * @param image image to be applied filter
   * @return image that applies the filter
   */
  @Override
  public IImage apply(IImage image) {
    return image;
  }

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "normal";
  }
}
