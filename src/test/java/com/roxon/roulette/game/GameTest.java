package com.roxon.roulette.game;

import static org.junit.Assert.assertTrue;

import com.roxon.roulette.model.Bet;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Game.class, Scanner.class})
public class GameTest {

  @Test
  public void When_Save_New_Bet_Called_Then_Should_Record_New_Bet_To_List() throws Exception {
    Game game = Game.getInstance();

    String input = "Erol 1 2.0";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    Scanner scanner = new Scanner(System.in);


    Method saveNewBet = Game.class.getDeclaredMethod(
        "saveNewBet",
        Scanner.class
    );

    List<String> playerNames = Collections.synchronizedList(new ArrayList());
    playerNames.add("Erol");

    Field fieldPlayerNames = game.getClass().getDeclaredField("playerNames");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, playerNames);

    saveNewBet.setAccessible(true);
    saveNewBet.invoke(game, scanner);

    System.setIn(stdin);

    Field fieldBetList = game.getClass().getDeclaredField("betList");
    fieldBetList.setAccessible(true);
    Object value = fieldBetList.get(game);
    List<Bet> betList = (List)value;
    assertTrue(betList.size() == 1);
  }
}
