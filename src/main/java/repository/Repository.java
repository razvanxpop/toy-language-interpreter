package repository;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.RepositoryException;
import model.prgstate.ProgramState;
import model.value.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
  private final List<ProgramState> states = new ArrayList<>();
  private final String logFilePath;
  public Repository(ProgramState program, String logFilePath) {
    this.states.add(program);
    this.logFilePath = logFilePath;
  }


  @Override
  public List<ProgramState> getAllProgramStates() {
    return List.copyOf(states);
  }

  @Override
  public void setAllProgramStates(List<ProgramState> newProgramStates) {
    states.clear();
    states.addAll(newProgramStates);
  }

  @Override
  public void logProgramStateExecution(ProgramState program) throws RepositoryException {
    try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))){
      logFile.println(program);
    } catch(IOException error){
      throw new RepositoryException("Could not open log file!");
    }
  }

  @Override
  public IDictionary<String, Value> getSymbolTable() throws RepositoryException{
    return states.getFirst().getSymbolTable();
  }

  @Override
  public IHeap getHeap() throws RepositoryException{
    return states.getFirst().getHeap();
  }
}
