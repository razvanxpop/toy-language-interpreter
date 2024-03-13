package model.prgstate;

import datastructure.*;
import exceptions.ExecutionStackException;
import exceptions.ProgramStateException;
import exceptions.StatementException;
import model.stmt.Statement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class ProgramState {
  private final IStack<Statement> executionStack;
  private final IDictionary<String, Value> symbolTable;
  private final IList<Value> output;
  private final IDictionary<StringValue, BufferedReader> fileTable;
  private final IHeap heap;
  private final Statement originalProgram;
  private final int id;
  private static int currentId = 0;

  public ProgramState(IStack<Statement> executionStack, IDictionary<String, Value> symbolTable, IDictionary<StringValue, BufferedReader> fileTable, IList<Value> output, IHeap heap, Statement program){
    this.executionStack = executionStack;
    this.symbolTable = symbolTable;
    this.fileTable = fileTable;
    this.output = output;
    this.heap = heap;
    this.originalProgram = program.deepCopy();

    this.id = ProgramState.generateId();
    executionStack.push(program);
  }

  public IStack<Statement> getExecutionStack() {
    return executionStack;
  }

  public IDictionary<String, Value> getSymbolTable() {
    return symbolTable;
  }

  public IList<Value> getOutput() {
    return output;
  }

  public IDictionary<StringValue, BufferedReader> getFileTable() {
    return fileTable;
  }

  public IHeap getHeap() { return heap; }

  public Statement getOriginalProgram(){
    return originalProgram;
  }

  public int getId(){
    return id;
  }

  public boolean isNotCompleted(){
    return !executionStack.isEmpty();
  }

  public ProgramState oneStep() throws ProgramStateException {
    if(executionStack.isEmpty()) throw new ProgramStateException("The execution stack is empty!");
    try {
      Statement currentStatement = executionStack.pop();
      return currentStatement.execute(this);
    } catch(ExecutionStackException | StatementException exception){
      throw new ProgramStateException(exception.getMessage());
    }
  }

  public static synchronized int generateId(){
    currentId++;
    return currentId;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("ProgramState ID: ").append(id).append('\n');
    stringBuilder.append("ExeStack:\n");
    if(executionStack.isEmpty())
      stringBuilder.append("########## The Execution Stack Is Empty! ##########").append('\n');
    else stringBuilder.append(executionStack);
    stringBuilder.append("SymTable:\n");
    if(symbolTable.isEmpty())
      stringBuilder.append("########## The Symbol Table Is Empty! ##########").append('\n');
    else stringBuilder.append(symbolTable);
    stringBuilder.append("Heap:\n");
    if(heap.isEmpty())
      stringBuilder.append("########## The Heap Is Empty! ##########").append('\n');
    else stringBuilder.append(heap);
    stringBuilder.append("FileTable:\n");
    if(fileTable.isEmpty())
      stringBuilder.append("########## The File Table Is Empty! ##########").append('\n');
    else stringBuilder.append(fileTable);
    stringBuilder.append("Output:\n");
    if(output.isEmpty())
      stringBuilder.append("########## The Output Is Empty! ##########").append('\n');
    else stringBuilder.append(output);
    return stringBuilder.toString();
  }
}
