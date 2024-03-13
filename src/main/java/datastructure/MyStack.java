package datastructure;

import exceptions.ExecutionStackException;

import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
  private final Stack<T> stack = new Stack<>();

  @Override
  public void push(T item) {
    stack.push(item);
  }

  @Override
  public T pop() throws ExecutionStackException {
    try {
      return stack.pop();
    } catch(EmptyStackException exception) {
      throw new ExecutionStackException(exception.getMessage());
    }
  }

  @Override
  public boolean isEmpty() {
    return stack.isEmpty();
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for(T element: this.stack){
      stringBuilder.append(element.toString()).append('\n');
    }
    return stringBuilder.toString();
  }
}
