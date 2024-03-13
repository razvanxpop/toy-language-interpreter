package datastructure;

import exceptions.HeapException;
import model.value.Value;

import java.util.Map;
import java.util.Set;

public interface IHeap {
  Integer add(Value content) throws HeapException;
  Value update(Integer address, Value content) throws HeapException;
  boolean search(Integer address) throws HeapException;
  Value get(Integer address) throws HeapException;
  void setContent(Map<Integer, Value> newContent);
  Map<Integer, Value> getContent();

  Set<Integer> keys();
  boolean isEmpty();
  Integer getFreeAddress();


}
