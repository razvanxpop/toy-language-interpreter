package model.exp;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.ExpressionException;
import model.type.Type;
import model.value.Value;

public interface Expression {
  Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException;
  Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException;
  Expression deepCopy();
}
