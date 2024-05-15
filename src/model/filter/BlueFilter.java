package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a blue-component filter.
 */
public class BlueFilter extends AbstractFilter {


  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "blue-component";
  }

  /**
   * Apply the filter to the given pixel.
   *
   * @param pixel the pixel to be filtered
   * @return the filtered pixel
   */
  @Override
  protected Pixel applyFilter(Pixel pixel) {
    int b = pixel.getB();
    return new PixelImpl(0, 0, b, 255);
  }
}
