package model.filter.blendingfilters;

import model.IImage;
import model.ILayer;
import model.ImageImpl;
import model.Pixel;
import model.PixelImpl;
import model.filter.AbstractFilter;

/**
 * This class represents a Darkening Blending Filter: Multiply.
 */
public class Multiply extends AbstractFilter {
  private final IImage image2;
  private final int offsetX;
  private final int offsetY;
  private int currentX;
  private int currentY;

  /**
   * Constructs a new Multiply filter with the specified layer, merged image,
   * and offset values.
   *
   * @param layer       the layer containing the image to be blended
   * @param mergedImage the merged image to blend with the layer image
   * @param offsetX     the X-axis offset of the layer image relative to the merged image
   * @param offsetY     the Y-axis offset of the layer image relative to the merged image
   */
  public Multiply(ILayer layer, IImage mergedImage, int offsetX, int offsetY) {
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

      double[] hsl1 = pixel.convertRGBtoHSL(pixel.getR(), pixel.getG(), pixel.getB());
      double[] hsl2 = pixel2.convertRGBtoHSL(pixel2.getR(), pixel2.getG(), pixel2.getB());

      double h = hsl1[0];
      double s = hsl1[1];
      double l = hsl1[2] * hsl2[2];

      int[] rgb = pixel.convertHSLtoRGB(h, s, l);

      return new PixelImpl(rgb[0], rgb[1], rgb[2], pixel.getA());
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
    return "Multiply";
  }
}
