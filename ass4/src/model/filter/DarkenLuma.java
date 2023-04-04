package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a darkenLuma filter.
 */
public class DarkenLuma extends AbstractFilter {

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "darken-luma";
  }

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

    double luma = 0.0216 * r + 0.7152 * g + 0.0722 * b;

    int newR = (int) Math.max(r - luma, 0);
    int newG = (int) Math.max(g - luma, 0);
    int newB = (int) Math.max(b - luma, 0);
    return new PixelImpl(newR, newG, newB, 255);
  }
}
