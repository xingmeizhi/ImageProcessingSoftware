package model;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for the PixelImpl class.
 */
public class PixelImplTest {


  @Test
  public void convertRGBtoHSL() {
    PixelImpl pixel = new PixelImpl(255, 0, 0, 255);
    double[] hsl = pixel.convertRGBtoHSL(255, 0, 0);

    assertEquals(0, hsl[0], 0.01);
    assertEquals(1, hsl[1], 0.01);
    assertEquals(0.5, hsl[2], 0.01);
  }

  @Test
  public void convertHSLtoRGB() {
    PixelImpl pixel = new PixelImpl(0, 1, 0.5, 255);
    int[] rgb = pixel.convertHSLtoRGB(0, 1, 0.5);

    assertEquals(255, rgb[0]);
    assertEquals(0, rgb[1]);
    assertEquals(0, rgb[2]);
  }
}