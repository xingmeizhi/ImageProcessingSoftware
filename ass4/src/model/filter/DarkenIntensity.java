package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a DarkenIntensity filter.
 */
public class DarkenIntensity extends AbstractFilter {
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

    int intensity = (r + g + b) / 3;

    int newR = Math.max(r - intensity, 0);
    int newG = Math.max(g - intensity, 0);
    int newB = Math.max(b - intensity, 0);

    return new PixelImpl(newR, newG, newB, 255);
  }

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "darken-intensity";
  }
}
