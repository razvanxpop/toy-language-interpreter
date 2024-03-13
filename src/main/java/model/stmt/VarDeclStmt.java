package model.stmt;

import datastructure.IDictionary;
import exceptions.StatementException;
import model.prgstate.ProgramState;
import model.type.*;
import model.value.Value;

public class VarDeclStmt implements Statement {
  private final String id;
  private final Type type;

  public VarDeclStmt(String id, Type type) {
    this.id = id;
    this.type = type;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IDictionary<String, Value> symbolTable= state.getSymbolTable();

    if(symbolTable.isDefined(id)) {
      throw new StatementException("The variable " + id + " is already declared!");
    } else{
      symbolTable.put(id, type.defaultValue());
    }

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    typeEnvironment.put(id, type);
    return typeEnvironment;
  }

  @Override
  public Statement deepCopy() {
    return new VarDeclStmt(id, type);
  }

  @Override
  public String toString() {
    return type.toString() + " " + id;
  }
}
