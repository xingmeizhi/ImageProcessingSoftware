package view;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Command;
import controller.Features;
import model.ILayer;

/**
 * A view that provides a user-friendly approach to using the collage program. Uses a JFrame and
 * implements the interface for GUIview.
 */
public class CollageGUIViewImpl extends JFrame implements CollageGUIView {

  private Features features;
  private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  private final JLabel layerLabel;
  private JButton loadButton;
  private JButton saveProjectButton;
  private JButton saveImageButton;
  private JButton addLayerButton;
  private JButton addImageToLayerButton;
  private JButton newProjectButton;

  private JPanel layerButtonsPanel;

  private JPanel imageViewPanel = new JPanel();


  private JRadioButton[] filterButtons;
  private ButtonGroup allFilterButtons;
  private String selectedLayerName;


  /**
   * Constructor for the CollageGUIViewImpl that sets up the GUI and add the buttons.
   */
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

  /**
   * Renders a message.
   * @param message the message to be rendered.
   */
  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this,
            message);
  }

  /**
   * Renders an error.
   * @param message the error to be rendered.
   */
  @Override
  public void renderError(String message) {
    JOptionPane.showMessageDialog(this, message,
            "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Resets the filter buttons so that none have been clicked.
   */
  @Override
  public void reset() {
    this.allFilterButtons.clearSelection();
  }



  /**
   * Assigns the features of the load, save image, save project, new project, add image, and
   * add image to layer buttons.
   *
   * @param features the features
   */
  @Override
  public void features(Features features) {

    this.features = features;

    //load project
    loadButton.addActionListener(e -> {
      final JFileChooser chooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Collage file",
              "collage");
      chooser.setFileFilter(filter);
      int retValue = chooser.showOpenDialog(this);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        String path = file.getAbsolutePath();
        try {
          features.loadProject(path);
          renderMessage("Project loaded successfully");
        } catch (IllegalArgumentException exception) {
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
        int value = fileChooser.showSaveDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String pathname = file.getAbsolutePath();
          features.saveImage(pathname);
        }
      } else {
        renderError("No active project. Please create or load a project first.");
      }


    });


    newProjectButton.addActionListener(e -> {
      features.runCommand(Command.newProject);

    });


    // add layer
    addLayerButton.addActionListener(event -> {
      if (features.hasActiveProject()) {
        String layerName = JOptionPane.showInputDialog(null,
                "Enter a layer name:", "Add Layer", JOptionPane.QUESTION_MESSAGE);
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
  }


  /**
   * Uses setIcon to actually show the user the image.
   * @param project the image of the current project
   * @param layers the list of layers in the project
   */
  @Override
  public void displayLayer(BufferedImage project, List<ILayer> layers) {
    if (project != null) {
      this.layerLabel.setIcon(new ImageIcon(project));
    }
  }


  /**
   * Helper method to ask user for the name of the layer.
   *
   * @param title the name of the layer
   * @return the layer name
   */
  private String getLayerNamePopUp(String title) {
    return JOptionPane.showInputDialog(this, title, "Enter Layer Name",
            JOptionPane.PLAIN_MESSAGE);
  }


  /**
   * Gets an int from the user to be used in editing.
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
   * Gets a string from the user to be used in editing.
   *
   * @param title Prompt for the user
   * @return the user inputted string
   */
  @Override
  public String getStringPopUp(String title) {
    return JOptionPane.showInputDialog(this, title, "Enter value",
            JOptionPane.PLAIN_MESSAGE);
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
   * The panel that displays the image.
   * @param width the width of the panel
   * @param height the height of the panel
   * @return the JPanel that displays the image
   */
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
    imageViewPanel.add(imageScroll);
    return imageViewPanel;
  }


  /**
   * Makes a new panel with all the options for editing a project.
   * @return the editing JPanel
   */
  private JPanel makeEditOptions() {
    JPanel editOptions = new JPanel();
    editOptions.setLayout(new BoxLayout(editOptions, BoxLayout.X_AXIS));

    // Layer buttons
    JPanel layerOptionsPanel = new JPanel();
    layerOptionsPanel.setLayout(new BorderLayout());
    layerOptionsPanel.add(new JLabel("Layers:"), BorderLayout.NORTH);
    Box box2 = Box.createVerticalBox();
    box2.add(layerButtonsPanel);
    layerOptionsPanel.add(box2, BorderLayout.SOUTH);
    editOptions.add(layerOptionsPanel);
    editOptions.add(box2);

    editOptions.add(Box.createRigidArea(new Dimension(20, 0)));

    // Filter buttons
    JPanel filters = new JPanel();
    filters.setLayout(new BorderLayout());
    filters.add(new JLabel("Filters:"), BorderLayout.NORTH);

    Box box1 = Box.createVerticalBox();
    String[] filterNames = new String[]{"Normal", "Blue-Component", "Red-Component",
                                        "Green-Component", "Darken-VALUE", "Darken-INTENSITY",
                                        "Darken-LUMA", "Brighten-VALUE", "Brighten-INTENSITY", "" +
                                        "Brighten-LUMA", "Difference",
                                        "Screen", "Multiply"};
    // assign all the different filters to buttons
    filterButtons = new JRadioButton[filterNames.length];
    allFilterButtons = new ButtonGroup();

    for (int i = 0; i < filterNames.length; i++) {
      filterButtons[i] = new JRadioButton(filterNames[i]);
      filterButtons[i].setActionCommand(filterNames[i].toLowerCase() + "\n");
      filterButtons[i].setSelected(false);
      allFilterButtons.add(filterButtons[i]);
      box1.add(filterButtons[i]);
    }

    filters.add(box1, BorderLayout.CENTER);
    editOptions.add(filters);

    JButton applyFilterButton = new JButton("Apply");
    editOptions.add(applyFilterButton);

    // Filter listener
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


  /**
   * Retrieve the filter that user selects.
   *
   * @return the name of the filter
   */
  private String getSelectedFilter() {
    for (JRadioButton filterButton : filterButtons) {
      if (filterButton.isSelected()) {
        return filterButton.getActionCommand().trim();
      }
    }
    return null;
  }


  /**
   * Update the layer buttons to add new buttons when a new layer is added.
   */
  public void updateLayerButtons() {
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
