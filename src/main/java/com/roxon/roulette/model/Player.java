package com.roxon.roulette.model;

public final class Player {

  private final String name;

  public Player(String name) {
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }
}
