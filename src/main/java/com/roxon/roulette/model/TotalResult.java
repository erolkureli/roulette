package com.roxon.roulette.model;

import java.math.BigDecimal;

public class TotalResult {
  private final Player player;
  private BigDecimal totalWin;
  private BigDecimal totalBet;

  public TotalResult(Player player, BigDecimal totalWin, BigDecimal totalBet) {
    this.player = player;
    this.totalWin = totalWin;
    this.totalBet = totalBet;
  }

  public Player getPlayer() {
    return player;
  }

  public BigDecimal getTotalBet() {
    return totalBet;
  }

  public BigDecimal getTotalWin() {
    return totalWin;
  }

  public synchronized void setTotalBet(BigDecimal totalBet) {
    this.totalBet = totalBet;
  }

  public synchronized void setTotalWin(BigDecimal totalWin) {
    this.totalWin = totalWin;
  }

  @Override
  public String toString() {
    return
        player.getName() + " " + totalWin + " " + totalBet ;
  }
}
