package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value {
  private final int value;
  public IntValue(int v){
    value = v;
  }

  public int getValue(){
    return value;
  }

  @Override
  public String toString(){
    return "" + value;
  }

  @Override
  public Type getType() {
    return new IntType();
  }
}
