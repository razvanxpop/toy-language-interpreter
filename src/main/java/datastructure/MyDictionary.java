package datastructure;

import exceptions.DictionaryException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MyDictionary<T, U> implements IDictionary<T, U> {
  private final HashMap<T, U> dictionary = new HashMap<>();

  @Override
  public void put(T id, U value) {
    dictionary.put(id, value);
  }

  @Override
  public boolean remove(T id, U value) throws DictionaryException {
    if(!dictionary.isEmpty())
      return dictionary.remove(id, value);
    else throw new DictionaryException("Trying to remove an element of the dictionary when is empty!");
  }

  @Override
  public void replace(T id, U value) throws DictionaryException{
    if(!isDefined(id))
      throw new DictionaryException(id + " is not defined!");
    dictionary.replace(id, value);
  }

  @Override
  public boolean isDefined(T id) {
    return dictionary.containsKey(id);
  }

  @Override
  public U get(T id) throws DictionaryException {
    U value = dictionary.get(id);
    if(value == null)
      throw new DictionaryException("The variable " + id + " is undefined!");
    return value;
  }

  @Override
  public Set<T> keys() {
    return new HashSet<>(this.dictionary.keySet());
  }

  @Override
  public Collection<U> values() {
    return dictionary.values();
  }

  @Override
  public boolean isEmpty() {
    return dictionary.isEmpty();
  }

  @Override
  public IDictionary<T, U> deepCopy() {
    IDictionary<T, U> copy = new MyDictionary<>();
    for(T key: dictionary.keySet())
      copy.put(key, dictionary.get(key));

    return copy;
  }

  @Override
  public String toString() {
      synchronized (dictionary){
        StringBuilder stringBuilder = new StringBuilder();
        for(T key: dictionary.keySet()) {
          stringBuilder.append(key).append(" -> ").append(dictionary.get(key)).append('\n');
        }
        return stringBuilder.toString();
      }
  }
}
