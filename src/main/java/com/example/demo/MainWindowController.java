package com.example.demo;

import programgenerator.ProgramStateGenerator;
import controller.Controller;
import datastructure.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.prgstate.ProgramState;
import model.stmt.Statement;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
  @FXML
  private ListView<String> selectProgramsList;

  private final ObservableList<String> selectProgramsListItems = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    selectProgramsList.setItems(selectProgramsListItems);

    ArrayList<Statement> programs = (ArrayList<Statement>) ProgramStateGenerator.getPrograms();
    for(Statement stmt: programs){
      selectProgramsListItems.add(stmt.toString());
    }


  }

  @FXML
  public void selectButton() throws IOException {
    if (!selectProgramsList.getSelectionModel().isEmpty()){
      String stringStatement = String.valueOf(selectProgramsList.getSelectionModel().getSelectedItems());
      ArrayList<Statement> programs = (ArrayList<Statement>) ProgramStateGenerator.getPrograms();
      for(Statement stmt: programs){
        if(Objects.equals(stringStatement, "[" + stmt.toString() + "]")){
          IStack<Statement> executionStack = new MyStack<>();
          IDictionary<String, Value> symbolTable = new MyDictionary<>();
          IDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
          IList<Value> output = new MyList<>();
          IHeap heap = new MyHeap();
          ProgramState programState = new ProgramState(executionStack, symbolTable, fileTable, output, heap, stmt);
          IRepository repository = new Repository(programState, "../test.in");
          Controller controller = new Controller(repository);
          ProgramWindow newWindow = new ProgramWindow();
          newWindow.openNewWindow(controller);
        }
      }
    }
  }
}