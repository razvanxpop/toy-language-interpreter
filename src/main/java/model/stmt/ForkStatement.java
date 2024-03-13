package model.stmt;

import datastructure.IDictionary;
import datastructure.IStack;
import datastructure.MyStack;
import exceptions.StatementException;
import model.prgstate.ProgramState;
import model.type.Type;
import model.value.Value;

public class ForkStatement implements Statement {
  private final Statement statement;

  public ForkStatement(Statement statement){
    this.statement = statement;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IDictionary<String, Value> newSymbolTable = state.getSymbolTable().deepCopy();
    IStack<Statement> newExecutionStack = new MyStack<>();

    return new ProgramState(
        newExecutionStack, newSymbolTable, state.getFileTable(), state.getOutput(), state.getHeap(), statement);
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    statement.typeCheck(typeEnvironment.deepCopy());
    return typeEnvironment;
  }

  @Override
  public Statement deepCopy() {
    return new ForkStatement(statement.deepCopy());
  }

  @Override
  public String toString(){
    return "fork(" + statement.toString() + ")";
  }
}
