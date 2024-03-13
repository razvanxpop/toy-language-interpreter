package repository;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.RepositoryException;
import model.prgstate.ProgramState;
import model.value.Value;

import java.util.List;


public interface IRepository {
  List<ProgramState> getAllProgramStates();

  void setAllProgramStates(List<ProgramState> newProgramStates);

  void logProgramStateExecution(ProgramState program) throws RepositoryException;

  IDictionary<String, Value> getSymbolTable() throws RepositoryException;

  IHeap getHeap() throws RepositoryException;
}
