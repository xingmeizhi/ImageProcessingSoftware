import java.io.IOException;

import controller.CollageController;
import controller.CollageControllerGUI;
import model.CollageModel;
import model.CollageModelImpl;
import view.CollageGUIView;
import view.CollageGUIViewImpl;

public class CollageProgram {


  public static void main(String[] args) throws IOException {
    CollageModel model = new CollageModelImpl();
    CollageGUIView view = new CollageGUIViewImpl();
    CollageController controller = new CollageControllerGUI(model, view);
    controller.run();
  }
}
