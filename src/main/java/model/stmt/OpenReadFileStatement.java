package model.stmt;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.exp.Expression;
import model.prgstate.ProgramState;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements Statement {
  private final Expression expression;

  public OpenReadFileStatement(Expression expression){
    this.expression = expression;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IDictionary<String, Value> symbolTable = state.getSymbolTable();
    IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
    IHeap heap = state.getHeap();

    Value value;
    try {
      value = expression.eval(symbolTable, heap);
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!value.getType().equals(new StringType())){
      throw new StatementException("The value " + value + " is not of type StringType!");
    }
    StringValue stringValue = (StringValue) value;

    if(fileTable.isDefined(stringValue)){
      throw new StatementException("The file " + stringValue + " is already defined and opened!");
    }

    try{
      BufferedReader bufferedReader;
      bufferedReader = new BufferedReader(new FileReader(stringValue.getValue()));
      fileTable.put(stringValue, bufferedReader);
    }catch (FileNotFoundException exception){
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
    return new OpenReadFileStatement(expression.deepCopy());
  }

  @Override
  public String toString(){
    return "open(" + expression.toString() + ")";
  }
}
