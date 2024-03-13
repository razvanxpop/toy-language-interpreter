package com.example.demo;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgramWindow {
  public void openNewWindow(Controller controller) throws IOException {
    Stage stage = new Stage();
    FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("program-window.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
    ProgramWindowController c = fxmlLoader.getController();
    c.changeController(controller);
    stage.setTitle("Toy Language Interpreter");
    stage.setScene(scene);
    stage.show();
  }
}
