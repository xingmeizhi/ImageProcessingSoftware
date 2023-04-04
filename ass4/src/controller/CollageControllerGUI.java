package controller;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CollageModel;
import model.ILayer;
import model.IProject;
import model.LayerImpl;
import view.CollageGUIView;

import static java.lang.System.exit;

public class CollageControllerGUI implements Features, CollageController {

  private IProject project;

  private CollageModel model;
  private CollageGUIView view;
  private final Map<String, Command> commandMap;

  private boolean projectExists;


  public CollageControllerGUI(CollageModel project, CollageGUIView view) {
    this.model = project;
    this.view = view;
    projectExists = false;
    commandMap = new HashMap<>();
    commandMap.put("new-project", Command.newProject);
    commandMap.put("load-project", Command.loadProject);
    commandMap.put("save-project", Command.saveProject);
    commandMap.put("add-layer", Command.addLayer);
    commandMap.put("add-image-to-layer", Command.addImageToLayer);
    commandMap.put("set-filter", Command.setFilter);
    commandMap.put("save-image", Command.saveImage);
    commandMap.put("quit", Command.quit);
  }


  /**
   * Loads an image into the GUI.
   *
   * @param projectPath the path to reach the project
   */
  @Override
  public void loadProject(String projectPath) {
    try {
      project = model.load(projectPath);
      view.reset();
      reload();
      BufferedImage combinedImage = getCombinedImage(project.getLayers());
      view.displayLayer(combinedImage, project.getLayers());
      projectExists = true;
    } catch (IOException exception) {
      view.renderError(exception.getMessage());
    }
  }


  @Override
  public void run(Command command) {
    try {
      if (command == Command.quit) {
        view.renderMessage("quit");
        exit(0);
      }

      if (command == null) {
        view.renderError("Unknown command.");
      }

      if (!projectExists && command != Command.newProject) {
        view.renderError("Please create a new project first");
      }

      try {
        switch (command) {
          case addLayer:
            String layerName_addLayer = view.getStringPopUp("Enter Layer Name:");
            project.addLayer(new LayerImpl(layerName_addLayer,
                    project.getWidth(), project.getHeight()));
            view.addtoLayerScroll(this, layerName_addLayer);
            reload();
            view.renderMessage("Layer added!");
            break;

          case saveImage:
            String fileName = view.getStringPopUp("Enter File Name:");
            model.saveImage(fileName);
            System.out.println("Image saved!");
            break;

          case saveProject:
            String savePath = view.getStringPopUp("Enter File Path:");
            model.save(savePath);
            System.out.println("Project saved!");
            break;

          case newProject:
            int width = view.getIntPopUp("Enter Width");
            int height = view.getIntPopUp("Enter Height");

            project = model.createProject(width, height);
            projectExists = true;
            reload();
            view.renderMessage("project created!");
            break;

          case setFilter:
            String layerName_setFilter = view.getStringPopUp("Enter Layer Name");
            String filterName_setFilter = view.getStringPopUp("Enter Filter Name");
            model.setFilter(layerName_setFilter, filterName_setFilter);
            reload();
            view.renderMessage("Filter set!");
            break;

          case addImageToLayer:
            String layerName_addImageToLayer = view.getStringPopUp("Enter Layer Name");
            String imageName_addImageToLayer = view.getStringPopUp("Enter Image Name");
            int x = view.getIntPopUp("Enter X Value");
            int y = view.getIntPopUp("Enter Y Value");
            ILayer layer = project.getLayerByName(layerName_addImageToLayer);
            if (layer == null) {
              view.renderMessage("Layer not found!");
            } else {
              model.addImageToLayer(layer, imageName_addImageToLayer, x, y);
            }
            reload();
            view.renderMessage("Image added successfully!");
            break;

          default:
            view.renderError("Unknown command");
        }

        BufferedImage combinedImage = getCombinedImage(project.getLayers());
        view.displayLayer(combinedImage, project.getLayers());
      } catch (IOException exception) {
        view.renderError(exception.getMessage());
      }
    } catch (IllegalArgumentException exception) {
      view.renderError(exception.getMessage());
    }

  }

  /**
   * Save the image to the given path.
   *
   * @param pathname the name of the path
   */
  @Override
  public void saveImage(String pathname) {
    try {
      model.saveImage(pathname);
    } catch (IOException exception) {
      view.renderError(exception.getMessage());
    }
  }

  /**
   * Save the project to the given filepath.
   *
   * @param filepath filepath to be saved
   */
  @Override
  public void saveProject(String filepath) {
    try {
      model.save(filepath);
    } catch (IOException exception) {
      view.renderError(exception.getMessage());
    }
  }

  /**
   * Add image to the current layer with given offset.
   *
   * @param layer     the layer to be added
   * @param imagepath the file path of the image
   * @param x         the x-coordinate of the layer
   * @param y         the y-coordinate of the layer
   */
  @Override
  public void addImageToLayer(ILayer layer, String imagepath, int x, int y) {
    try {
      model.addImageToLayer(layer, imagepath, x, y);
      BufferedImage combinedImage = getCombinedImage(project.getLayers());
      view.displayLayer(combinedImage, project.getLayers());
    } catch (FileNotFoundException e) {
      view.renderError(e.getMessage());
    }
  }

  /**
   * Get the layer with the given name.
   *
   * @param name the name of the layer to be found
   * @return the layer with given name
   */
  public ILayer getLayerByName(String name) {
    return project.getLayerByName(name);
  }

  /**
   * Get the list of layers of the current project.
   *
   * @return list of layers of the current project
   */
  @Override
  public List<ILayer> getProjectLayers() {
    if (project != null) {
      return project.getLayers();
    }
    return null;
  }


  /**
   * Set the filter with given filtername and layername.
   *
   * @param layerName  the name of the layer to be set
   * @param filterName the filter to set
   */
  @Override
  public void setFilter(String layerName, String filterName) {
    model.setFilter(layerName, filterName);
    BufferedImage combinedImage = getCombinedImage(project.getLayers());
    view.displayLayer(combinedImage, project.getLayers());
  }

  /**
   * Get the conbined image as a buffered image.
   *
   * @param layers the layers that contain image to be get
   * @return combined bufferedimage
   */
  @Override
  public BufferedImage getCombinedImage(List<ILayer> layers) {
    if (project != null) {
      return project.getProjectImage(layers);
    }
    return null;
  }

  /**
   * Return if there's an active project.
   *
   * @return if there's an active project
   */
  @Override
  public boolean hasActiveProject() {
    return this.projectExists;
  }


  @Override
  public void reload() {
    try {
//      view.displayLayer(bufferedImage(project.getLayerByName("Top Layer")), project.getLayers()); // need to figure this out
      BufferedImage combinedImage = getCombinedImage(project.getLayers());
      view.displayLayer(combinedImage, project.getLayers());
    } catch (IllegalArgumentException e) {
      view.renderError(e.getMessage());
    }
  }

  /**
   * Add a layer with given name to the current project.
   *
   * @param name the name of the layer to be added
   */
  @Override
  public void addLayer(String name) {
    ILayer layer = new LayerImpl(name, project.getWidth(), project.getHeight());
    try {
      project.addLayer(layer);
//      BufferedImage combinedImage = getCombinedImage(project.getLayers());
//      view.displayLayer(combinedImage, project.getLayers());
    } catch (IllegalArgumentException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void revertProject() {
    ILayer newLayer = project.getBottomLayer();
    project.addLayer(newLayer);
    view.reset();
  }


  @Override
  public void run() throws IOException {
    view.features(this);
    view.renderMessage("Welcome "); ///////////////////////// ADD EXPLANATION OF PROGRAM HERE
  }


//  private BufferedImage bufferedImage(ILayer layer) {
//
//
//
//
//    BufferedImage image =
//            new BufferedImage(model.getCols(), img.getRows(), BufferedImage.TYPE_INT_RGB);
//    for (int i = 0; i < img.getRows(); i++) {
//      for (int j = 0; j < img.getCols(); j++) {
//        Color c = new Color(
//                img.getPixel(i, j).getRed(),
//                img.getPixel(i, j).getGreen(),
//                img.getPixel(i, j).getBlue());
//        image.setRGB(j, i, c.getRGB());
//      }
//    }
//    return image;
//  }
}
