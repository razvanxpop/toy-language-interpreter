package datastructure;

import exceptions.ListException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyList<T> implements IList<T> {
  private final List<T> list = new ArrayList<>();

  @Override
  public void add(T value) {
    list.add(value);
  }

  @Override
  public T remove(int index) throws ListException{
    try {
      return list.remove(index);
    }catch(UnsupportedOperationException | IndexOutOfBoundsException exception){
      throw new ListException(exception.getMessage());
    }
  }

  public T pop() throws ListException {
    try{
      return list.removeLast();
    } catch(NoSuchElementException | UnsupportedOperationException exception){
      throw new ListException(exception.getMessage());
    }
  }

  @Override
  public T get(int index) throws ListException {
    try {
      return list.get(index);
    } catch(IndexOutOfBoundsException exception){
      throw new ListException(exception.getMessage());
    }
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public List<T> getListFormat() {
    return new ArrayList<T>(list);
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public String toString() {
    synchronized (list){
      StringBuilder stringBuilder = new StringBuilder();
      for(T element: list) {
        stringBuilder.append(element.toString()).append("\n");
      }
      return stringBuilder.toString();
    }
  }
}
