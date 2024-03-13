package model.exp;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.type.Type;
import model.value.Value;

public class VarExpression implements Expression {
  private final String id;

  public VarExpression(String id){
    this.id = id;
  }

  @Override
  public Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException {
    try {
      return symbolTable.get(id);
    } catch(DictionaryException exception){
      throw new ExpressionException(exception.getMessage());
    }
  }

  @Override
  public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException {
    try {
      return typeEnvironment.get(id);
    } catch(DictionaryException exception){
      throw new ExpressionException(exception.getMessage());
    }
  }

  @Override
  public Expression deepCopy() {
    return new VarExpression(id);
  }

  @Override
  public String toString() {
    return id;
  }
}
