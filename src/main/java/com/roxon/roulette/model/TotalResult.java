package com.roxon.roulette.model;

public class TotalResult {
  private final Player player;
  private double totalWin;
  private double totalBet;

  public TotalResult(Player player, double totalWin, double totalBet) {
    this.player = player;
    this.totalWin = totalWin;
    this.totalBet = totalBet;
  }

  public Player getPlayer() {
    return player;
  }

  public double getTotalBet() {
    return totalBet;
  }

  public double getTotalWin() {
    return totalWin;
  }

  public void setTotalBet(double totalBet) {
    this.totalBet = totalBet;
  }

  public void setTotalWin(double totalWin) {
    this.totalWin = totalWin;
  }

  @Override
  public String toString() {
    return
        player.getName() + " " + totalWin + " " + totalBet ;
  }
}
