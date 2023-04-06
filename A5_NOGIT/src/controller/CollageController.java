package controller;

import java.io.IOException;

/**
 * This class represents a CollageController interface,
 * which has a run method to run the program and get users'
 * input.
 */
public interface CollageController {

  /**
   * Run the program, and get the commands from the users,
   * then take care of the command accordingly.
   *
   * @throws IOException if the file is not found
   *                     OR there's an error writing file
   */
  void run() throws IOException;
}
