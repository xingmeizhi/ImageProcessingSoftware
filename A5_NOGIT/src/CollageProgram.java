import java.io.IOException;

import controller.CollageController;
import controller.CollageControllerGUI;
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
   * @param args argument
   * @throws IOException if something fails when trying to run the program
   */
  public static void main(String[] args) throws IOException {
    CollageModel model = new CollageModelImpl();
    CollageGUIView view = new CollageGUIViewImpl();
    CollageController controller = new CollageControllerGUI(model, view);
    controller.run();
  }
}
