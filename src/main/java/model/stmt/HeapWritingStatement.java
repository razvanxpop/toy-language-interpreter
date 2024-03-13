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

public class HeapWritingStatement implements Statement {
  private final String id;
  private final Expression expression;

  public HeapWritingStatement(String id, Expression expression){
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
    Value idValue;
    try {
      idValue = symbolTable.get(id);
    } catch(DictionaryException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!idValue.getType().equals(new ReferenceType(null))){
      throw new StatementException("The variable " + id + " is not of type ReferenceType!");
    }

    ReferenceValue referenceValue;
    try {
      referenceValue = (ReferenceValue) idValue;
      if (!heap.search(referenceValue.getAddress())) {
        throw new StatementException("The address of the ReferenceValue " + referenceValue + " was not found in the heap!");
      }
    } catch(HeapException exception){
      throw new StatementException(exception.getMessage());
    }

    Value expressionValue;
    try {
      expressionValue = expression.eval(symbolTable, heap);
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!expressionValue.getType().equals(referenceValue.getLocationType())){
      throw new StatementException("The type of the expression " + expression +
          " is not equal to the type of the ReferenceValue " + referenceValue.getLocationType() + "!");
    }

    try {
      heap.update(referenceValue.getAddress(), expressionValue);
    } catch(HeapException exception){
      throw new StatementException(exception.getMessage());
    }

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    try{
      Type variableType = typeEnvironment.get(id);
      if(!variableType.equals(new ReferenceType(null))){
        throw new StatementException("The variable " + id + " is not of type ReferenceType!");
      }
      Type expressionType = expression.typeCheck(typeEnvironment);
      if(expressionType.equals(((ReferenceType) variableType).getInner())){
        return typeEnvironment;
      } else {
       throw new StatementException("The expression " + expression + " is not of type " +
           ((ReferenceType) variableType).getInner());
      }
    } catch(DictionaryException | ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }
  }

  @Override
  public Statement deepCopy() {
    return new HeapWritingStatement(id, expression.deepCopy());
  }

  @Override
  public String toString(){
    return "heapWrite(" + id + ", " + expression.toString() + ")";
  }
}
