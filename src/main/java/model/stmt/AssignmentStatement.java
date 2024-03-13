package model.stmt;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.exp.Expression;
import model.prgstate.ProgramState;
import model.type.Type;
import model.value.Value;

public class AssignmentStatement implements Statement {
  private final String id;
  private final Expression expression;

  public AssignmentStatement(String id, Expression expression) {
    this.id = id;
    this.expression = expression;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IDictionary<String, Value> symbolTable = state.getSymbolTable();
    IHeap heap = state.getHeap();

    try {
      if (symbolTable.isDefined(id)) {
        Value expressionValue = expression.eval(symbolTable, heap);
        Type typeId = (symbolTable.get(id)).getType();
        if (expressionValue.getType().equals(typeId)) {
          symbolTable.replace(id, expressionValue);
        } else
          throw new StatementException("The type of the variable " + id + " and the type of the assigned expression do not match!");
      } else throw new StatementException("The variable " + id + " was not declared before assigning!");
    } catch(ExpressionException | DictionaryException exception){
      throw new StatementException(exception.getMessage());
    }

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    try {
      Type variableType = typeEnvironment.get(id);
      Type expressionType = expression.typeCheck(typeEnvironment);
      if(variableType.equals(expressionType)){
        return typeEnvironment;
      } else {
        throw new StatementException("right hand side and left hand side have different types!");
      }
    } catch(DictionaryException | ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }
  }

  @Override
  public Statement deepCopy() {
    return new AssignmentStatement(id, expression.deepCopy());
  }

  @Override
  public String toString() {
    return id + " = " + expression.toString();
  }
}
