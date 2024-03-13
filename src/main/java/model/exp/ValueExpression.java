package model.exp;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.ExpressionException;
import model.type.Type;
import model.value.Value;

public class ValueExpression implements Expression {
  private final Value value;

  public ValueExpression(Value value){
    this.value = value;
  }

  @Override
  public Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException {
    return value;
  }

  @Override
  public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException {
    return value.getType();
  }

  @Override
  public Expression deepCopy() {
    return new ValueExpression(value);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
