package com.roxon.roulette.main;

import static java.util.logging.Level.SEVERE;

import com.roxon.roulette.game.Game;
import com.roxon.roulette.generator.NumberGenerator;
import java.util.logging.Logger;

public class Roulette {

  static Logger logger = Logger.getLogger(Roulette.class.getName());

  public static void main(String[] args) {
    try {
      Game game = Game.getInstance();
      Thread thread = new Thread(game);
      thread.start();
      NumberGenerator.handleRoundPeriod(game);
    } catch (Exception ex) {
      logger.log(SEVERE, ex.getMessage());
    }
  }
}
