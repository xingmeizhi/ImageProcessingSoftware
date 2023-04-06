package model;

/**
 * This class represents a Pixel interface that
 * contains all the methods relate to pixel.
 */
public interface Pixel {


  /**
   * Getter for value R.
   *
   * @return the value R of a pixel
   */
  int getR();

  /**
   * Getter for value G.
   *
   * @return the value G of a pixel
   */
  int getG();

  /**
   * Getter for value B.
   *
   * @return the value B of a pixel
   */
  int getB();

  /**
   * Getter for value A.
   *
   * @return the alpha value of a pixel
   */
  int getA();

  /**
   * Converts an RGB representation in the range 0-1 into an HSL
   * representation where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>.
   *
   * @param r red value of the RGB between 0 and 1
   * @param g green value of the RGB between 0 and 1
   * @param b blue value of the RGB between 0 and 1
   */
  double[] convertRGBtoHSL(int r, int g, int b);

  /**
   * Converts an HSL representation where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   * into an RGB representation where each component is in the range 0-1.
   *
   * @param hue        hue of the HSL representation
   * @param saturation saturation of the HSL representation
   * @param lightness  lightness of the HSL representation
   */
  int[] convertHSLtoRGB(double hue, double saturation, double lightness);
}
