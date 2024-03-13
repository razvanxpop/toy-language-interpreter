package datastructure;

import exceptions.ExecutionStackException;

public interface IStack<T> {
  void push(T item);
  T pop() throws ExecutionStackException;
  boolean isEmpty();
}
