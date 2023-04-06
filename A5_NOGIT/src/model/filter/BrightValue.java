package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a brightValue filter.
 */
public class BrightValue extends AbstractFilter {
  /**
   * Apply the filter to the given pixel.
   *
   * @param pixel the pixel to be filtered
   * @return the filtered pixel
   */
  @Override
  protected Pixel applyFilter(Pixel pixel) {
    int r = pixel.getR();
    int g = pixel.getG();
    int b = pixel.getB();

    int maxValue = Math.max(r, Math.max(g, b));

    int newR = Math.min(r + maxValue, 255);
    int newG = Math.min(g + maxValue, 255);
    int newB = Math.min(b + maxValue, 255);

    return new PixelImpl(newR, newG, newB, 255);
  }

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "brighten-value";
  }
}
