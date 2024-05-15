package model.filter.blendingfilters;

import model.IImage;
import model.ILayer;
import model.ImageImpl;
import model.Pixel;
import model.PixelImpl;
import model.filter.AbstractFilter;

/**
 * This class represents a Inversion Blending Filter: Difference.
 */
public class Difference extends AbstractFilter {
  private final IImage image2;
  private final int offsetX;
  private final int offsetY;
  private int currentX;
  private int currentY;

  /**
   * Constructs a new Difference filter with the specified layer, merged image,
   * and offset values.
   *
   * @param layer       the layer containing the image to be blended
   * @param mergedImage the merged image to blend with the layer image
   * @param offsetX     the X-axis offset of the layer image relative to the merged image
   * @param offsetY     the Y-axis offset of the layer image relative to the merged image
   */
  public Difference(ILayer layer, IImage mergedImage, int offsetX, int offsetY) {
    IImage image1 = layer.getImage();
    this.image2 = mergedImage;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }

  /**
   * Apply the filter to the given image.
   *
   * @param image1 image to be applied filter
   * @return image that applies the filter
   */
  @Override
  public IImage apply(IImage image1) {
    if (image1 == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }

    IImage result = new ImageImpl(image1.getWidth(), image1.getHeight(), image1.getMaxValue());
    for (int y = 0; y < image1.getHeight(); y++) {
      for (int x = 0; x < image1.getWidth(); x++) {
        currentX = x;
        currentY = y;
        result.setPixel(x, y, applyFilter(image1.getPixel(x, y)));
      }
    }

    return result;
  }

  /**
   * Apply the filter to the given pixel.
   *
   * @param pixel the pixel to be filtered
   * @return the filtered pixel
   */
  @Override
  protected Pixel applyFilter(Pixel pixel) {
    int newX = currentX + offsetX;
    int newY = currentY + offsetY;

    // Check if the new coordinates are within the boundaries of image2
    if (newX >= 0 && newX < image2.getWidth() && newY >= 0 && newY < image2.getHeight()) {
      Pixel pixel2 = image2.getPixel(newX, newY);

      int r = Math.abs(pixel.getR() - pixel2.getR());
      int g = Math.abs(pixel.getG() - pixel2.getG());
      int b = Math.abs(pixel.getB() - pixel2.getB());

      return new PixelImpl(r, g, b, pixel.getA());
    } else {
      return pixel;
    }
  }

  /**
   * Get the name of the filter.
   *
   * @return the name of the filter
   */
  @Override
  public String getName() {
    return "Difference";
  }
}
