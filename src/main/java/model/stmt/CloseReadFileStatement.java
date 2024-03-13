package model.stmt;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.exp.Expression;
import model.prgstate.ProgramState;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements Statement {
  private final Expression expression;

  public CloseReadFileStatement(Expression expression) {
    this.expression = expression;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IDictionary<String, Value> symbolTable = state.getSymbolTable();
    IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
    IHeap heap = state.getHeap();

    Value expressionValue;
    try {
      expressionValue = expression.eval(symbolTable, heap);
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!expressionValue.getType().equals(new StringType())){
      throw new StatementException("The value of the expression " + expression + " is not of type StringType!");
    }

    StringValue stringValue = (StringValue) expressionValue;

    if(!fileTable.isDefined(stringValue)){
      throw new StatementException("The file " + stringValue + " was not found!");
    }

    try{
      BufferedReader bufferedReader = fileTable.get(stringValue);
      bufferedReader.close();
      fileTable.remove(stringValue, bufferedReader);
    } catch(IOException | DictionaryException exception) {
      throw new StatementException(exception.getMessage());
    }

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    try{
      Type expresionType = expression.typeCheck(typeEnvironment);
      if(expresionType.equals(new StringType())){
        return typeEnvironment;
      } else {
        throw new StatementException("The expression " + expression + " is not of type StringType!");
      }
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }
  }

  @Override
  public Statement deepCopy() {
    return new CloseReadFileStatement(expression.deepCopy());
  }

  @Override
  public String toString() {
    return "close(" + expression.toString() + ")";
  }
}
