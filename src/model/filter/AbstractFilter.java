package model.filter;

import model.IImage;
import model.ImageImpl;
import model.Pixel;

/**
 * This class represents an AbstractFilter that
 * abstracts the methods for IFilter.
 */
public abstract class AbstractFilter implements IFilter {

  /**
   * Apply the filter to the given image.
   *
   * @param image image to be applied filter
   * @return image that applies the filter
   */
  @Override
  public IImage apply(IImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    int maxValue = image.getMaxValue();

    ImageImpl filteredImage = new ImageImpl(width, height, maxValue);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        filteredImage.setPixel(i, j, applyFilter(image.getPixel(i, j)));
      }
    }
    return filteredImage;
  }

  /**
   * Apply the filter to the given pixel.
   *
   * @param pixel the pixel to be filtered
   * @return the filtered pixel
   */
  protected abstract Pixel applyFilter(Pixel pixel);
}
