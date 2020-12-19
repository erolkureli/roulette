package com.roxon.roulette.model;

import java.math.BigDecimal;

public class Result {
  private final Player player;
  private final String bettingNumber;
  private final BetResult betResult;
  private final BigDecimal moneyWin;

  public Result(Player player, String bettingNumber, BetResult betResult, BigDecimal moneyWin) {
    this.player = player;
    this.bettingNumber = bettingNumber;
    this.betResult = betResult;
    this.moneyWin = moneyWin;
  }

  @Override
  public String toString() {
    return
         player.getName() + "  "+ bettingNumber + " "+ betResult + "  " + moneyWin;
  }
}
