package model.type;

import model.value.ReferenceValue;
import model.value.Value;

public class ReferenceType implements Type{
  private final Type inner;

  public ReferenceType(Type inner) {
    this.inner = inner;
  }

  public Type getInner() { return this.inner; }

  @Override
  public boolean equals(Object another){
    return another instanceof ReferenceType;
  }

  @Override
  public Value defaultValue() {
    return new ReferenceValue(0, inner);
  }

  @Override
  public String toString(){
    return "reference(" + inner.toString() + ")";
  }


}
