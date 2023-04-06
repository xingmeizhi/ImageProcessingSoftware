package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a brightenIntensity filter.
 */
public class BrightenIntensity extends AbstractFilter {
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

    int newR = Math.min(r + intensity, 255);
    int newG = Math.min(g + intensity, 255);
    int newB = Math.min(b + intensity, 255);

    return new PixelImpl(newR, newG, newB, 255);
  }

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "brighten-intensity";
  }
}
