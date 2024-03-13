package model.exp;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.ExpressionException;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RationalExpression implements Expression {
  private final int operation;
  private final Expression firstExpression;
  private final Expression secondExpression;

  public RationalExpression(int operation, Expression firstExpression, Expression secondExpression) {
    this.operation = operation;
    this.firstExpression = firstExpression;
    this.secondExpression = secondExpression;
  }
  @Override
  public Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException {
    Value firstValue, secondValue;
    firstValue = firstExpression.eval(symbolTable, heap);
    if(firstValue.getType().equals(new IntType())){
      secondValue = secondExpression.eval(symbolTable, heap);
      if(secondValue.getType().equals(new IntType())){
        IntValue firstIntValue = (IntValue)firstValue;
        IntValue secondIntValue = (IntValue)secondValue;
        int firstInteger, secondInteger;
        firstInteger = firstIntValue.getValue();
        secondInteger = secondIntValue.getValue();
        if(operation == 1) return new BoolValue(firstInteger < secondInteger);
        if(operation == 2) return new BoolValue(firstInteger <= secondInteger);
        if(operation == 3) return new BoolValue(firstInteger == secondInteger);
        if(operation == 4) return new BoolValue(firstInteger != secondInteger);
        if(operation == 5) return new BoolValue(firstInteger > secondInteger);
        if(operation == 6) return new BoolValue(firstInteger >= secondInteger);
      } else throw new ExpressionException("The expression " + secondExpression + " is not of type IntType!");
    } else throw new ExpressionException("The expression " + firstExpression + " is not of type IntType!");
    return null;
  }

  @Override
  public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException {
    Type firstExpressionType = firstExpression.typeCheck(typeEnvironment);
    Type secondExpressionType = secondExpression.typeCheck(typeEnvironment);
    if(firstExpressionType.equals(new IntType())){
      if(secondExpressionType.equals(new IntType())){
        return new BoolType();
      } else {
        throw new ExpressionException("The expression " + secondExpression + " is not of type IntType!");
      }
    } else {
      throw new ExpressionException("The expression " + firstExpression + " is not of type IntType!");
    }
  }

  @Override
  public Expression deepCopy() {
    return new RationalExpression(operation, firstExpression.deepCopy(), secondExpression.deepCopy());
  }

  @Override
  public String toString() {
    String operator = "";
    if(operation == 1) operator = "<";
    if(operation == 2) operator = "<=";
    if(operation == 3) operator = "==";
    if(operation == 4) operator = "!=";
    if(operation == 5) operator = ">";
    if(operation == 6) operator = ">=";
    return firstExpression.toString() +  " " + operator + " " + secondExpression.toString();
  }
}
