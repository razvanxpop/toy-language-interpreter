package model.stmt;

import datastructure.IDictionary;
import datastructure.IHeap;
import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.HeapException;
import exceptions.StatementException;
import model.exp.Expression;
import model.prgstate.ProgramState;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapAllocationStatement implements Statement {
  private final String id;
  private final Expression expression;

  public HeapAllocationStatement(String id, Expression expression){
    this.id = id;
    this.expression = expression;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IDictionary<String, Value> symbolTable = state.getSymbolTable();
    IHeap heap = state.getHeap();

    if(!symbolTable.isDefined(id)){
      throw new StatementException("The variable " + id + " is undefined!");
    }

    Value value;
    try {
      value = symbolTable.get(id);
    } catch(DictionaryException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!value.getType().equals(new ReferenceType(null))){
      throw new StatementException("The variable " + id + " is not of type ReferenceType!");
    }

    Value expressionValue;
    try {
      expressionValue = expression.eval(symbolTable, heap);
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!expressionValue.getType().equals((((ReferenceValue) value).getLocationType()))){
      throw new StatementException("The value of the expression " + expression + " is not of type ReferenceType!");
    }

    try {
      int address = heap.add(expressionValue);
      symbolTable.replace(id, new ReferenceValue(address, expressionValue.getType()));
    } catch(HeapException | DictionaryException exception){
      throw new StatementException(exception.getMessage());
    }

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    try{
      Type variableType = typeEnvironment.get(id);
      Type expressionType = expression.typeCheck(typeEnvironment);
      if(variableType.equals(new ReferenceType(expressionType))){
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
    return new HeapAllocationStatement(id, expression.deepCopy());
  }

  @Override
  public String toString(){
    return "heapAlloc("+ id + " " + expression.toString() + ")";
  }
}
