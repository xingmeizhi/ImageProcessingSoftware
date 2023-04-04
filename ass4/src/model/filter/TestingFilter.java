package model.filter;

import model.Pixel;
import model.PixelImpl;

public class TestingFilter extends AbstractFilter{
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
    return new PixelImpl(r, g, b, 100);
  }

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "testing";
  }
}
