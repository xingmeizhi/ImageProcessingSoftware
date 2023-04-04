package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a DarkenValue filter.
 */
public class DarkenValue extends AbstractFilter {
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

    int newR = Math.max(r - maxValue, 0);
    int newG = Math.max(g - maxValue, 0);
    int newB = Math.max(b - maxValue, 0);

    return new PixelImpl(newR, newG, newB, 255);
  }

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "darken-value";
  }
}
