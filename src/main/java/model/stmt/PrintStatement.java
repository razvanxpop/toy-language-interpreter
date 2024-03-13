package model.stmt;

import datastructure.IHeap;
import datastructure.IDictionary;
import datastructure.IList;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.exp.Expression;
import model.prgstate.ProgramState;
import model.type.Type;
import model.value.Value;

public class PrintStatement implements Statement {
  private final Expression expression;

  public PrintStatement(Expression expression) {
    this.expression = expression;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IList<Value> output = state.getOutput();
    IDictionary<String, Value> symbolTable = state.getSymbolTable();
    IHeap heap = state.getHeap();

    try {
      Value value = this.expression.eval(symbolTable, heap);
      output.add(value);
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    try {
      expression.typeCheck(typeEnvironment);
      return typeEnvironment;
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }
  }

  @Override
  public Statement deepCopy() {
    return new PrintStatement(expression.deepCopy());
  }

  @Override
  public String toString() {
    return "print(" + expression.toString() + ")";
  }
}
