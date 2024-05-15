package model.filter;

import model.Pixel;
import model.PixelImpl;

/**
 * This class represents a brightenLuma filter.
 */
public class BrightenLuma extends AbstractFilter {


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

    int newR = (int) Math.min(r + luma, 255);
    int newG = (int) Math.min(g + luma, 255);
    int newB = (int) Math.min(b + luma, 255);
    return new PixelImpl(newR, newG, newB, 255);
  }


  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "brighten-luma";
  }
}
