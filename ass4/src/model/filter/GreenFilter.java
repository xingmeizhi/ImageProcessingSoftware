package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a GreenFilter.
 */
public class GreenFilter extends AbstractFilter {


  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "green-component";
  }

  /**
   * Apply the filter to the given pixel.
   *
   * @param pixel the pixel to be filtered
   * @return the filtered pixel
   */
  @Override
  protected Pixel applyFilter(Pixel pixel) {
    int g = pixel.getG();
    return new PixelImpl(0, g, 0, 255);
  }
}
