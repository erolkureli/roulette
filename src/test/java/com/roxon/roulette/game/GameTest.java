package com.roxon.roulette.game;

import static org.junit.Assert.assertTrue;

import com.roxon.roulette.model.Bet;
import com.roxon.roulette.model.Player;
import com.roxon.roulette.model.TotalResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Game.class, Scanner.class})
public class GameTest {

  static final int ODD_RANDOM_NUMBER = 1;
  static final int EVEN_RANDOM_NUMBER = 2;

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
    List<Bet> betList = (List) value;
    assertTrue(betList.size() == 1);
  }

  @Test
  public void When_Finish_Round_Called_Then_Round_and_Total_Points_Should_Be_Calculated_For_Number_Guess_Win()
      throws Exception {
    Game game = Game.getInstance();

    Player player = new Player("Erol");
    Bet bet = new Bet(player, "1", new BigDecimal(2.0));
    List<Bet> betList = Collections.synchronizedList(new ArrayList());
    betList.add(bet);

    Field fieldPlayerNames = game.getClass().getDeclaredField("betList");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, betList);

    List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());
    TotalResult totalResult = new TotalResult(player, BigDecimal.ZERO, BigDecimal.ZERO);
    totalResultList.add(totalResult);

    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    fieldTotalResultList.set(game, totalResultList);

    game.finishRound(ODD_RANDOM_NUMBER);

    Object value = fieldTotalResultList.get(game);
    List<TotalResult> newTotalResultList = (List) value;
    assertTrue(newTotalResultList.size() == 1);
    assertTrue(newTotalResultList.get(0).getTotalWin().compareTo(BigDecimal.valueOf(72.0)) == 0);
    assertTrue(newTotalResultList.get(0).getTotalBet().compareTo(BigDecimal.valueOf(2.0)) == 0);
  }

  @Test
  public void When_Finish_Round_Called_Then_Round_and_Total_Points_Should_Be_Calculated_For_Number_Guess_Lose()
      throws Exception {
    Game game = Game.getInstance();

    Player player = new Player("Erol");
    Bet bet = new Bet(player, "1", new BigDecimal(2.0));
    List<Bet> betList = Collections.synchronizedList(new ArrayList());
    betList.add(bet);

    Field fieldPlayerNames = game.getClass().getDeclaredField("betList");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, betList);

    List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());
    TotalResult totalResult = new TotalResult(player, BigDecimal.ZERO, BigDecimal.ZERO);
    totalResultList.add(totalResult);

    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    fieldTotalResultList.set(game, totalResultList);

    game.finishRound(EVEN_RANDOM_NUMBER);

    Object value = fieldTotalResultList.get(game);
    List<TotalResult> newTotalResultList = (List) value;
    assertTrue(newTotalResultList.size() == 1);
    assertTrue(newTotalResultList.get(0).getTotalWin().compareTo(BigDecimal.valueOf(0.0)) == 0);
    assertTrue(newTotalResultList.get(0).getTotalBet().compareTo(BigDecimal.valueOf(2.0)) == 0);
  }

  @Test
  public void When_Finish_Round_Called_Then_Round_and_Total_Points_Should_Be_Calculated_For_Even_Guess_Lose()
      throws Exception {
    Game game = Game.getInstance();

    Player player = new Player("Erol");
    Bet bet = new Bet(player, "EVEN", new BigDecimal(2.0));
    List<Bet> betList = Collections.synchronizedList(new ArrayList());
    betList.add(bet);

    Field fieldPlayerNames = game.getClass().getDeclaredField("betList");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, betList);

    List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());
    TotalResult totalResult = new TotalResult(player, BigDecimal.ZERO, BigDecimal.ZERO);
    totalResultList.add(totalResult);

    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    fieldTotalResultList.set(game, totalResultList);

    game.finishRound(ODD_RANDOM_NUMBER);

    Object value = fieldTotalResultList.get(game);
    List<TotalResult> newTotalResultList = (List) value;
    assertTrue(newTotalResultList.size() == 1);
    assertTrue(newTotalResultList.get(0).getTotalWin().compareTo(BigDecimal.valueOf(0.0)) == 0);
    assertTrue(newTotalResultList.get(0).getTotalBet().compareTo(BigDecimal.valueOf(2.0)) == 0);
  }

  @Test
  public void When_Finish_Round_Called_Then_Round_and_Total_Points_Should_Be_Calculated_For_Even_Guess_Win()
      throws Exception {
    Game game = Game.getInstance();

    Player player = new Player("Erol");
    Bet bet = new Bet(player, "EVEN", new BigDecimal(2.0));
    List<Bet> betList = Collections.synchronizedList(new ArrayList());
    betList.add(bet);

    Field fieldPlayerNames = game.getClass().getDeclaredField("betList");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, betList);

    List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());
    TotalResult totalResult = new TotalResult(player, BigDecimal.ZERO, BigDecimal.ZERO);
    totalResultList.add(totalResult);

    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    fieldTotalResultList.set(game, totalResultList);

    game.finishRound(EVEN_RANDOM_NUMBER);

    Object value = fieldTotalResultList.get(game);
    List<TotalResult> newTotalResultList = (List) value;
    assertTrue(newTotalResultList.size() == 1);
    assertTrue(newTotalResultList.get(0).getTotalWin().compareTo(BigDecimal.valueOf(4.0)) == 0);
    assertTrue(newTotalResultList.get(0).getTotalBet().compareTo(BigDecimal.valueOf(2.0)) == 0);
  }

  @Test
  public void When_Finish_Round_Called_Then_Round_and_Total_Points_Should_Be_Calculated_For_Odd_Guess_Lose()
      throws Exception {
    Game game = Game.getInstance();

    Player player = new Player("Erol");
    Bet bet = new Bet(player, "ODD", new BigDecimal(2.0));
    List<Bet> betList = Collections.synchronizedList(new ArrayList());
    betList.add(bet);

    Field fieldPlayerNames = game.getClass().getDeclaredField("betList");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, betList);

    List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());
    TotalResult totalResult = new TotalResult(player, BigDecimal.ZERO, BigDecimal.ZERO);
    totalResultList.add(totalResult);

    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    fieldTotalResultList.set(game, totalResultList);

    game.finishRound(EVEN_RANDOM_NUMBER);

    Object value = fieldTotalResultList.get(game);
    List<TotalResult> newTotalResultList = (List) value;
    assertTrue(newTotalResultList.size() == 1);
    assertTrue(newTotalResultList.get(0).getTotalWin().compareTo(BigDecimal.valueOf(0.0)) == 0);
    assertTrue(newTotalResultList.get(0).getTotalBet().compareTo(BigDecimal.valueOf(2.0)) == 0);
  }

  @Test
  public void When_Finish_Round_Called_Then_Round_and_Total_Points_Should_Be_Calculated_For_Odd_Guess_Win()
      throws Exception {
    Game game = Game.getInstance();

    Player player = new Player("Erol");
    Bet bet = new Bet(player, "ODD", new BigDecimal(2.0));
    List<Bet> betList = Collections.synchronizedList(new ArrayList());
    betList.add(bet);

    Field fieldPlayerNames = game.getClass().getDeclaredField("betList");
    fieldPlayerNames.setAccessible(true);
    fieldPlayerNames.set(game, betList);

    List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());
    TotalResult totalResult = new TotalResult(player, BigDecimal.ZERO, BigDecimal.ZERO);
    totalResultList.add(totalResult);

    Field fieldTotalResultList = game.getClass().getDeclaredField("totalResultList");
    fieldTotalResultList.setAccessible(true);
    fieldTotalResultList.set(game, totalResultList);

    game.finishRound(ODD_RANDOM_NUMBER);

    Object value = fieldTotalResultList.get(game);
    List<TotalResult> newTotalResultList = (List) value;
    assertTrue(newTotalResultList.size() == 1);
    assertTrue(newTotalResultList.get(0).getTotalWin().compareTo(BigDecimal.valueOf(4.0)) == 0);
    assertTrue(newTotalResultList.get(0).getTotalBet().compareTo(BigDecimal.valueOf(2.0)) == 0);
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
    List<TotalResult> totalResultList = (List) valueTotalResultList;
    assertTrue(totalResultList.size() == 2);

    Field fieldPlayerNames = game.getClass().getDeclaredField("playerNames");
    fieldPlayerNames.setAccessible(true);
    Object valuePlayerNames = fieldPlayerNames.get(game);
    List<String> playerNames = (List) valuePlayerNames;
    assertTrue(playerNames.size() == 2);
  }

}
