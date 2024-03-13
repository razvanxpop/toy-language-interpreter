package model.stmt;

import datastructure.IDictionary;
import datastructure.IStack;
import exceptions.StatementException;
import model.prgstate.ProgramState;
import model.type.Type;

public class CompoundStatement implements Statement {
  private final Statement firstStatement;
  private final Statement secondStatement;

  public CompoundStatement(Statement firstStatement, Statement secondStatement) {
    this.firstStatement = firstStatement;
    this.secondStatement = secondStatement;
  }
  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IStack<Statement> executionStack = state.getExecutionStack();
    executionStack.push(secondStatement);
    executionStack.push(firstStatement);

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    return secondStatement.typeCheck(firstStatement.typeCheck(typeEnvironment));
  }

  @Override
  public Statement deepCopy() {
    return new CompoundStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
  }

  @Override
  public String toString() {
    return "(" + firstStatement.toString() + ";" + secondStatement.toString() + ")";
  }
}
