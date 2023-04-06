package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.CollageModel;
import model.CollageModelImpl;
import model.ILayer;
import model.IProject;
import model.LayerImpl;

/**
 * This class represents a CollageControllerImpl that handle the methods that
 * run the game.
 */
public class CollageControllerImpl implements CollageController {

  private IProject project;
  private final Map<String, Command> commandMap;
  private CollageModel model = new CollageModelImpl();


  /**
   * Constructs a CollageControllerImpl, initializes the commandMap
   * with possible commands and their enum values.
   */
  public CollageControllerImpl() {
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
   * Run the program, and get the commands from the users,
   * then take care of the command accordingly.
   *
   * @throws IOException if the file is not found
   *                     OR there's an error writing file
   */
  @Override
  public void run() throws IOException {
    Scanner scanner = new Scanner(System.in);
    boolean projectCreate = false;
    while (true) {
      System.out.println("Please enter command: ");
      if (!scanner.hasNextLine()) {
        break;
      }
      String input = scanner.nextLine();
      String[] inputs = input.split(" ");
      String commandString = inputs[0].toLowerCase();
      Command command = commandMap.get(commandString);

      if (command == Command.quit) {
        System.out.println("quit");
        break;
      }

      if (command == null) {
        System.out.println("Unknown command.");
        continue;
      }


      String[] args = new String[inputs.length - 1];
      System.arraycopy(inputs, 1, args, 0, inputs.length - 1);

      if (!projectCreate && command != Command.newProject) {
        if (command != Command.quit) {
          System.out.println("Please create a new project first");
        }
        continue;
      }

      try {
        switch (command) {
          case addLayer:
            String layerName_addLayer = args[0];
            project.addLayer(new LayerImpl(layerName_addLayer,
                    project.getWidth(), project.getHeight()));
            System.out.println("Layer added!");
            break;

          case saveImage:
            String fileName = args[0];
            model.saveImage(fileName);
            System.out.println("Image saved!");
            break;

          case newProject:
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            project = model.createProject(width, height);
            projectCreate = true;
            System.out.println("project created!");
            break;

          case setFilter:
            String layerName_setFilter = args[0];
            String filterName_setFilter = args[1];
            model.setFilter(layerName_setFilter, filterName_setFilter);
            System.out.println("Filter set!");
            break;

          case saveProject:
            String savepath = args[0];
            model.save(savepath);
            System.out.println("Project saved!");
            break;

          case loadProject:
            String loadpath = args[0];
            model.load(loadpath);
            System.out.println("Project loaded!");
            break;

          case addImageToLayer:
            String layerName_addImageToLayer = args[0];
            String imageName_addImageToLayer = args[1];
            int x = Integer.parseInt(args[2]);
            int y = Integer.parseInt(args[3]);
            ILayer layer = project.getLayerByName(layerName_addImageToLayer);
            if (layer == null) {
              System.out.println("Layer not found!");
            } else {
              model.addImageToLayer(layer, imageName_addImageToLayer, x, y);
            }
            System.out.println("Image added successfully!");
            break;

          case quit:
            System.out.println("quit");
            break;

          default:
            System.out.println("Unknown command");
        }

      } catch (IOException e) {
        e.getMessage();
      }
    }
    scanner.close();
  }

  /**
   * main method that use to test controller.
   *
   * @param args args
   * @throws IOException if there are error
   */
  public static void main(String[] args) throws IOException {
    CollageController controller = new CollageControllerImpl();
    controller.run();
  }
}
