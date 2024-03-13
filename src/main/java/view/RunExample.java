package view;

import controller.Controller;
import exceptions.ControllerException;

public class RunExample extends Command {
  private final Controller controller;
  public RunExample(String key, String description, Controller controller){
    super(key, description);
    this.controller = controller;
  }
  @Override
  public void execute() {
    try{
      controller.allSteps();
    }
    catch(ControllerException exception) {
      System.out.println(exception.getMessage());
    }
  }
}
