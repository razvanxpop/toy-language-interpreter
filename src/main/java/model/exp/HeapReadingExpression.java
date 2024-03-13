package model.exp;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.ExpressionException;
import exceptions.HeapException;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapReadingExpression implements Expression {
  private final Expression expression;

  public HeapReadingExpression(Expression expression){
    this.expression = expression;
  }

  @Override
  public Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException {
    Value expressionValue = expression.eval(symbolTable, heap);

    if(!expressionValue.getType().equals(new ReferenceType(null))){
      throw new ExpressionException("The expression " + expression + " is not a type ReferenceType!");
    }

    ReferenceValue referenceValue = (ReferenceValue) expressionValue;
    try {
      if (!heap.search(referenceValue.getAddress())) {
        throw new ExpressionException("The address " + referenceValue.getAddress() + " is not in the heap!");
      }
    } catch(HeapException exception){
      throw new ExpressionException(exception.getMessage());
    }

    Value value;
    try{
      value = heap.get(referenceValue.getAddress());
    } catch(HeapException exception){
      throw new ExpressionException(exception.getMessage());
    }
    return value;
  }

  @Override
  public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException {
    Type expressionType = expression.typeCheck(typeEnvironment);
    if(expressionType instanceof ReferenceType referenceType){
      return referenceType.getInner();
    } else {
      throw new ExpressionException("The expression " + expression + " is not a type ReferenceType!");
    }
  }

  @Override
  public Expression deepCopy() {
    return new HeapReadingExpression(expression.deepCopy());
  }

  @Override
  public String toString() {
    return "heapRead(" + expression.toString() + ")";
  }
}
