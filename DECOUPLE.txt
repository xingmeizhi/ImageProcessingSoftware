VIEW:
- 
As it relates to decoupling, there was little that we had to do as our view only utilised 
two interfaces which were the Features interface the ILayer interface. The ILayer interface
was used to update the layer buttons as new layers were added to the project and to access the 
method to get a layer by name for editing. The other interface that was used in the view was
the features interface, which allowed the view to access the controller and call functions when 
buttons were pressed. The other file that was passed in was the enum of the various commands
that are accepted by the program.

CONTROLLER:
- 
The files that we need to send over for the controller to compile was the CollageModel, ILayer, 
and IProject interfaces as well as the CollageGUIView. The CollageModel interface is necessary 
because it holds all the functions related to actually having the program work. The ILayer interface
is necessary because there are numerous functions like addImages to layers, adding Layers, or 
filtering specific layers. The ILayer interface allows for retrieval of these layers or creating  
new layers. The IProject interface is useful because it acts similar to the model, just with a 
separate distinction for items related to layers and images. The view interface is necessary because 
you need a view to have something for the user to interact with. 