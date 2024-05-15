package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * this class represents a ProjectImpl class that
 * contain all the methods relate to project.
 */
public class ProjectImpl implements IProject {
  private final int width;
  private final int height;
  private final List<ILayer> layers;



  /**
   * Constructs a ProjectImpl object with the specified width, height, and layers.
   *
   * @param width  the width of the project
   * @param height the height of the project
   * @param layers the list of layers in the project
   * @throws IllegalArgumentException if the width or height is less than or equal to 0,
   *                                  OR if the layers is null
   */
  public ProjectImpl(int width, int height, List<ILayer> layers) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid height or width");
    }
    if (layers == null) {
      throw new IllegalArgumentException("Layers cannot be null");
    }
    this.width = width;
    this.height = height;
    this.layers = layers;
  }

  /**
   * Add a layer to the current project.
   *
   * @param name name of the layer
   * @throws IllegalArgumentException if the layer given is null
   */
  @Override
  public void addLayer(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }
    ILayer l = new LayerImpl(name, width, height);
    layers.add(l);
  }

  /**
   * Remove a layer to the current project.
   *
   * @param layer a layer to be removed
   * @throws IllegalArgumentException if the given layer is null
   */
  @Override
  public void removeLayer(ILayer layer) throws IllegalArgumentException {
    if (layer == null) {
      throw new IllegalArgumentException("Layer cannot be null");
    }
    layers.remove(layer);
  }

  /**
   * Get the list of layers in the current project.
   *
   * @return a list of layers in the current project
   */
  @Override
  public List<ILayer> getLayers() {
    return layers;
  }


  /**
   * Get the width of the project.
   *
   * @return the width of the project
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Get the height of the project.
   *
   * @return the height of the project
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Get the layer with the given name.
   *
   * @param name the name of the layer
   * @return the ILayer object with given name
   */
  @Override
  public ILayer getLayerByName(String name) {
    for (ILayer layer : layers) {
      if (layer.getName().equals(name)) {
        return layer;
      }
    }
    return null;
  }

  @Override
  public ILayer getBottomLayer() {
    return layers.get(0);
  }

  /**
   * Return a merged image of given range of layers.
   *
   * @param startIndex the start index of the layers arraylist
   * @param endIndex   the end index of the layers arraylist
   * @return a merged image of given range of layers
   */
  @Override
  public ImageImpl mergeLayersInRange(int startIndex, int endIndex) {
    ImageImpl mergedImage = new ImageImpl(this.getWidth(), this.getHeight(), 255);
    initializeImage(mergedImage);

    for (int i = startIndex; i <= endIndex; i++) {
      ILayer layer = this.getLayers().get(i);
      ImageImpl layerImage = (ImageImpl) layer.getImage();
      int offsetX = layer.getX();
      int offsetY = layer.getY();

      // Iterate through all pixels of the layer image
      for (int y = 0; y < layerImage.getHeight(); y++) {
        for (int x = 0; x < layerImage.getWidth(); x++) {
          Pixel pixel = layerImage.getPixel(x, y);

          // Check if pixel is null
          if (pixel != null) {
            int newX = x + offsetX;
            int newY = y + offsetY;

            // Check if the new coordinates are within the result image boundaries
            if (newX >= 0 && newX < mergedImage.getWidth()
                    && newY >= 0
                    && newY < mergedImage.getHeight()) {
              // Set the pixel in the result image
              mergedImage.setPixel(newX, newY, pixel);
            }
          }
        }
      }
    }

    return mergedImage;
  }

  /**
   * Get the image of the project as a buffered image.
   *
   * @return buffered image of current project
   */
  @Override
  public BufferedImage getProjectImage(List<ILayer> layers) {
    if (layers.isEmpty()) {
      return null;
    }

    BufferedImage projectImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g = projectImage.createGraphics();

    for (ILayer layer : layers) {
      if (layer.hasImage()) {
        IImage layerIimage = layer.getImage();
        BufferedImage layerImage = layerIimage.toBufferedImage();
        int xOffset = layer.getX();
        int yOffset = layer.getY();
        g.drawImage(layerImage, xOffset, yOffset, null);
      }
    }

    g.dispose();

    return projectImage;
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

}
