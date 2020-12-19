package com.roxon.roulette.generator;

import com.roxon.roulette.game.Game;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class NumberGenerator {

  static Logger logger = Logger.getLogger(NumberGenerator.class.getName());

  private static final int MIN_VALUE = 0;
  private static final int MAX_VALUE = 36;
  private static final int NUMBER_GENERATION_PERIOD = 30;
  private static AtomicInteger generatedNumber = new AtomicInteger(0);
  private static Random random = new Random();

  public static void handleRoundPeriod(Game game) {
    while (true) {
      try {
        TimeUnit.SECONDS.sleep(NUMBER_GENERATION_PERIOD);
        finishRound(game);
      } catch (Exception ex) {
        logger.log(Level.WARNING, "Random number could not be generated : " + ex.getMessage());
      }
    }
  }

  private static void finishRound(Game game) {
    generatedNumber.set(random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE);
    game.finishRound(generatedNumber.get());
  }
}
