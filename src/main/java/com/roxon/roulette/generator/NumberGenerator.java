package com.roxon.roulette.generator;

import com.roxon.roulette.game.Game;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class NumberGenerator {

  private static final int MIN_VALUE = 0;
  private static final int MAX_VALUE = 36;
  private static final int NUMBER_GENERATION_PERIOD = 30;
  private static AtomicInteger generatedNumber = new AtomicInteger(0);
  private static Random random = new Random();

  public synchronized static void generateRandomNumber(Game game) {
    while (true) {
      try {
        TimeUnit.SECONDS.sleep(NUMBER_GENERATION_PERIOD);
        generatedNumber.set(random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE);
        game.finishRound(generatedNumber.get());
      } catch (Exception ex) {

      }
    }
  }


}
