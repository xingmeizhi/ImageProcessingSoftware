package model.filter;

import model.ILayer;
import model.IProject;
import model.ImageImpl;
import model.filter.blendingfilters.Difference;
import model.filter.blendingfilters.Multiply;
import model.filter.blendingfilters.Screen;

/**
 * This class represents an Allfilter class
 * that contains all the filters.
 */
public class Allfilter {

  /**
   * Create a filter with the given filter name.
   *
   * @param filterName filter name to be created
   * @return filter with given name
   * @throws IllegalArgumentException if filter name is null
   *                                  OR filter not found
   */
  public static IFilter createFilter(String filterName, ILayer layer, IProject project) {
    if (filterName == null) {
      throw new IllegalArgumentException("Filter name cannot be null");
    }

    if (filterName.equalsIgnoreCase("difference")
            || filterName.equalsIgnoreCase("screen")
            || filterName.equalsIgnoreCase("multiply")) {
      return createBlendingFilter(filterName, layer, project);
    } else {
      return createNonBlendingFilter(filterName);
    }
  }

  public static IFilter createFilter(String filterName) {
    return createFilter(filterName, null, null);
  }

  /**
   * helper method to create non blending filter.
   */
  private static IFilter createNonBlendingFilter(String filterName) {
    switch (filterName) {
      case "blue-component":
        return new BlueFilter();
      case "red-component":
        return new RedFilter();
      case "green-component":
        return new GreenFilter();
      case "normal":
        return new NormalFilter();
      case "darken-value":
        return new DarkenValue();
      case "darken-luma":
        return new DarkenLuma();
      case "brighten-luma":
        return new BrightenLuma();
      case "brighten-intensity":
        return new BrightenIntensity();
      case "darken-intensity":
        return new DarkenIntensity();
      case "brighten-value":
        return new BrightValue();
      case "testing":
        return new TestingFilter();
      default:
        throw new IllegalArgumentException("Filter not found: " + filterName);
    }
  }

  /**
   * Helper method to create blending filter.
   */
  private static IFilter createBlendingFilter(String filterName, ILayer layer, IProject project) {
    int layerIndex = project.getLayers().indexOf(layer);
    if (layerIndex <= 0) {
      throw new IllegalArgumentException("No layer under!");
    }
    ImageImpl mergedImage = project.mergeLayersInRange(0, layerIndex - 1);
    int offsetX = layer.getX();
    int offsetY = layer.getY();

    switch (filterName.toLowerCase()) {
      case "difference":
        return new Difference(layer, mergedImage, offsetX, offsetY);
      case "screen":
        return new Screen(layer, mergedImage, offsetX, offsetY);
      case "multiply":
        return new Multiply(layer, mergedImage, offsetX, offsetY);
      default:
        throw new IllegalArgumentException("Unsupported blending filter: " + filterName);
    }
  }
}


