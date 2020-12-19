The names, initial bet amounts and initial win amounts of players are under file src/players.txt.

If a name does not exist in players.txt file, the application does not allow him to bet.

Results are printed to system out.

The names of the players are case sensitive.

If a player bets more than once in a round, each bet's result is displayed individually.

SAMPLE INPUT FILE 1
Eyup, 1.0,2.0
Erol, 5.0,7.0

SAMPLE RUN 1

Enter your bet :
Eyup ODD 1
Enter your bet :
Erol EVEN 9.99
Enter your bet :



Number : 29
Player Bet Outcome Winnings
- - -
Eyup  ODD WIN  2
Erol  EVEN LOSE  0



Player Total Win Total Bet
- - -
Eyup 3.0 3.0
Erol 5.0 16.99

-----------------------
-----------------------

SAMPLE INPUT FILE 1
Eyup, 1.0,2.0
Erol, 5.0,7.0

SAMPLE RUN 2

Enter your bet :
Eyup ODD 999.99
Enter your bet :
Erol EVEN 9999.98
Enter your bet :
Erol 1 2.3
Enter your bet :
Eyup 2 2.3
Enter your bet :
Ali 1 1.2
Player Ali not exist in the player list
Enter your bet :



Number : 30
Player Bet Outcome Winnings
- - -
Eyup  ODD LOSE  0
Erol  EVEN WIN  19999.96
Erol  1 LOSE  0
Eyup  2 LOSE  0



Player Total Win Total Bet
- - -
Eyup 1.0 1004.29
Erol 20004.96 10009.28