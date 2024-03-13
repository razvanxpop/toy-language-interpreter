package model.stmt;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.exp.Expression;
import model.prgstate.ProgramState;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement {
  private final Expression expression;
  private final String id;

  public ReadFileStatement(Expression expression, String id){
    this.expression = expression;
    this.id = id;
  }


  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IDictionary<String, Value> symbolTable = state.getSymbolTable();
    IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
    IHeap heap = state.getHeap();

    if(!symbolTable.isDefined(id)){
      throw new StatementException("The variable " + id + " is not defined!");
    }

    Value idValue;
    try {
      idValue = symbolTable.get(id);
    } catch(DictionaryException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!idValue.getType().equals(new IntType())){
      throw new StatementException("The value " + idValue + " is not of type int!");
    }

    Value value;
    try {
      value = expression.eval(symbolTable, heap);
    }catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!value.getType().equals(new StringType())){
      throw new StatementException("The value" + value + " is not of type StringType!");
    }
    StringValue stringValue = (StringValue) value;
    if(!fileTable.isDefined(stringValue)){
      throw new StatementException("The file " + stringValue + " was not open before read!");
    }

    try {
      BufferedReader bufferedReader = fileTable.get(stringValue);
      String line = bufferedReader.readLine();
      if(line == null) {
        symbolTable.replace(id, new IntType().defaultValue());
      } else {
        symbolTable.replace(id, new IntValue(Integer.parseInt(line)));
      }
    } catch(IOException | DictionaryException exception){
      throw new StatementException(exception.getMessage());
    }

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    try{
      Type variableType = typeEnvironment.get(id);
      if(!variableType.equals(new IntType())){
        throw new StatementException("The variable " + id + " is not of type IntType!");
      }
      Type expressionType = expression.typeCheck(typeEnvironment);
      if(!expressionType.equals(new StringType())){
        throw new StatementException("The expression" + expression + " is not of type StringType!");
      }
      return typeEnvironment;
    } catch(DictionaryException | ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }
  }

  @Override
  public Statement deepCopy() {
    return new ReadFileStatement(expression.deepCopy(), id);
  }

  @Override
  public String toString(){
    return "read(" + expression.toString() + ", " + id + ")";
  }
}
