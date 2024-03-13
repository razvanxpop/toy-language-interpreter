package model.exp;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.ExpressionException;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicalExpression implements Expression {
  private final int operation;
  private final Expression firstExpression;
  private final Expression secondExpression;

  public LogicalExpression(int operation, Expression firstExpression, Expression secondExpression){
    this.operation = operation;
    this.firstExpression = firstExpression;
    this.secondExpression = secondExpression;
  }

  @Override
  public Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException {
    Value firstValue, secondValue;
    firstValue = firstExpression.eval(symbolTable, heap);
    if(firstValue.getType().equals(new BoolType())){
      secondValue = secondExpression.eval(symbolTable, heap);
      if(secondValue.getType().equals(new BoolType())){
        BoolValue firstBoolValue = (BoolValue)firstValue;
        BoolValue secondBoolValue = (BoolValue)secondValue;
        boolean firstBoolean, secondBoolean;
        firstBoolean = firstBoolValue.getValue();
        secondBoolean = secondBoolValue.getValue();
        if(operation == 1) return new BoolValue(firstBoolean && secondBoolean);
        if(operation == 2) return new BoolValue(firstBoolean || secondBoolean);
      } else throw new ExpressionException("The expression " + secondExpression + " is not of type BoolType!");
    } else throw new ExpressionException("The expression " + firstExpression + " is not of type BoolType!");
    return null;
  }

  @Override
  public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException {
    Type firstExpressionType = firstExpression.typeCheck(typeEnvironment);
    Type secondExpressionType = secondExpression.typeCheck(typeEnvironment);
    if(firstExpressionType.equals(new BoolType())){
      if(secondExpressionType.equals(new BoolType())){
        return new BoolType();
      } else {
        throw new ExpressionException("The expression " + secondExpression + " is not of type BoolType!");
      }
    } else {
      throw new ExpressionException("The expression " + firstExpression + " is not of type BoolType!");
    }

  }

  @Override
  public Expression deepCopy() {
    return new LogicalExpression(operation, firstExpression.deepCopy(), secondExpression.deepCopy());
  }

  @Override
  public String toString() {
    String operator = "";
    if(operation == 1) operator = "&&";
    if(operation == 2) operator = "||";
    return firstExpression.toString() + " " + operator + " " + secondExpression.toString();
  }
}
