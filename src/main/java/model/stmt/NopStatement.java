package model.stmt;

import datastructure.IDictionary;
import exceptions.StatementException;
import model.prgstate.ProgramState;
import model.type.Type;

public class NopStatement implements Statement {

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    return typeEnvironment;
  }

  @Override
  public Statement deepCopy() {
    return new NopStatement();
  }

  @Override
  public String toString() {
    return "NopStmt";
  }
}
