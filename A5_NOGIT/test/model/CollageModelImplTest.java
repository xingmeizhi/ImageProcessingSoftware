package model;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class represents a class for testing CollageModel.
 */
public class CollageModelImplTest {

  private CollageModelImpl collageModel;

  @Before
  public void setup() {
    collageModel = new CollageModelImpl();
  }

  @Test
  public void testCreateProject() {
    IProject project = collageModel.createProject(1000, 600);
    assertEquals(1000, project.getWidth());
    assertEquals(600, project.getHeight());
  }

  @Test
  public void testAddImageToLayer() throws IOException {
    IProject project = collageModel.createProject(1000, 1000);
    ILayer layer = new LayerImpl("Layer1", project.getWidth(), project.getHeight());
    project.addLayer(layer);
    collageModel.addImageToLayer(layer, "tako.ppm", 0, 0);
    assertNotNull(layer.getImage());
  }


  @Test
  public void testSetFilter() {
    IProject project = collageModel.createProject(800, 600);
    ILayer layer = new LayerImpl("Layer1", project.getWidth(), project.getHeight());
    project.addLayer(layer);
    collageModel.setFilter("Layer1", "blue-component");
    assertNotNull(layer.getFilter());
  }


  //test save and load ppm
  @Test
  public void testSaveAndLoad() throws IOException {

    IProject project = collageModel.createProject(1000, 1000);
    ILayer layer1 = new LayerImpl("Layer1", 1000, 1000);
    ILayer layer2 = new LayerImpl("Layer2", 1000, 1000);

    project.addLayer(layer1);
    project.addLayer(layer2);
    collageModel.addImageToLayer(layer1, "black.ppm", 400, 0);
    collageModel.setFilter("Layer2", "blue-component");
    collageModel.addImageToLayer(layer2, "tibbers.ppm", 400, 0);
    
    String filepath = "test_save_load.collage";
    collageModel.save(filepath);


    CollageModel loadedCollageModel = new CollageModelImpl();
    loadedCollageModel.load(filepath);
    assertEquals(project.getWidth(), 1000);
    assertEquals(project.getHeight(), 1000);

    assertTrue(layer1.hasImage());
    assertTrue(layer2.hasImage());
  }

  //test save and load image format other than ppm
  @Test
  public void testSaveAndLoad2() throws IOException {

    IProject project = collageModel.createProject(1000, 1000);
    ILayer layer1 = new LayerImpl("Layer1", 1000, 1000);
    ILayer layer2 = new LayerImpl("Layer2", 1000, 1000);

    project.addLayer(layer1);
    project.addLayer(layer2);
    collageModel.addImageToLayer(layer1, "1.png", 0, 0);
    collageModel.setFilter("Layer2", "blue-component");
    collageModel.addImageToLayer(layer2, "2.png", 0, 0);


    String filepath = "test_save_load.collage";

    collageModel.save(filepath);


    CollageModel loadedCollageModel = new CollageModelImpl();
    loadedCollageModel.load(filepath);
    assertEquals(project.getWidth(), 1000);
    assertEquals(project.getHeight(), 1000);

    assertTrue(layer1.hasImage());
    assertTrue(layer2.hasImage());
  }


}