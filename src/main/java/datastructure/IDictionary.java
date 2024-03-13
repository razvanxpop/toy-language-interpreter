package datastructure;

import exceptions.DictionaryException;

import java.util.Collection;
import java.util.Set;

public interface IDictionary<T, U> {
  void put(T id, U value);
  boolean remove(T id, U value) throws DictionaryException;
  void replace(T id, U value) throws DictionaryException;
  boolean isDefined(T id);
  U get(T id) throws DictionaryException;

  Set<T> keys();
  Collection<U> values();
  boolean isEmpty();

  IDictionary<T, U> deepCopy();
}
