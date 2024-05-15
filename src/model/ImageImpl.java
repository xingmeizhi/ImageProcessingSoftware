package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * This class represents an ImageImpl class that contains
 * all the methods related to image.
 */
public class ImageImpl implements IImage {

  private int width;
  private int height;
  private int maxValue;
  private Pixel[][] pixel;

  /**
   * Constructs an ImageImpl with given width, height and
   * maximum value.
   *
   * @param width    the width of image
   * @param height   the height of image
   * @param maxValue maximum value of a color in this file (usually 255)
   */
  public ImageImpl(int width, int height, int maxValue) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height cannot be negative.");
    }
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixel = new Pixel[width][height];
  }

  /**
   * Return the width of Image.
   *
   * @return the width of Image
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Return the height of Image.
   *
   * @return the height of Image
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Return the max color value of Image.
   *
   * @return the max color value of Image.
   */
  @Override
  public int getMaxValue() {
    return this.maxValue;
  }


  /**
   * Get the pixel value at the (x, y) coordinates.
   * Return an array that contains the RGB value of the pixel.
   *
   * @param x the x-coordinate of the pixel
   * @param y the y-coordinate of the pixel
   * @return Array that contains the RGB value.
   * @throws IllegalArgumentException if the given coordinates are invalid.
   */
  @Override
  public Pixel getPixel(int x, int y) throws IllegalArgumentException {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("Invalid x or y.");
    }
    return this.pixel[x][y];
  }


  /**
   * Set the pixel value at the (x, y) coordinates.
   *
   * @param x     the x-coordinate of the pixel
   * @param y     the y-coordinate of the pixel
   * @param pixel Array that contains the RGB value
   * @throws IllegalArgumentException if the given coordinates are invalid.
   *                                  OR length of the array is null
   */
  @Override
  public void setPixel(int x, int y, Pixel pixel) throws IllegalArgumentException {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("Invalid x or y");
    }
    if (pixel == null) {
      throw new IllegalArgumentException("Invalid pixel");
    }
    this.pixel[x][y] = pixel;
  }

  /**
   * Read the PPM file and get the width, height, and maxvalue.
   * Also, the rgb value for each pixel.
   * Get from ImageUtil.java class from the starter code.
   *
   * @param filename the name of the file to be read
   * @throws FileNotFoundException if the file is not found
   */
  @Override
  public void readPPM(String filename) throws FileNotFoundException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    this.width = sc.nextInt();
    this.height = sc.nextInt();
    this.maxValue = sc.nextInt();
    this.pixel = new Pixel[width][height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        //set the rgb that read
        this.setPixel(j, i, new PixelImpl(r, g, b, 255));
      }
    }
  }


  /**
   * Writes the Image object data to a PPM file.
   *
   * @param filename name of the file to be saved
   * @throws IOException if there's an error writing file
   */
  @Override
  public void writePPM(String filename) throws IOException {
    FileWriter writer = null;
    try {
      writer = new FileWriter(filename);
      writer.write("P3\n");
      writer.write(this.getWidth() + " " + this.getHeight() + "\n");
      writer.write(this.getMaxValue() + "\n");

      // Write the rgb data
      for (int y = 0; y < this.getHeight(); y++) {
        for (int x = 0; x < this.getWidth(); x++) {
          Pixel rgb = this.getPixel(x, y);
          writer.write(Math.min(rgb.getR(), this.maxValue) + " "
                  + Math.min(rgb.getG(), this.maxValue) + " "
                  + Math.min(rgb.getB(), this.maxValue));


          // Add space between pixels, but not at the end of the row
          if (x < this.getWidth() - 1) {
            writer.write(" ");
          }
        }
        // Add a newline at the end
        writer.write("\n");
      }
      writer.close();
    } catch (IOException ex) {
      throw new IOException("Error when writing file.");

      //make sure the writer is closed
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          System.err.println(e.getMessage());
        }
      }
    }
  }

  /**
   * Convert a ImageImpl class to a buffered image.
   *
   */
  @Override
  public BufferedImage toBufferedImage() {
    int width = this.getWidth();
    int height = this.getHeight();

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel pixel = this.getPixel(x, y);
        Color color = new Color(
                pixel.getR(), pixel.getG(), pixel.getB(), pixel.getA());
        bufferedImage.setRGB(x, y, color.getRGB());
      }
    }
    return bufferedImage;
  }

  /**
   * Read an image file, and convert it to an ImageImpl class,
   * and set its height, width, rgba value.
   *
   * @param filename the name of the file to be read
   * @throws IOException if there's an error reading image
   */
  @Override
  public void readImage(String filename) throws IOException {
    BufferedImage bufferedImage;
    try {
      bufferedImage = ImageIO.read(new File(filename));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File" + filename + "not found");
    } catch (IOException ex) {
      throw new IOException("error when reading image");
    }
    this.width = bufferedImage.getWidth();
    this.height = bufferedImage.getHeight();
    this.maxValue = 255;
    this.pixel = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color color = new Color(bufferedImage.getRGB(x, y));
        this.setPixel(x, y, new PixelImpl(
                color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
      }
    }
  }

  /**
   * Writes the Image object data to an image file.
   *
   * @param filename name of the file to be saved
   * @throws IOException if there's an error writing file
   */
  @Override
  public void writeImage(String filename) throws IOException {
    // Convert the ImageImpl to a BufferedImage
    BufferedImage bufferedImage = this.toBufferedImage();

    // Extract the file extension from the filename
    String fileExtension = filename.substring(filename.lastIndexOf('.') + 1);

    // Validate the file extension
    if (!ImageIO.getImageWritersByFormatName(fileExtension).hasNext()) {
      throw new IllegalArgumentException("Invalid image format: " + fileExtension);
    }

    // Write the BufferedImage to the output file
    ImageIO.write(bufferedImage, fileExtension, new File(filename));
  }




  /**
   * Main method use to test this class.
   *
   * @param args args
   * @throws IOException if there are error
   */
  public static void main(String[] args) throws IOException {
    ImageImpl image = new ImageImpl(1, 1, 255);
    image.readPPM("tako.ppm");
    image.writePPM("tako100r.ppm");
  }
}
