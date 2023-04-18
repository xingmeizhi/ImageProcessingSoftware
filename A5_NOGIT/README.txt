This program is a image editor which users can edit images using different buttons (In the case
of the GUI) or command (In the case of the text-based program). When the user opens the program
they should go to the "Collage Program" file in the source code. They should run that file and if
they want to use the GUI they should put no arguments in the terminal. If they want to use the
text based program, they should enter "-text" when running the program. And if they want to use
a script (A selection of pre-written commands in a text format) they should enter the file path
ending in "-file" that contains the commands.

For the text based version here are the commands:
1. new-project canvas-height canvas-width: creates a new project with the given name and given
                                           dimensions. Every project has a white background layer
                                           by default.
2. load-project path-to-project-file: loads a project into the program
3. save-project: save the project as one file as described above
4. add-layer layer-name: adds a new layer with the given name to the top of the whole project.
                         This layer always has a fully transparent white image and the Normal filter
                         on by default. Any attempt at creating another layer with the same name
                         reports an error to the user, but continues the program.
5. add-image-to-layer layer-name image-name x-pos y-pos: places an image on the layer such that the
                                                         top left corner of the image is at
                                                         (x-pos, y-pos)
6. set-filter layer-name filter-option: sets the filter of the given layer where filter-option is
                                        one of the following at the moment
7. save-image file-name: save the result of applying all filters on the image
8. quit: quits the project and loses all unsaved work

For the GUI Based version there are a number of buttons. The order of usage of these buttons should
be as follows:
- Load a project or make a new project to start.
- Once project is open, add layers or images to the project and use the various filters on the
  right.
  - The filter options are as follows:
  - Normal: A normal filter
  - Blue-Component: Increases the blue in a picture
  - Red-Component: Increases the red in a picture
  - Green-Component: Increases the green in a picture
  - Darken-VALUE: Darken the value of an image
  - Darken-INTENSITY: Darken the intensity of an image
  - Darken-LUMA: Darken the luma of an image
  - Brighten-VALUE: Brighten the value of an image
  - Brighten-INTENSITY: Brighten the intensity of an image
  - Brighten-LUMA: Brighten the luma of an image
  - Difference: Takes the two pixels RGB components and subtracts them component wise
  - Screen: Use the lightness value of the composite image's pixels below and the current layer's
            pixels
  - Multiply: Multiply the values together to darken image
- As you add new layers, they will appear next to your image, click on them to make edits to that
  particular layer.
- Once finished, you can save the project as the image that is on your screen with the
  Save Image button.
- Additionally, you can save the project as it is for future edits.

Requirements to run the code:
- Java 11 or higher JRE
- JUnit 4 for running the tests

Note:
- The USE ME file can be found in the root in by the name "USEME.txt"

Citation:
readPPM method in ImageImpl class is cited from the starer code.
convertRGBtoHSL AND convertHSLtoRGB methoad are cited from the code given.
How to use System setOut to test is cited from http://www.java2s.com/example/java-api/java/lang/system/setout-1-4.html



A script of commands that your program will accept:
if you go to the main method in the CollageControllerImpl class, you can run the program with the command, here’s a simple script:
new-project 800 600 (create a project with 800 x 600 canvas)
add-layer layer1 (add a layer called layer1)
set-filter layer1 blue-component (set a blue-component filter on layer1)
add-image-to-layer layer1 yourimage.ppm x(int) y(int) (add yourimage.ppm to layer1 with given x and y offset)
add-layer layer2 (add a layer called layer2)
set-filter layer2 darken-value (set a darken filter based on the value)
add-image-to-layer yourimage2.ppm x y (add yourimage.ppm to layer2 with given x and y offset)
save-image path(string) (save the image to the given path)
save-project (save your project to the memory)
Load-project (load your project from the memory)
quit (quit the program)





Design:
The model has five different interfaces:
1. IFilter: this contains apply and getName method, to apply the filter to the given image and get
            the name of the filter.
2. CollageModel: this class represents a CollageModel interface that contains all the methods
                 relate to users'command such as saving and loading.
3. IImage: this is an image interface that has all the methods relates to the image.
4. IProject: this interface has all the methods relate to the project users create.
5. ILayer: this interface has all the methods relate to the layers.
6. IPixel: this interface has all the methods relate to the pixel.
Each implementation is designed to handle the methods from the interface. There’s one thing special
about the IFilter interface is that in order to handle with duplicate code, we created an
abstract class. And under filter package all the filters can be found. There’s also a Pixel class
to save the rgb value.


The controller has two interface:
1. CollageController that contains a run method to run the program.
controller package also has an enum class to save all the commands.
2. Features, the features that will be contained in our GUI.


The view has one interface:
1. CollageGUIView is the GUI view for the program that has methods to display a project, render
   errors and messages, and add features to all the buttons that are created in the implementation.



Update on A5:
The load and save method are complete, user can now load and save from a file path.
We added a Pixel interface instead of just a class because we put the convert to HSL method into the interface to make the program more organized.

How to use our GUI is very clear, users can simply click each buttons, they will need to create or load a project first.
We made layers a button for users to select so whenever they want to apply a filter to a certain layer they won't have to type the name of the layer, the layer will automatically add to the GUI.
We also added a method to convert our Imageimpl into a bufferedImage so it can be displayed on our GUI.

Update on A6:
The convertToBufferedImage static method in IImage interface that takes a IImage was deleted. Instead, we added a new method toBufferedImage, which takes no arguments and convert an imageimpl
Into a bufferedImage.
Also, read and write Image methods are added to the IImage interface. These methods use ImageIO to read and write image, readImage method will automatically initialize the
bufferedImage as an ImageImpl class. Write Image method uses ImageIO to save the Image with given filename, users can choose any formats they want by typing the name "example.format". As long as ImageIO support.
Some methods in the CollageModelImpl class are also changed to adjust our change. For instance, addImageToLayer method, if users want to add a PPM image to the layers, 
Our program will then use readPPM method, anything else, use the ImageIO method. Same with the writing, if the format is PPM, use write PPM, else use write image.

As it relates to decoupling, there was little that we had to do as our view only utilised two interfaces which were the Features interface the ILayer interface. The ILayer interface
was used to update the layer buttons as new layers were added to the project and to access the method to get a layer by name for editing. The other interface that was used in the view was
the features interface, which allowed the view to access the controller and call functions when buttons were pressed. The other file that was passed in was the enum of the various commands
that are accepted by the program.

The only changes that we made to the program was to add the support for command arguments, which was missing from our previous submission. This allows for the ability to run the program
with a script if so desired. We did not add any extra functionality in this iteration, however if we were to do so it would simply be a matter of creating a new button in the view, add
the feature to the features interface, the implement the feature in the GUI Controller. Our design is pretty good, we have a good interaction between our model, controller, and view. There
is not any access from our view to the model, and it only interacts with interfaces. The one thing that we would want to maybe consider changing in the future is the use of the switch statement
in our controller, as if we begin to add extensive functionality that switch statement would become increasingly long. To do this we could make an interface and have each of the commands
extend said interface so that there is no need for a switch statement, just an execute command in the controller that executes the command.

