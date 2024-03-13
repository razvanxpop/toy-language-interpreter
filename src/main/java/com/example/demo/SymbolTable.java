package com.example.demo;

import model.value.Value;

public class SymbolTable {
  private final Value value;
  private final String id;

  public SymbolTable(String id, Value value) {
    this.value = value;
    this.id = id;
  }

  public Value getValue() {
    return value;
  }

  public String getId() {
    return id;
  }
}
