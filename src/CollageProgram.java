import java.io.IOException;

import controller.CollageController;
import controller.CollageControllerGUI;
import controller.CollageControllerImpl;
import model.CollageModel;
import model.CollageModelImpl;
import view.CollageGUIView;
import view.CollageGUIViewImpl;


/**
 * The CollageProgram class for running a collage program.
 */
public class CollageProgram {


  /**
   * The main method to run the collage program.
   *
   * @param args argument
   * @throws IOException if something fails when trying to run the program
   */
  public static void main(String[] args) throws IOException {
    // Run the GUI version
    if (args.length == 0) {
      CollageModel model = new CollageModelImpl();
      CollageGUIView view = new CollageGUIViewImpl();
      CollageController controller = new CollageControllerGUI(model, view);
      controller.run();
      //Run the text version
    } else if (args.length == 1 && args[0].equals("-text")) {
      CollageModel model = new CollageModelImpl();
      CollageController controller = new CollageControllerImpl(model);
      controller.run();
      //Run the script and shut down
    } else if (args.length == 2 && args[0].equals("-file")) {
      String pathOfScriptFile = args[1];
      CollageModel model = new CollageModelImpl();
      CollageController controller = new CollageControllerImpl(model);
      ((CollageControllerImpl) controller).executeScriptFile(pathOfScriptFile);
    } else {
      System.err.println("Invalid command.");
    }

  }
}
