package com.example.demo;

import exceptions.HeapException;
import controller.Controller;
import datastructure.IDictionary;
import datastructure.IList;
import datastructure.IStack;
import exceptions.ControllerException;
import exceptions.DictionaryException;
import exceptions.RepositoryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.prgstate.ProgramState;
import model.stmt.Statement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;

public class ProgramWindowController implements Initializable {
  @FXML
  private ListView<String> executionStackList;
  private final ObservableList<String> executionStackItems = FXCollections.observableArrayList();
  @FXML
  private ListView<String> outputList;
  private final ObservableList<String> outputListItems = FXCollections.observableArrayList();
  @FXML
  private ListView<String> fileTableList;
  private final ObservableList<String> fileTableListItems = FXCollections.observableArrayList();
  @FXML
  private ListView<Object> threadsList;
  private final ObservableList<Object> threadsListItems = FXCollections.observableArrayList();
  @FXML
  private TextField threadCountText;
  private int threadsCounter = 0;

  private String selectedThread;
  private final ArrayList<Integer> threads = new ArrayList<>();

  @FXML
  private TableView<Heap> heapTable;
  @FXML
  private TableColumn<Heap, String> valueHeapTableColumn;
  @FXML
  private TableColumn<Heap, String> addressHeapTableColumn;
  private ObservableList<Heap> heapTableItems = FXCollections.observableArrayList();

  @FXML
  private TableView<SymbolTable> symbolTable;
  @FXML
  private TableColumn<SymbolTable, String> symbolTableValueColumn;
  @FXML
  private TableColumn<SymbolTable, String> symbolTableIdColumn;
  private final ObservableList<SymbolTable> symbolTableItems = FXCollections.observableArrayList();

  private Controller controller;
  public void changeController(Controller c){
    controller = c;
  }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    executionStackList.setItems(executionStackItems);

    addressHeapTableColumn.setCellValueFactory(new PropertyValueFactory<Heap, String>("address"));
    valueHeapTableColumn.setCellValueFactory(new PropertyValueFactory<Heap, String>("value"));
    heapTable.setItems(heapTableItems);

    symbolTableIdColumn.setCellValueFactory(new PropertyValueFactory<SymbolTable, String>("id"));
    symbolTableValueColumn.setCellValueFactory(new PropertyValueFactory<SymbolTable, String>("value"));
    symbolTable.setItems(symbolTableItems);

    outputList.setItems(outputListItems);
    fileTableList.setItems(fileTableListItems);
    threadsList.setItems(threadsListItems);
    threadsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      int selectedIndex = threadsList.getSelectionModel().getSelectedIndex();
      if (selectedIndex != -1) {
        IStack<Statement> executionStack = this.controller.getRepository().getAllProgramStates().get(selectedIndex).getExecutionStack();
        this.selectedThread = String.valueOf(this.threadsListItems.get(selectedIndex));
        executionStackItems.clear();
        executionStackItems.add(executionStack.toString());
      }
    });
  }

  @FXML
  public void oneStep() throws ControllerException, DictionaryException, RepositoryException, HeapException {
    List<ProgramState> programStates = this.controller.removeCompletedProgram(this.controller.getRepository().getAllProgramStates());
    if (!programStates.isEmpty()) {
      controller.oneStepForAllPrograms(programStates);
      IStack<Statement> executionStack = this.controller.getRepository().getAllProgramStates().getFirst().getExecutionStack();
      executionStackItems.clear();
      executionStackItems.add(executionStack.toString());

      IList<Value> output = this.controller.getRepository().getAllProgramStates().getFirst().getOutput();
      outputListItems.clear();
      outputListItems.add(output.toString());

      IDictionary<StringValue, BufferedReader> fileTable = this.controller.getRepository().getAllProgramStates().getFirst().getFileTable();
      fileTableListItems.clear();
      fileTableListItems.add(fileTable.toString());

      this.heapTableItems.clear();
      for (ProgramState programState : this.controller.getRepository().getAllProgramStates()) {
        for (Integer address : programState.getHeap().keys()) {
          Heap entry = new Heap(address, programState.getHeap().get(address));
          this.heapTableItems.add(entry);
        }
        break;
      }

      this.symbolTableItems.clear();
      for (ProgramState programState : this.controller.getRepository().getAllProgramStates()) {
        //System.out.println(programState.getId() + " " + selectedThread);
        //if (String.valueOf(programState.getId()).equals(this.selectedThread)) {
          for (String key : programState.getSymbolTable().keys()) {
            SymbolTable entry = new SymbolTable(key, programState.getSymbolTable().get(key));
            this.symbolTableItems.add(entry);
          }
          break;
        //}
      }

      this.fileTableListItems.clear();
      ProgramState programStatex = this.controller.getRepository().getAllProgramStates().getFirst();
      for(StringValue file: programStatex.getFileTable().keys()){
        fileTableListItems.add(file.toString());
      }

      threadsListItems.clear();
      int threadId = controller.getRepository().getAllProgramStates().getFirst().getId();
      for (ProgramState programState : programStates) {
        threadsListItems.add("Program state: " + programState.getId());
      }

      if (!threads.contains(threadId)) {
        threads.add(threadId);
        threadsCounter = threadsCounter + 1;
        threadCountText.setText("Thread Count: " + String.valueOf(threadsCounter));
      }
    }
  }

  @FXML
  public void allSteps() throws ControllerException, HeapException, DictionaryException {
    try {
      this.controller.allSteps();
      IStack<Statement> executionStack = this.controller.getRepository().getAllProgramStates().getFirst().getExecutionStack();
      executionStackItems.clear();
      executionStackItems.add(executionStack.toString());
      IList<Value> output = this.controller.getRepository().getAllProgramStates().getFirst().getOutput();
      outputListItems.clear();
      outputListItems.add(output.toString());
      IDictionary<StringValue, BufferedReader> fileTable = this.controller.getRepository().getAllProgramStates().getFirst().getFileTable();
      fileTableListItems.clear();
      fileTableListItems.add(fileTable.toString());
      this.heapTableItems.clear();
      for (ProgramState programState : this.controller.getRepository().getAllProgramStates()) {
        for (Integer address : programState.getHeap().keys()) {
          Heap entry = new Heap(address, programState.getHeap().get(address));
          this.heapTableItems.add(entry);
        }
        break;
      }

      this.symbolTableItems.clear();
      for (ProgramState programState : this.controller.getRepository().getAllProgramStates()) {
        //System.out.println(programState.getId() + " " + selectedThread);
        //if (String.valueOf(programState.getId()).equals(this.selectedThread)) {
        for (String key : programState.getSymbolTable().keys()) {
          SymbolTable entry = new SymbolTable(key, programState.getSymbolTable().get(key));
          this.symbolTableItems.add(entry);
        }
        break;
        //}
      }

      this.fileTableListItems.clear();
      ProgramState programStatex = this.controller.getRepository().getAllProgramStates().getFirst();
      for(StringValue file: programStatex.getFileTable().keys()){
        fileTableListItems.add(file.toString());
      }

      List<ProgramState> programStates = this.controller.removeCompletedProgram(this.controller.getRepository().getAllProgramStates());
      threadsListItems.clear();
      int threadId = controller.getRepository().getAllProgramStates().getFirst().getId();
      for (ProgramState programState : programStates) {
        threadsListItems.add("Program state: " + programState.getId());
      }

      if (!threads.contains(threadId)) {
        threads.add(threadId);
        threadsCounter = threadsCounter + 1;
        threadCountText.setText("Thread Count: " + String.valueOf(threadsCounter));
      }
    } catch (ControllerException e) {
      throw new RuntimeException(e);
    }
  }
}
