package model;

/**
 * This class represents a Pixel class that uses to save the rgb and
 * HSL value.
 */
public class PixelImpl implements Pixel {
  private int r;
  private int g;
  private int b;
  private int a;
  private double h;
  private double s;
  private double l;


  /**
   * Constructs a pixel using rgba value.
   *
   * @param r r value of the pixel
   * @param g g value of the pixel
   * @param b b value of the pixel
   * @param a a value of the pixel, which is alpha value
   */
  public PixelImpl(int r, int g, int b, int a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }

  /**
   * Constructs a pixel using HSL values.
   *
   * @param h hue value of the pixel
   * @param s saturation value of the pixel
   * @param l lightness value of the pixel
   * @param a alpha value of the pixel
   */
  public PixelImpl(double h, double s, double l, int a) {
    this.h = h;
    this.s = s;
    this.l = l;
    this.a = a;

    // Convert HSL to RGB and store the values
    int[] rgb = convertHSLtoRGB(h, s, l);
    this.r = rgb[0];
    this.g = rgb[1];
    this.b = rgb[2];
  }

  /**
   * Getter for value R.
   *
   * @return the value R of a pixel
   */
  public int getR() {
    return r;
  }

  /**
   * Getter for value G.
   *
   * @return the value G of a pixel
   */
  public int getG() {
    return g;
  }

  /**
   * Getter for value B.
   *
   * @return the value B of a pixel
   */
  public int getB() {
    return b;
  }

  /**
   * Getter for value A.
   *
   * @return the alpha value of a pixel
   */
  public int getA() {
    return a;
  }

  /**
   * Converts an RGB representation in the range 0-1 into an HSL
   * representation where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   *  @param r red value of the RGB between 0 and 1
   *
   * @param g green value of the RGB between 0 and 1
   * @param b blue value of the RGB between 0 and 1
   */
  @Override
  public double[] convertRGBtoHSL(int r, int g, int b) {
    double dr = r / 255.0;
    double dg = g / 255.0;
    double db = b / 255.0;

    double componentMax = Math.max(dr, Math.max(dg, db));
    double componentMin = Math.min(dr, Math.min(dg, db));
    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;
    double hue, saturation;

    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;

      if (componentMax == dr) {
        hue = (dg - db) / delta;
        while (hue < 0) {
          hue += 6;
        }
        hue = hue % 6;
      } else if (componentMax == dg) {
        hue = (db - dr) / delta;
        hue += 2;
      } else if (componentMax == db) {
        hue = (dr - dg) / delta;
        hue += 4;
      }

      hue = hue * 60;
    }

    return new double[]{hue, saturation, lightness};
  }

  /**
   * Converts an HSL representation where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   * into an RGB representation where each component is in the range 0-1
   *  @param hue        hue of the HSL representation
   *
   * @param saturation saturation of the HSL representation
   * @param lightness  lightness of the HSL representation
   */
  @Override
  public int[] convertHSLtoRGB(double hue, double saturation, double lightness) {
    double r = convertFn(hue, saturation, lightness, 0) * 255;
    double g = convertFn(hue, saturation, lightness, 8) * 255;
    double b = convertFn(hue, saturation, lightness, 4) * 255;

    return new int[]{(int) r, (int) g, (int) b};
  }


  /**
   * Helper method that performs the translation from the HSL polygonal
   * model to the more familiar RGB model
   */
  private static double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue / 30)) % 12;
    double a = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }


}
