package model;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * This class represents an ImageImplTest.
 */
public class ImageImplTest {

  private IImage image;

  @Before
  public void setup() throws FileNotFoundException {
    image = new ImageImpl(1, 1, 255);
  }

  @Test
  public void getWidth() {
    assertEquals(1, image.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(1, image.getHeight());
  }

  @Test
  public void getMaxValue() {
    assertEquals(255, image.getMaxValue());
  }

}