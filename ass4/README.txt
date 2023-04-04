This program is a text-based image editor which users can edit images using different commands. Here are the commands:
1. new-project canvas-height canvas-width: creates a new project with the given name and given dimensions. Every project has a white background layer by default.
2. load-project path-to-project-file: loads a project into the program
3. save-project: save the project as one file as described above
4. add-layer layer-name: adds a new layer with the given name to the top of the whole project. This layer always has a fully transparent white image and the Normal filter on by default. Any attempt at creating another layer with the same name reports an error to the user, but continues the program.
5. add-image-to-layer layer-name image-name x-pos y-pos: places an image on the layer such that the top left corner of the image is at (x-pos, y-pos)
6. set-filter layer-name filter-option: sets the filter of the given layer where filter-option is one of the following at the moment
7. save-image file-name: save the result of applying all filters on the image
8. quit: quits the project and loses all unsaved work


The design is based on MVC, there’s nothing in the view yet because this is a text-baed editor.


The model has five different interface:
1. IFilter: this contains apply and getName method, to apply the filter to the given image and get the name of the filter.
2. CollageModel: this class represents a CollageModel interface that contains all the methods relate to users'command such as saving and loading.
3. IImage: this is an image interface that has all the methods relates to the image.
4. IProject: this interface has all the methods relate to the project users create.
5. ILayer: this interface has all the methods relate to the layers.
6. IPixel: this interface has all the methods relate to the pixel.
Each implementations are designed to handel the methods from the interface. There’s one thing special about the IFilter interface is that in order to handel with duplicate code, I created an abstraction class. And under filter package, there’re all the filters.
There’s also a Pixel class to save the rgb value.


The controller has one interface:
1. CollageController that contains a run method to run the program.
controller package also has an enum class to save all the commands.

Things change from A4 to A5:
1. Save and Load method fixed, now it can read from a file path.
2. Add a pixel interface instead of just a class, so we can add method that covert RGB and HSL.


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