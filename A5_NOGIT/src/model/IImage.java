package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class represents an image interface.
 */
public interface IImage {
  /**
   * Return the width of Image.
   *
   * @return the width of Image
   */
  int getWidth();

  /**
   * Return the height of Image.
   *
   * @return the height of Image
   */
  int getHeight();

  /**
   * Return the max color value of Image.
   *
   * @return the max color value of Image.
   */
  int getMaxValue();

  /**
   * Get the pixel value at the (x, y) coordinates.
   * Return an array that contains the RGB value of the pixel.
   *
   * @param x the x-coordinate of the pixel
   * @param y the y-coordinate of the pixel
   * @return Array that contains the RGB value.
   * @throws IllegalArgumentException if the given coordinates are invalid.
   */
  Pixel getPixel(int x, int y) throws IllegalArgumentException;


  /**
   * Set the pixel value at the (x, y) coordinates.
   *
   * @param x     the x-coordinate of the pixel
   * @param y     the y-coordinate of the pixel
   * @param pixel Array that contains the RGB value
   * @throws IllegalArgumentException if the given coordinates are invalid.
   *                                  OR length of the array is null
   */
  void setPixel(int x, int y, Pixel pixel) throws IllegalArgumentException;

  /**
   * Read the PPM file and get the width, height, and maxvalue.
   * Also, the rgb value for each pixel.
   *
   * @param filename the name of the file to be read
   * @throws FileNotFoundException if the file is not found
   */
  void readPPM(String filename) throws FileNotFoundException;

  /**
   * Writes the Image object data to a PPM file.
   *
   * @param filename name of the file to be saved
   * @throws IOException if there's an error writing file
   */
  void writePPM(String filename) throws IOException;

//  /**
//   * Convert a ImageImpl class to a buffered image.
//   * @param imageImpl the imageImpl to be converted
//   * @return image that is converted to bufferedimage.
//   */
//  static BufferedImage convertToBufferedImage(IImage imageImpl) {
//    int width = imageImpl.getWidth();
//    int height = imageImpl.getHeight();
//
//    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//    for (int y = 0; y < height; y++) {
//      for (int x = 0; x < width; x++) {
//        Pixel pixel = imageImpl.getPixel(x, y);
//        Color color = new Color(pixel.getR(), pixel.getG(), pixel.getB(), pixel.getA());
//        bufferedImage.setRGB(x, y, color.getRGB());
//      }
//    }
//
//    return bufferedImage;
//  }

  /**
   * Convert a ImageImpl to a buffered image.
   * @return a buffered image that is converted by ImageImpl
   */
  BufferedImage toBufferedImage();

  /**
   * Read an image file, and convert it to an ImageImpl class,
   * and set its height, width, rgba value.
   *
   * @param filename the name of the file to be read
   * @throws IOException if there's an error reading image
   */
  void readImage(String filename) throws IOException;

  /**
   * Writes the Image object data to an image file.
   *
   * @param filename name of the file to be saved
   * @throws IOException if there's an error writing file
   */
  void writeImage(String filename) throws IOException;

}
