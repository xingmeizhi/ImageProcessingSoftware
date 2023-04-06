package model;

import org.junit.Before;
import org.junit.Test;

import model.filter.Allfilter;
import model.filter.IFilter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class represents a LayerImplTest.
 */
public class LayerImplTest {

  private LayerImpl layer;
  private ImageImpl image;

  @Before
  public void setUp() {
    layer = new LayerImpl("Layer", 500, 500);
    image = new ImageImpl(1, 1, 255);
  }

  @Test
  public void testSetX() {
    layer.setX(10);
    assertEquals(10, layer.getX());
  }

  @Test
  public void testSetY() {
    layer.setY(10);
    assertEquals(10, layer.getY());
  }

  @Test
  public void testGetX() {
    assertEquals(0, layer.getX());
  }

  @Test
  public void testGetY() {
    assertEquals(0, layer.getX());
  }

  @Test
  public void testSetImage() {
    layer.setImage(image);
    assertEquals(image, layer.getImage());
  }

  @Test
  public void testGetName() {
    assertEquals("Layer", layer.getName());
  }

  @Test
  public void testGetAndSetFilter() {
    IFilter filter = Allfilter.createFilter("normal");
    layer.setFilter(filter);
    layer.getFilter();
    assertEquals(filter, layer.getFilter());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddImage() {
    layer.addImage(image, -1, 0);
  }

  @Test
  public void getImage() {
    IImage baseImage = layer.getImage();
    assertNotNull(baseImage);
    assertEquals(500, baseImage.getWidth());
    assertEquals(500, baseImage.getHeight());
  }
}