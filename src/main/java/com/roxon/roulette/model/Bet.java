package com.roxon.roulette.model;

import java.math.BigDecimal;

public final class Bet {

  private final Player player;
  private final String bettingNumber;
  private final BigDecimal moneyBet;

  public Bet(Player player, String bettingNumber, BigDecimal moneyBet) {
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

  public BigDecimal getMoneyBet() {
    return moneyBet;
  }

  public Player getPlayer() {
    return player;
  }

  public String getBettingNumber() {
    return bettingNumber;
  }
}
