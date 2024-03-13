package model.stmt;

import datastructure.IDictionary;
import datastructure.IHeap;
import datastructure.IStack;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.exp.Expression;
import model.prgstate.ProgramState;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class WhileStatement implements Statement{
  private final Expression expression;
  private final Statement statement;

  public WhileStatement(Expression expression, Statement statement){
    this.expression = expression;
    this.statement = statement;
  }

  @Override
  public ProgramState execute(ProgramState state) throws StatementException {
    IStack<Statement> executionStack = state.getExecutionStack();
    IDictionary<String, Value> symbolTable = state.getSymbolTable();
    IHeap heap = state.getHeap();

    Value expressionValue;
    try {
      expressionValue = expression.eval(symbolTable, heap);
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }

    if(!expressionValue.getType().equals(new BoolType())){
      throw new StatementException("The expression " + expression + " value is not of type BoolType!");
    }

    BoolValue boolValue = (BoolValue) expressionValue;
    if(boolValue.getValue()){
      executionStack.push(this);
      executionStack.push(statement);
    }

    return null;
  }

  @Override
  public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
    try{
      Type expressionType = expression.typeCheck(typeEnvironment);
      if(expressionType.equals(new BoolType())){
        statement.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
      } else {
        throw new StatementException("The expression " + expression + " is not of type BoolType!");
      }
    } catch(ExpressionException exception){
      throw new StatementException(exception.getMessage());
    }
  }

  @Override
  public Statement deepCopy() {
    return new WhileStatement(expression.deepCopy(), statement.deepCopy());
  }

  @Override
  public String toString(){
    return "while(" + expression.toString() + " " + statement.toString() + ")";
  }
}
