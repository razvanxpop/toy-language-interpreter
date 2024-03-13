package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
  private final Map<String, Command> commands = new HashMap<>();
  public void addCommand(Command command){
    commands.put(command.getKey(),command);
  }
  private void printMenu(){
    for(Command command : commands.values()){
      String line=String.format("%4s : %s", command.getKey(), command.getDescription());
      System.out.println(line);
    }
  }
  public void show(){
    try(Scanner scanner=new Scanner(System.in)) {
      while(true) {
        printMenu();
        System.out.printf("Input the option: ");
        String key = scanner.nextLine();
        Command command = commands.get(key);
        if (command == null) {
          System.out.println("Invalid Option");
          continue;
        }
        command.execute();
      }
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
}
