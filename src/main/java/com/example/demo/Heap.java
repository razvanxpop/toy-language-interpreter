package com.example.demo;

import model.value.Value;

public class Heap {
  private final int address;
  private final Value value;

  public Heap(int address, Value value) {
    this.address = address;
    this.value = value;
  }

  public int getAddress() {
    return address;
  }

  public Value getValue() {
    return value;
  }
}
