package datastructure;

import exceptions.HeapException;
import model.value.Value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyHeap implements IHeap {

  private Map<Integer, Value> heap = new HashMap<Integer, Value>();
  private int freeLocation = 1;

  public int getFreeLocation() { return this.freeLocation; }
  public void setFreeLocation(int newFreeLocation) { this.freeLocation = newFreeLocation; }

  @Override
  public Integer add(Value content) throws HeapException{
    try {
      heap.put(freeLocation, content);
      freeLocation++;
      return freeLocation - 1;
    } catch(UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException exception){
      throw new HeapException(exception.getMessage());
    }
  }

  @Override
  public Value update(Integer address, Value content) throws HeapException{
    try {
      return heap.replace(address, content);
    } catch(UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException exception){
      throw new HeapException(exception.getMessage());
    }
  }

  @Override
  public boolean search(Integer address) throws HeapException {
    try {
      return heap.containsKey(address);
    } catch(ClassCastException | NullPointerException exception){
      throw new HeapException(exception.getMessage());
    }
  }

  @Override
  public Value get(Integer address) throws HeapException{
    try {
      return heap.get(address);
    } catch(ClassCastException | NullPointerException exception){
      throw new HeapException(exception.getMessage());
    }
  }

  @Override
  public void setContent(Map<Integer, Value> newContent) {
    this.heap = newContent;
  }

  @Override
  public Map<Integer, Value> getContent() {
    return new HashMap<>(heap);
  }

  @Override
  public Set<Integer> keys() {
    return new HashSet<>(this.heap.keySet());
  }

  @Override
  public boolean isEmpty() {
    return heap.isEmpty();
  }

  @Override
  public Integer getFreeAddress() {
    return freeLocation;
  }

  @Override
  public String toString() {
    synchronized (heap){
      StringBuilder stringBuilder = new StringBuilder();
      for(int key: heap.keySet()) {
        stringBuilder.append(key).append(" -> ").append(heap.get(key)).append('\n');
      }
      return stringBuilder.toString();
    }
  }

}
