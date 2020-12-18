package com.roxon.roulette.game;

import static com.roxon.roulette.model.BetResult.LOSE;
import static com.roxon.roulette.model.BetResult.WIN;
import static com.roxon.roulette.model.Type.EVEN;
import static com.roxon.roulette.model.Type.ODD;
import static com.roxon.roulette.model.Type.OTHER;

import com.roxon.roulette.model.Bet;
import com.roxon.roulette.model.BetResult;
import com.roxon.roulette.model.Player;
import com.roxon.roulette.model.Result;
import com.roxon.roulette.model.TotalResult;
import com.roxon.roulette.model.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public final class Game implements Runnable {

  private static Game instance;

  private Game() {
  }

  public synchronized static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }

  List<Bet> betList = Collections.synchronizedList(new ArrayList());
  List<Result> resultList = Collections.synchronizedList(new ArrayList());
  List<String> playerNames = Collections.synchronizedList(new ArrayList());
  List<TotalResult> totalResultList = Collections.synchronizedList(new ArrayList());

  private static final long GAME_PERIOD = (long) 0.1;

  @Override
  public void run() {
    try {
      initializePlayers();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    Scanner myObj = new Scanner(System.in);
    System.out.println("Enter your bet : ");

    while (true) {
      String round = null;
      try {
        round = myObj.nextLine();
        String[] roundInput = round.split("\\s+");
        String playerName = roundInput[0].trim();
        String guess = roundInput[1].trim();
        Double betMoney = Double.valueOf(roundInput[2].trim());

        if(playerNames.contains(playerName)) {
          Player player = new Player(playerName);
          Bet bet = new Bet(player, guess, betMoney);
          betList.add(bet);
        }else {
          System.out.println("Player " + playerName + " not exist in the player list ");
        }
        TimeUnit.SECONDS.sleep(GAME_PERIOD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void finishRound(int randomNumber) {
    boolean isEven = randomNumber % 2 == 0;

    betList.forEach(bet -> {
      if (bet.getBettingNumber().equals(String.valueOf(EVEN))) {
        if (isEven) {
          evaluate(WIN, EVEN, bet);
        } else {
          evaluate(LOSE, EVEN, bet);
        }
      } else if (bet.getBettingNumber().equals(String.valueOf(ODD))) {
        if (!isEven) {
          evaluate(WIN, ODD, bet);
        } else {
          evaluate(LOSE, ODD, bet);
        }
      } else {
        if (String.valueOf(randomNumber).equals(bet.getBettingNumber())) {
          evaluate(WIN, OTHER, bet);
        } else {
          evaluate(LOSE, OTHER, bet);
        }
      }
    });

    System.out.println("Number : " + randomNumber);
    System.out.println("Player Bet Outcome Winnings");
    System.out.println("- - -");
    resultList.forEach(result -> {
      System.out.println(result);
    });


    betList.clear();
    resultList.clear();

    System.out.println("");
    System.out.println("");
    System.out.println("");

    System.out.println("Player Total Win Total Bet");
    System.out.println("- - -");
    totalResultList.forEach(result ->{
      System.out.println(result);
    });

    System.out.println("");
    System.out.println("");
    System.out.println("");
  }

  private void evaluate(BetResult betResult, Type type, Bet bet) {
    Result result;
    double winMoney = 0.0;
    if (type.equals(ODD) || type.equals(EVEN)) {
      if (betResult.equals(WIN)) {
        winMoney = 2 * bet.getMoneyBet();
        result = new Result(bet.getPlayer(), bet.getBettingNumber(), WIN, winMoney);
      } else {
        result = new Result(bet.getPlayer(), bet.getBettingNumber(), LOSE, 0.0);
      }
    } else {
      if (betResult.equals(WIN)) {
        winMoney = 36 * bet.getMoneyBet();
        result = new Result(bet.getPlayer(), bet.getBettingNumber(), WIN, winMoney);
      } else {
        result = new Result(bet.getPlayer(), bet.getBettingNumber(), LOSE, 0.0);
      }
    }
    resultList.add(result);
    Optional<TotalResult> totalResultOpt = totalResultList.stream().filter(r->r.getPlayer().getName().equals(bet.getPlayer().getName())).findFirst();
    if(totalResultOpt.isPresent()){
      TotalResult totalResult = totalResultOpt.get();
      totalResult.setTotalBet(totalResult.getTotalBet() + bet.getMoneyBet());
      totalResult.setTotalWin(totalResult.getTotalWin() + winMoney);
    }
  }


  private void initializePlayers() throws FileNotFoundException {
    File players = new File("src/players.txt");
    Scanner reader = new Scanner(players);
    while (reader.hasNextLine()) {
      String playerInput = reader.nextLine();
      String[] totalResults = playerInput.split(",");
      String playerName = totalResults[0].trim();
      double totalWin = Double.valueOf(totalResults[1].trim());
      double totalBet = Double.valueOf(totalResults[2].trim());

      TotalResult totalResult = new TotalResult(new Player(playerName), totalWin, totalBet);
      totalResultList.add(totalResult);
      System.out.println(playerName);
      playerNames.add(playerName);
    }
    reader.close();
  }


}
