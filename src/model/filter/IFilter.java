package model.filter;

import model.IImage;

/**
 * This class represents a Filter interface.
 */
public interface IFilter {

  /**
   * Apply the filter to the given image.
   *
   * @param image image to be applied filter
   * @return image that applies the filter
   */
  IImage apply(IImage image);


  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  String getName();
}
