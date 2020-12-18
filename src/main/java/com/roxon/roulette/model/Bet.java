package com.roxon.roulette.model;

public final class Bet {

  private final Player player;
  private final String bettingNumber;
  private final double moneyBet;

  public Bet(Player player, String bettingNumber, double moneyBet) {
    this.player = player;
    this.bettingNumber = bettingNumber;
    this.moneyBet = moneyBet;
  }

  @Override
  public String toString() {
    return "Bet{" +
        "player=" + player +
        ", bettingNumber='" + bettingNumber + '\'' +
        ", moneyBet=" + moneyBet +
        '}';
  }

  public double getMoneyBet() {
    return moneyBet;
  }

  public Player getPlayer() {
    return player;
  }

  public String getBettingNumber() {
    return bettingNumber;
  }
}
