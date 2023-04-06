package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.filter.Allfilter;
import model.filter.IFilter;

/**
 * This class represents a CollageModelImpl class that contains
 * possible commands that users can put.
 */
public class CollageModelImpl implements CollageModel {
  private static IProject project;

  /**
   * Creates a project with given width and height.
   *
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @return project that is created
   */
  @Override
  public IProject createProject(int width, int height) {
    return project = new ProjectImpl(width, height, new ArrayList<>());
  }

  /**
   * Load a project with given file path.
   *
   * @param filepath a file path to be loaded
   * @throws IOException if there's an error loading data
   */
  @Override
  public IProject load(String filepath) throws IOException {
    File fileToLoad = new File(filepath);

    //read the file
    try (Scanner scanner = new Scanner(new FileReader(fileToLoad))) {
      String line = scanner.nextLine();
      //check if it's ppm
      if (!"C1".equals(line)) {
        throw new IOException("Invalid file format");
      }

      //get width, height and maxvalue
      String[] dimensions = scanner.nextLine().split(" ");
      int width = Integer.parseInt(dimensions[0]);
      int height = Integer.parseInt(dimensions[1]);
      int maxValue = Integer.parseInt(scanner.nextLine());

      //a new arraylist to store layers
      List<ILayer> layers = new ArrayList<>();

      //read layer information
      while (scanner.hasNextLine()) {
        String[] layerInfo = scanner.nextLine().split(" ");
        String layerName = layerInfo[0];
        String filterName = layerInfo[1];

        //create filter
        IFilter filter = Allfilter.createFilter(filterName);

        //get layers width, height, offset
        dimensions = scanner.nextLine().split(" ");
        int layerWidth = Integer.parseInt(dimensions[0]);
        int layerHeight = Integer.parseInt(dimensions[1]);
        int offsetX = Integer.parseInt(dimensions[2]);
        int offsetY = Integer.parseInt(dimensions[3]);

        ImageImpl layerImage = new ImageImpl(layerWidth, layerHeight, maxValue);

        for (int y = 0; y < layerHeight; y++) {
          String[] pixelData = scanner.nextLine().split(" ");
          for (int x = 0; x < layerWidth; x++) {
            int r = Integer.parseInt(pixelData[x * 3]);
            int g = Integer.parseInt(pixelData[x * 3 + 1]);
            int b = Integer.parseInt(pixelData[x * 3 + 2]);
            layerImage.setPixel(x, y, new PixelImpl(r, g, b, 255));
          }
        }

        LayerImpl layer = new LayerImpl(layerName, layerWidth, layerHeight);
        layer.setImage(layerImage);
        layer.setFilter(filter);
        layer.setX(offsetX);
        layer.setY(offsetY);
        layers.add(layer);
      }

      return project = new ProjectImpl(width, height, layers);
    }
  }

  /**
   * Save the current project to given file path.
   *
   * @param filepath a file path to save project
   * @throws IOException if there's an error saving data
   */
  public void save(String filepath) throws IOException {
    File fileToSave = new File(filepath);

    //write file
    try (FileWriter writer = new FileWriter(fileToSave)) {
      writer.write("C1\n");
      writer.write(project.getWidth() + " " + project.getHeight() + "\n");

      // Write the maxValue only once at the beginning of the file
      ImageImpl firstLayerImage = (ImageImpl) project.getLayers().get(0).getImage();
      writer.write(firstLayerImage.getMaxValue() + "\n");

      for (ILayer layer : project.getLayers()) {


        ImageImpl layerImage = (ImageImpl) layer.getImage();


        // Assign a normal filter if the layer's filter is null
        if (layer.getFilter() == null) {
          layer.setFilter(Allfilter.createFilter("normal"));
        }


        //write layer and filter name
        writer.write(layer.getName() + " " + layer.getFilter().getName() + "\n");
        //write width, height and offset
        writer.write(layerImage.getWidth() +
                " "
                + layerImage.getHeight()
                + " "
                + layer.getX()
                + " " + layer.getY() + "\n");


        // Write the PPM image data directly into the file
        for (int y = 0; y < layerImage.getHeight(); y++) {
          for (int x = 0; x < layerImage.getWidth(); x++) {
            Pixel pixel = layerImage.getPixel(x, y);
            writer.write(pixel.getR() + " " + pixel.getG() + " " + pixel.getB() + " ");
          }
          writer.write("\n");
        }


      }
    }
  }


  /**
   * Add image to the current layer with given offset.
   *
   * @param layer     the layer to be added
   * @param imageName the name of the image
   * @param x         the x-coordinate of the layer
   * @param y         the y-coordinate of the layer
   * @throws FileNotFoundException if the file is not found
   */
  @Override
  public void addImageToLayer(ILayer layer, String imageName, int x, int y)
          throws FileNotFoundException {
    if (layer == null || imageName == null) {
      throw new IllegalArgumentException("Layer or image cannot be null");
    }
    if (x < 0 || x > project.getWidth() || y < 0 || y > project.getHeight()) {
      throw new IllegalArgumentException("Invalid offset");
    }

    ImageImpl image = new ImageImpl(1, 1, 255);

    image.readPPM(imageName);

    layer.addImage(image, x, y);
    layer.setImage(image);
    layer.setX(x);
    layer.setY(y);
  }


  /**
   * Sets the filter to the given layer.
   *
   * @param layerName  the layer to be set.
   * @param filterName the name of filter to be set.
   */
  @Override
  public void setFilter(String layerName, String filterName) {
    if (layerName == null || filterName == null) {
      throw new IllegalArgumentException("layer or filter name cannot be null");
    }

    ILayer given = null;
    for (ILayer layer : project.getLayers()) {
      if (layer.getName().equals(layerName)) {
        given = layer;
        break;
      }
    }

    if (given == null) {
      throw new IllegalArgumentException("Layer not found");
    }

    IFilter filter = Allfilter.createFilter(filterName, given, project);
    given.setFilter(filter);
  }




  /**
   * Save the final rendered image.
   *
   * @param filename the name that the file to be named
   * @throws IOException if there's an error writing file
   */
  @Override
  public void saveImage(String filename) throws IOException {

    ImageImpl renderedImage = new ImageImpl(project.getWidth(), project.getHeight(), 255);
    initializeImage(renderedImage);


    for (ILayer layer : project.getLayers()) {
      ImageImpl layerImage = (ImageImpl) layer.getImage();
      int offsetX = layer.getX();
      int offsetY = layer.getY();

      // Iterate through all pixels of the layer image
      for (int y = 0; y < layerImage.getHeight(); y++) {
        for (int x = 0; x < layerImage.getWidth(); x++) {
          // Get the pixel from the layer image
          Pixel pixel = layerImage.getPixel(x, y);

          //check if pixel is null
          if (pixel != null) {
            int newX = x + offsetX;
            int newY = y + offsetY;

            // Check if the new coordinates are within the result image boundaries
            if (newX >= 0 && newX < renderedImage.getWidth()
                    && newY >= 0
                    && newY < renderedImage.getHeight()) {
              // Set the pixel in the result image
              renderedImage.setPixel(newX, newY, pixel);
            }
          }


        }
      }
    }
    // Save the rendered image to the given file
    renderedImage.writePPM(filename);
  }


  /**
   * Helper method to initialize every pixel.
   *
   * @param image image to be initialized
   */
  private void initializeImage(ImageImpl image) {
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        image.setPixel(x, y, new PixelImpl(255, 255, 255, 255));
      }
    }
  }


  /**
   * Main method that test the model.
   *
   * @param args args
   * @throws IOException if there's error
   */
  public static void main(String[] args) throws IOException {
    CollageModel collageModel = new CollageModelImpl();
    collageModel.createProject(1000, 1000);
    ILayer layer1 = new LayerImpl("Layer1", project.getWidth(), project.getHeight());
    project.addLayer(layer1);
    collageModel.addImageToLayer(layer1, "black.ppm", 400, 0);
    ILayer layer2 = new LayerImpl("Layer2", project.getWidth(), project.getHeight());
    project.addLayer(layer2);
    collageModel.setFilter("Layer2", "blue-component");
    collageModel.addImageToLayer(layer2, "tibbers.ppm", 400, 0);
    ILayer layer3 = new LayerImpl("Layer3", project.getWidth(), project.getHeight());
    project.addLayer(layer3);
    collageModel.addImageToLayer(layer3, "tako.ppm", 100, 0);
    collageModel.setFilter("Layer3", "Difference");
    collageModel.saveImage("res/Difference.ppm");


    //    collageModel.saveImage("res/testing2.ppm");
  }


}
