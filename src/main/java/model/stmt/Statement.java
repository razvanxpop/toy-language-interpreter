package model.stmt;

import datastructure.IDictionary;
import exceptions.StatementException;
import model.prgstate.ProgramState;
import model.type.Type;

public interface Statement {
  ProgramState execute(ProgramState state) throws StatementException;
  IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException;
  Statement deepCopy();
}
