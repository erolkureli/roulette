package com.roxon.roulette.game;

import static org.junit.Assert.assertTrue;

import com.roxon.roulette.model.Bet;
import com.roxon.roulette.model.Player;
import com.roxon.roulette.model.TotalResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Game.class, Scanner.class})
public class GameTest {

  @Before
  public void setup() throws Exception {
    Game game = Game.getInstance();

    List<String> playerNames = Collections.synchronizedList(new ArrayList());
    Field fieldPlayerNames = game.getClass().getDeclaredField("playerNames");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, playerNames);

    List<Bet> betList = Collections.synchronizedList(new ArrayList());
    Field fieldBetList = game.getClass().getDeclaredField("betList");
    fieldBetList.setAccessible(true);
    fieldBetList.set(game, betList);

    List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());
    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    fieldTotalResultList.set(game, totalResultList);
  }

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

  @Test
  public void When_Finish_Round_Called_Then_Round_and_Total_Points_Should_Be_Calculated() throws Exception {
    Game game = Game.getInstance();

    Player player = new Player("Erol");
    Bet bet = new Bet(player, "1", 2.0);
    List<Bet> betList = Collections.synchronizedList(new ArrayList());
    betList.add(bet);

    Field fieldPlayerNames = game.getClass().getDeclaredField("betList");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, betList);

    List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());
    TotalResult totalResult = new TotalResult(player, 0.0, 0.0);
    totalResultList.add(totalResult);

    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    fieldTotalResultList.set(game, totalResultList);

    game.finishRound(1);

    Object value = fieldTotalResultList.get(game);
    List<TotalResult> newTotalResultList = (List)value;
    assertTrue(newTotalResultList.size() == 1);
  }

  @Test
  public void When_InitializePlayers_Called_Then_Should_Add_Player_Names_And_Total_Result_To_List() throws Exception {
    Game game = Game.getInstance();

    Method initializePlayers = Game.class.getDeclaredMethod(
        "initializePlayers",
        null
    );

    initializePlayers.setAccessible(true);
    initializePlayers.invoke(game, null);

    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    Object valueTotalResultList = fieldTotalResultList.get(game);
    List<TotalResult> totalResultList = (List)valueTotalResultList;
    assertTrue(totalResultList.size() == 2);

    Field fieldPlayerNames = game.getClass().getDeclaredField("playerNames");
    fieldPlayerNames.setAccessible(true);
    Object valuePlayerNames = fieldPlayerNames.get(game);
    List<String> playerNames = (List)valuePlayerNames;
    assertTrue(playerNames.size() == 2);
  }

}
