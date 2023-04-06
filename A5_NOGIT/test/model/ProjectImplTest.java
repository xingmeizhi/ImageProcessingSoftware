package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This clas represents a ProjectImplTest.
 */
public class ProjectImplTest {

  private IProject project;
  private ILayer layer1;
  private ILayer layer2;

  @Before
  public void setup() {
    layer1 = new LayerImpl("Layer1", 10, 10);
    layer2 = new LayerImpl("Layer2", 10, 10);

    List<ILayer> layers = new ArrayList<>();
    layers.add(layer1);
    layers.add(layer2);

    project = new ProjectImpl(10, 10, layers);
  }

  @Test
  public void addLayer() {
    ILayer newLayer = new LayerImpl("newLayer", 4, 4);
    project.addLayer(newLayer);

    assertEquals(3, project.getLayers().size());
    assertEquals(newLayer, project.getLayerByName("newLayer"));
  }

  @Test
  public void removeLayer() {
    project.removeLayer(layer1);

    assertEquals(1, project.getLayers().size());
  }

  @Test
  public void getLayers() {
    List<ILayer> layers = project.getLayers();

    assertEquals(2, layers.size());
    assertTrue(layers.contains(layer1));
    assertTrue(layers.contains(layer2));
  }

  @Test
  public void getWidth() {
    assertEquals(10, project.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(10, project.getHeight());
  }

  @Test
  public void getLayerByName() {
    assertEquals(layer1, project.getLayerByName("Layer1"));
    assertEquals(layer2, project.getLayerByName("Layer2"));
  }
}