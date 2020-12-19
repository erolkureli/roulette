package com.roxon.roulette.display;

import com.roxon.roulette.model.Result;
import com.roxon.roulette.model.TotalResult;
import java.util.List;

public class Display {

  public static void displaySpaces() {
    System.out.println("");
    System.out.println("");
    System.out.println("");
  }

  public static void displayTotalInfo(List<TotalResult> totalResultList) {
    System.out.println("Player Total Win Total Bet");
    System.out.println("- - -");
    totalResultList.forEach(result ->{
      System.out.println(result);
    });
  }

  public static void displayRoundInfo(int randomNumber, List<Result> resultList) {
    System.out.println("Number : " + randomNumber);
    System.out.println("Player Bet Outcome Winnings");
    System.out.println("- - -");
    resultList.forEach(result -> {
      System.out.println(result);
    });
  }
}
