

import com.roxon.roulette.game.Game;
import com.roxon.roulette.generator.NumberGenerator;

public class Roulette {

  public static void main(String[] args) {
    try {
      Game game = Game.getInstance();
      Thread thread = new Thread(game);
      thread.start();
      NumberGenerator.generateRandomNumber(game);
    } catch (Exception ex) {

    }
  }
}
