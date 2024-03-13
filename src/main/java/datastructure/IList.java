package datastructure;

import exceptions.ListException;

import java.util.List;

public interface IList<T> {
  void add(T value);

  T remove(int index) throws ListException;

  T pop() throws ListException;

  T get(int index) throws ListException;

  boolean isEmpty();

  List<T> getListFormat();

  void clear();
}
