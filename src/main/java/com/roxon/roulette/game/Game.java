package com.roxon.roulette.game;

import static com.roxon.roulette.model.BetResult.LOSE;
import static com.roxon.roulette.model.BetResult.WIN;
import static com.roxon.roulette.model.Type.EVEN;
import static com.roxon.roulette.model.Type.ODD;
import static com.roxon.roulette.model.Type.OTHER;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

import com.roxon.roulette.display.Display;
import com.roxon.roulette.model.Bet;
import com.roxon.roulette.model.BetResult;
import com.roxon.roulette.model.Player;
import com.roxon.roulette.model.Result;
import com.roxon.roulette.model.TotalResult;
import com.roxon.roulette.model.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class Game implements Runnable {

  static Logger logger = Logger.getLogger(Game.class.getName());

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
  private static final String PLAYERS_FILE = "src/players.txt";

  @Override
  public void run() {
    try {
      initializePlayers();
    } catch (FileNotFoundException ex) {
      logger.log(SEVERE, ex.getMessage());
      throw new RuntimeException(ex);
    }

    Scanner scanner = new Scanner(System.in);
    while (true) {
      saveNewBet(scanner);
    }
  }

  private void saveNewBet(Scanner scanner) {
    String play;
    try {
      System.out.println("Enter your bet : ");
      play = scanner.nextLine();
      String[] singlePlay = play.split("\\s+");
      String playerName = singlePlay[0].trim();
      String guess = singlePlay[1].trim();
      BigDecimal betMoney = new BigDecimal(singlePlay[2].trim());

      if(playerNames.contains(playerName)) {
        Player player = new Player(playerName);
        Bet bet = new Bet(player, guess, betMoney);
        betList.add(bet);
      }else {
        System.out.println("Player " + playerName + " not exist in the player list ");
      }
      TimeUnit.SECONDS.sleep(GAME_PERIOD);
    } catch (Exception ex) {
      logger.log(SEVERE, ex.getMessage());
    }
  }

  public void finishRound(int randomNumber) {
    calculateAllRoundPoints(randomNumber);

    Display.displayRoundInfo(randomNumber, resultList);

    clearRoundInfo();

    Display.displaySpaces();

    Display.displayTotalInfo(totalResultList);

    Display.displaySpaces();
  }

  private void calculateAllRoundPoints(int randomNumber) {
    boolean isEven = randomNumber % 2 == 0;

    betList.forEach(bet -> {
      if (bet.getBettingNumber().equals(String.valueOf(EVEN))) {
        if (isEven) {
          calculateRoundPoints(WIN, EVEN, bet);
        } else {
          calculateRoundPoints(LOSE, EVEN, bet);
        }
      } else if (bet.getBettingNumber().equals(String.valueOf(ODD))) {
        if (!isEven) {
          calculateRoundPoints(WIN, ODD, bet);
        } else {
          calculateRoundPoints(LOSE, ODD, bet);
        }
      } else {
        if (String.valueOf(randomNumber).equals(bet.getBettingNumber())) {
          calculateRoundPoints(WIN, OTHER, bet);
        } else {
          calculateRoundPoints(LOSE, OTHER, bet);
        }
      }
    });
  }

  private void clearRoundInfo() {
    betList.clear();
    resultList.clear();
  }

  private void calculateRoundPoints(BetResult betResult, Type type, Bet bet) {
    Result result;
    BigDecimal winMoney = BigDecimal.ZERO;
    if (type.equals(ODD) || type.equals(EVEN)) {
      if (betResult.equals(WIN)) {
        winMoney = bet.getMoneyBet().multiply(new BigDecimal(2.0));
        result = new Result(bet.getPlayer(), bet.getBettingNumber(), WIN, winMoney);
      } else {
        result = new Result(bet.getPlayer(), bet.getBettingNumber(), LOSE, BigDecimal.ZERO);
      }
    } else {
      if (betResult.equals(WIN)) {
        winMoney = bet.getMoneyBet().multiply(new BigDecimal(36.0));
        result = new Result(bet.getPlayer(), bet.getBettingNumber(), WIN, winMoney);
      } else {
        result = new Result(bet.getPlayer(), bet.getBettingNumber(), LOSE, BigDecimal.ZERO);
      }
    }
    resultList.add(result);
    Optional<TotalResult> totalResultOpt = totalResultList.stream().filter(r->r.getPlayer().getName().equals(bet.getPlayer().getName())).findFirst();
    if(totalResultOpt.isPresent()){
      TotalResult totalResult = totalResultOpt.get();

      BigDecimal totalBet = totalResult.getTotalBet().add(bet.getMoneyBet());
      BigDecimal totalWin = totalResult.getTotalWin().add(winMoney);

      totalResult.setTotalBet(totalBet);
      totalResult.setTotalWin(totalWin);
    }
  }


  private void initializePlayers() throws FileNotFoundException {
    File players = new File(PLAYERS_FILE);
    Scanner reader = new Scanner(players);
    while (reader.hasNextLine()) {
      String playerInput = reader.nextLine();
      String[] totalResults = playerInput.split(",");
      String playerName = totalResults[0].trim();

      BigDecimal totalWin = BigDecimal.ZERO;
      BigDecimal totalBet = BigDecimal.ZERO;

      try {
        totalWin = new BigDecimal(totalResults[1].trim());
        totalBet = new BigDecimal(totalResults[2].trim());
      }catch (Exception ex){
        logger.log(INFO, "Total win and total bet are initialized as 0 for " + playerName);
      }

      TotalResult totalResult = new TotalResult(new Player(playerName), totalWin, totalBet);
      totalResultList.add(totalResult);
      playerNames.add(playerName);
    }
    reader.close();
  }


}
