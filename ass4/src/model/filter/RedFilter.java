package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a redFilter.
 */
public class RedFilter extends AbstractFilter {


  /**
   * Apply the filter to the given pixel.
   *
   * @param pixel the pixel to be filtered
   * @return the filtered pixel
   */
  @Override
  protected Pixel applyFilter(Pixel pixel) {
    int r = pixel.getR();
    return new PixelImpl(r, 0, 0, 255);
  }

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "red-component";
  }
}
