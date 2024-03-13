package model.value;

import model.type.ReferenceType;
import model.type.Type;

public class ReferenceValue implements Value {
  private final int address;
  private final Type locationType;

  public ReferenceValue(int address, Type locationType) {
    this.address = address;
    this.locationType = locationType;
  }

  public int getAddress() { return this.address; }

  public Type getLocationType() { return this.locationType; }

  @Override
  public Type getType() {
    return new ReferenceType(this.locationType);
  }

  @Override
  public String toString() {
    return address + "->" + locationType.toString();
  }
}
