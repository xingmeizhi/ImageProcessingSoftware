package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.CollageControllerGUI;
import controller.Command;
import controller.Features;
import model.ILayer;

public class CollageGUIViewImpl extends JFrame implements CollageGUIView {

  private Features features;
  private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  private final JLabel layerLabel;
  private JButton loadButton;
  private JScrollPane layers;
  private JButton saveProjectButton;
  private JButton saveImageButton;
  private JButton addLayerButton;
  private JButton addImageToLayerButton;

  private JButton revertButton;

  private JButton newProjectButton;

  private JPanel layerButtonsPanel;

  private JPanel imageViewPanel = new JPanel();


  private JRadioButton[] filterButtons;
  private ButtonGroup allFilterButtons;
  private String selectedLayerName;

  private JList<String> listOfStrings;
  private JList<Integer> listOfIntegers;



  public CollageGUIViewImpl() {


    this.setTitle("Collage Project");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBackground(Color.GRAY);
    this.setSize(this.screenSize.width, this.screenSize.height);

    layerButtonsPanel = new JPanel();
    JPanel projectPanel = new JPanel();
    projectPanel.setLayout(new BorderLayout(0, 0));
    this.add(projectPanel);

    this.layerLabel = new JLabel();
    JPanel imageInfo = new JPanel();
    imageInfo.add(makeProjectDisplay(800, 800));


    projectPanel.add(makeSaveAndLoadButtons(
            "Save Project", "save-project",
            "Save Image", "save-image",
            "Load Project", "load-project",
            "New Project", "new-project",
            "Add Image to Layer", "add-image-to-layer"), BorderLayout.NORTH);
    projectPanel.add(makeAddLayerButton("Add Layer",
            "add-layer"), BorderLayout.WEST);

    projectPanel.add(makeEditOptions(), BorderLayout.EAST);
    projectPanel.add(imageInfo, BorderLayout.CENTER);

    setVisible(true);
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this,
            message);
  }

  @Override
  public void renderError(String message) {
    JOptionPane.showMessageDialog(this, message,
            "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void reset() {
    this.allFilterButtons.clearSelection();
  }


  //TODO: ask to create project even if user load a project.
  @Override
  public void features(Features features) {

    this.features = features;

    //load project
    loadButton.addActionListener(e -> {
      final JFileChooser chooser = new JFileChooser(".");
//      FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files",
//              "jpg", "jpeg", "png", "bmp", "ppm");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Collage file",
              "collage");
      chooser.setFileFilter(filter);
      int retValue = chooser.showOpenDialog(this);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        String path = file.getAbsolutePath();
//        String imageName = file.getName();
        try{
          features.loadProject(path);
          renderMessage("Project loaded successfully");
        }catch (IllegalArgumentException exception){
          renderError(exception.getMessage());
        }
      }
    });


    //save project
    saveProjectButton.addActionListener(e -> {
      if (features.hasActiveProject()) {
        final JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Collage Project Files",
                "collage");
        fileChooser.setFileFilter(filter);
        int value = fileChooser.showSaveDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String path = file.getAbsolutePath();
          if (!path.endsWith(".collage")) {
            path = path + ".collage";
          }
          features.saveProject(path);
          renderMessage("Project saved successfully");
        }
      } else {
        renderError("No active project. Please create or load a project first.");
      }

    });


    // save image
    saveImageButton.addActionListener(e -> {
      if (features.hasActiveProject()) {
        final JFileChooser fileChooser = new JFileChooser(".");
//      FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files",
//              "jpg", "jpeg", "png", "bmp", "ppm");
//      fileChooser.setFileFilter(filter);
//      int value = fileChooser.showOpenDialog(this);
        int value = fileChooser.showSaveDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String pathname = file.getAbsolutePath();
          features.saveImage(pathname);
          renderMessage("Image saved successfully");
        }
      } else {
        renderError("No active project. Please create or load a project first.");
      }


    });


    newProjectButton.addActionListener(e -> {
      features.run(Command.newProject);
    });


    // add layer
//    addLayerButton.addActionListener(
//            event -> features.run(Command.valueOf(event.getActionCommand())));
    addLayerButton.addActionListener(event -> {
      if (features.hasActiveProject()) {
        String layerName = JOptionPane.showInputDialog(null, "Enter a layer name:", "Add Layer", JOptionPane.QUESTION_MESSAGE);
        if (layerName != null && !layerName.isEmpty()) {
          features.addLayer(layerName);
          renderMessage("Layer added successfully.");
          updateLayerButtons();
        } else {
          renderError("Please enter a layer name.");
        }
      } else {
        renderError("No active project. Please create or load a project first.");
      }
    });


    //addImageToLayer
    addImageToLayerButton.addActionListener(e -> {
      if (features.hasActiveProject()) {
        JFileChooser chooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files",
                "jpg", "jpeg", "png", "bmp", "ppm");
        chooser.setFileFilter(filter);
        int retValue = chooser.showOpenDialog(this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          File file = chooser.getSelectedFile();
          String imagePath = file.getAbsolutePath();

          String layerName = getLayerNamePopUp("Enter the name of the layer:");
          if (layerName == null || layerName.isEmpty()) {
            renderError("Please enter a layer name.");
            return;
          }

          ILayer layer = features.getLayerByName(layerName);
          if (layer == null) {
            renderError("Layer not found.");
            return;
          }

          int xOffset = getIntPopUp("Enter x offset:");
          int yOffset = getIntPopUp("Enter y offset:");

          try {
            features.addImageToLayer(layer, imagePath, xOffset, yOffset);
            renderMessage("Image added to layer successfully.");
          } catch (Exception ex) {
            renderError(ex.getMessage());
          }
        }
      } else {
        renderError("No active project. Please create or load a project first.");
      }
    });


//    for (JRadioButton b : filterButtons) {
//      b.addActionListener(event -> features.run(Command.valueOf(event.getActionCommand())));
//    }

//    revertButton.addActionListener(event -> features.revertProject());
  }

  @Override
  public void displayLayer(BufferedImage project, List<ILayer> layers) {
    if (project != null) {
      this.layerLabel.setIcon(new ImageIcon(project));
    }
  }


  //Helper method to ask user for the name of the layer
  private String getLayerNamePopUp(String title) {
    return JOptionPane.showInputDialog(this, title, "Enter Layer Name",
            JOptionPane.PLAIN_MESSAGE);
  }


  /**
   * Gets an int from the user to be used in editing
   *
   * @param title Prompt for the user
   * @return the user inputted int
   */
  @Override
  public int getIntPopUp(String title) {
    return Integer.parseInt(JOptionPane.showInputDialog(this, title,
            "Enter value", JOptionPane.PLAIN_MESSAGE));
  }

  /**
   * Gets a string from the user to be used in editing
   *
   * @param title Prompt for the user
   * @return the user inputted string
   */
  @Override
  public String getStringPopUp(String title) {
    return JOptionPane.showInputDialog(this, title, "Enter value",
            JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void addtoLayerScroll(Features features, String name) {
    Button newLayer = new Button(name);
    layers.add(newLayer);
  }

  /**
   * Set the size of the canvas.
   *
   * @param width  the width of canvas
   * @param height the height of canvas
   */
  @Override
  public void setCanvasSize(int width, int height) {
    this.layerLabel.setPreferredSize(new Dimension(width, height));
    this.layerLabel.revalidate();
    this.layerLabel.repaint();
  }

  /**
   * Update the size of canvas.
   */
  @Override
  public void updateCanvasSize() {
    List<ILayer> layers = features.getProjectLayers();
    BufferedImage project = features.getCombinedImage(layers);
    if (project != null) {
      setCanvasSize(project.getWidth(), project.getHeight());
    }
  }


  /**
   * Makes an addLayer button.
   *
   * @param buttonText    the text within the button
   * @param buttonCommand what the button does
   * @return a panel consisting of the button
   */
  private JPanel makeAddLayerButton(String buttonText, String buttonCommand) {
    JPanel addLayerButtonPanel = new JPanel();
    addLayerButtonPanel.setBackground(Color.RED);
    addLayerButton = new JButton(buttonText);
    addLayerButton.setActionCommand(buttonCommand);
    addLayerButton.setSize(new Dimension(25, 5));
    addLayerButtonPanel.add(addLayerButton);
    return addLayerButtonPanel;
  }


  /**
   * Makes the panel for saving an image a project and loading a project.
   *
   * @param sPButtonText     the text within the save project button
   * @param sPButtonCommand  what the save project button does
   * @param sIButtonText     the text within the save image button
   * @param sIButtonCommand  what the save image button does
   * @param lPButtonText     the text within the load project button
   * @param lPButtonCommand  what the load project button does
   * @param nPButtonText     the text within the new project button
   * @param nPButtonCommand  what new project button does
   * @param aiLButtonText    the text within the add image to layer button
   * @param aiLButtonCommand what the add image to layer button does
   * @return the panel with the save image, save project, and load project buttons.
   */
  private JPanel makeSaveAndLoadButtons(String sPButtonText, String sPButtonCommand,
                                        String sIButtonText, String sIButtonCommand,
                                        String lPButtonText, String lPButtonCommand,
                                        String nPButtonText, String nPButtonCommand,
                                        String aiLButtonText, String aiLButtonCommand) {
    JPanel loadAndSaveButtonPanel = new JPanel();
    loadAndSaveButtonPanel.setBackground(Color.RED);

    saveProjectButton = new JButton(sPButtonText);
    saveProjectButton.setActionCommand(sPButtonCommand);
    saveProjectButton.setSize(new Dimension(25, 5));
    loadAndSaveButtonPanel.add(saveProjectButton);

    saveImageButton = new JButton(sIButtonText);
    saveImageButton.setActionCommand(sIButtonCommand);
    saveImageButton.setSize(new Dimension(25, 5));
    loadAndSaveButtonPanel.add(saveImageButton);

    loadButton = new JButton(lPButtonText);
    loadButton.setActionCommand(lPButtonCommand);
    loadButton.setSize(new Dimension(25, 5));
    loadAndSaveButtonPanel.add(loadButton);

    newProjectButton = new JButton(nPButtonText);
    newProjectButton.setActionCommand(nPButtonCommand);
    newProjectButton.setSize(new Dimension(25, 5));
    loadAndSaveButtonPanel.add(newProjectButton);

    addImageToLayerButton = new JButton(aiLButtonText);
    addImageToLayerButton.setActionCommand(aiLButtonCommand);
    addImageToLayerButton.setSize(new Dimension(25, 5));
    loadAndSaveButtonPanel.add(addImageToLayerButton);


    return loadAndSaveButtonPanel;
  }


  /**
   * Makes the panel that shows the user the image and the edits being done to it.
   *
   * @return the panel of the image
   */
//  private JPanel makeProjectDisplay() {
//    JPanel top = new JPanel();
//    top.add(new JLabel("Project:")).setFont(new Font(Font.SANS_SERIF, Font.BOLD,
//            16));
//    JPanel imageViewPanel = new JPanel();
//    imageViewPanel.setPreferredSize(new Dimension(600, 430));
//    imageViewPanel.add(top);
//
//
//    layerLabel.setHorizontalAlignment(JLabel.CENTER);
//    JScrollPane imageScroll = new JScrollPane(layerLabel);
//    imageScroll.setPreferredSize(new Dimension(600, 390));
//
//    imageViewPanel.add(imageScroll);
//    return imageViewPanel;
//  }

  private JPanel makeProjectDisplay(int width, int height) {
    JPanel top = new JPanel();
    top.add(new JLabel("Project:")).setFont(new Font(Font.SANS_SERIF, Font.BOLD,
            16));

    imageViewPanel.setPreferredSize(new Dimension(width, height));
    imageViewPanel.add(top);

    layerLabel.setHorizontalAlignment(JLabel.CENTER);
    JScrollPane imageScroll = new JScrollPane(layerLabel);
    imageScroll.setPreferredSize(new Dimension(width, height));

    imageViewPanel.add(imageScroll);
    imageViewPanel.add(layerButtonsPanel);
    imageViewPanel.add(imageScroll);
    return imageViewPanel;
  }


  private JPanel makeEditOptions() {
    JPanel editOptions = new JPanel();
    editOptions.setLayout(new BoxLayout(editOptions, BoxLayout.X_AXIS));
    editOptions.add(new JLabel("Options"));///Eh

    //Layer buttons
    JPanel layerOptionsPanel = new JPanel();
    layerOptionsPanel.setLayout(new BorderLayout());
    layerOptionsPanel.add(new JLabel("Layers"), BorderLayout.NORTH);
    layerOptionsPanel.add(layerButtonsPanel, BorderLayout.WEST);
    editOptions.add(layerOptionsPanel, BorderLayout.WEST);

    //Filter buttons
    JPanel filters = new JPanel();
    filters.add(new JLabel("Filters:"));
    Box box1 = Box.createVerticalBox();
    String[] filterNames = new String[]{"Normal", "Blue-Component", "Red-Component",
            "Green-Component", "Darken-VALUE", "Darken-INTENSITY",
            "Darken-LUMA", "Brighten-VALUE", "Brighten-INTENSITY", "Brighten-LUMA", "Difference",
            "Screen", "Multiply"};

    filterButtons = new JRadioButton[filterNames.length];
    allFilterButtons = new ButtonGroup();

    for (int i = 0; i < filterNames.length; i++) {
      filterButtons[i] = new JRadioButton(filterNames[i]);
      filterButtons[i].setActionCommand(filterNames[i].toLowerCase() + "\n");
      filterButtons[i].setSelected(false);
      allFilterButtons.add(filterButtons[i]);
      box1.add(filterButtons[i]);
    }

    filters.add(box1);
    editOptions.add(filters);


    JButton applyFilterButton = new JButton("Apply");
    editOptions.add(applyFilterButton);

    //filter listener
    applyFilterButton.addActionListener(e -> {

      if (!features.hasActiveProject()) {
        renderError("No active project. Please create or load a project first.");
        return;
      }

      String selectedFilter = getSelectedFilter();


      if (selectedFilter == null) {
        renderError("Please select a filter.");
        return;
      }


      String layerName = selectedLayerName;


      if (layerName == null || layerName.isEmpty()) {
        renderError("Please select a layer.");
        return;
      }

      try {
        features.setFilter(layerName, selectedFilter);
      } catch (IllegalArgumentException ex) {
        renderError(ex.getMessage());
        return;
      } catch (Exception ex) {
        renderError("An error occurred while applying the filter. " + ex.getMessage());
        return;
      }
      renderMessage("Filter applied successfully.");
    });
    return editOptions;
  }


  // get the filter that user selects.
  private String getSelectedFilter() {
    for (JRadioButton filterButton : filterButtons) {
      if (filterButton.isSelected()) {
        return filterButton.getActionCommand().trim();
      }
    }
    return null;
  }

  //update the layer buttons
  private void updateLayerButtons() {
    layerButtonsPanel.removeAll();
    List<ILayer> layers = features.getProjectLayers();
    for (ILayer layer : layers) {
      JButton layerButton = new JButton(layer.getName());
      layerButton.addActionListener(event -> {
        selectedLayerName = layer.getName();
      });
      layerButtonsPanel.add(layerButton);
    }
    layerButtonsPanel.revalidate();
    layerButtonsPanel.repaint();
  }


}
