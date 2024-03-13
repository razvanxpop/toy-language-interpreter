package model.stmt;

import datastructure.IHeap;
import datastructure.IDictionary;
import datastructure.IStack;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.exp.Expression;
import model.prgstate.ProgramState;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class IfStatement implements Statement {
  private final Expression expression;
  private final Statement thenStatement;
  private final Statement elseStatement;
  public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
    this.expression = expression;
    this.thenStatement = thenStatement;
    this.elseStatement = elseStatement;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IStack<Statement> executionStack = state.getExecutionStack();
    IDictionary<String, Value> symbolTable = state.getSymbolTable();
    IHeap heap = state.getHeap();

    Value value;
    try {
      value = expression.eval(symbolTable, heap);
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!value.getType().equals(new BoolType())){
      throw new StatementException("The value of the conditional expression " + expression + " is not of type BoolType!");
    } else{
      BoolValue condition = (BoolValue) value;
      if(condition.getValue()){
        executionStack.push(thenStatement);
      } else {
        executionStack.push(elseStatement);
      }
    }
    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    Type expressionType;
    try {
      expressionType = expression.typeCheck(typeEnvironment);
    } catch(ExpressionException exception) {
      throw new StatementException(exception.getMessage());
    }
    if(expressionType.equals(new BoolType())){
      thenStatement.typeCheck(typeEnvironment.deepCopy());
      elseStatement.typeCheck(typeEnvironment.deepCopy());
      return typeEnvironment;
    } else {
      throw new StatementException("The conditional expression " + expression + " is not of type BoolType!");
    }
  }

  @Override
  public Statement deepCopy() {
    return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
  }

  @Override
  public String toString() {
    return "(if("+expression.toString()+") then(" + thenStatement.toString() +") else("+ elseStatement.toString()+"))";
  }
}
