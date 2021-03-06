/*import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

public class GameContoller {



public static String[] pokerkarten = {"AS","AH","AD","AC","KS","KH","KD","KC","QS","QH","QD","QC","JS","JH","JD","JC","TS","TH","TD","TC","9S","9H","9D","9C","8S","8H","8D","8C","7S","7H","7D","7C","6S","6H","6D","6C","5S","5H","5D","5C","4S","4H","4D","4C","3S","3H","3D","3C","2S","2H","2D","2C"};
public static Deck deck = new Deck();
public static PokerKI pki = new PokerKI();
public static MonteCarloSimulation mcs = new MonteCarloSimulation();
int [] cardDeck;
int button = 3;
int playersTurn = 3;

public boolean activeHand = false;
public boolean activeGame = false;

public void changeActiveHand(boolean b) {
	activeHand = b;
	PokerKI.setCCButton();
}
public void changeActiveGame(boolean b) {
	activeGame = b;
	PokerKI.setCCButton();
}

Player player0 = new Player();
Player player1 = new Player();
Player player2 = new Player();
int blind;
int highestBet;
int gamestate = 0;
int pot = 0;

public void startNewGame(){
	pot = 0;
	button = 3;
	player0 = new Player();
	player1 = new Player();
	player2 = new Player();
	changeActiveGame(true);
	changeActiveHand(true);
	startHand();
	
}

public void startHand(){
	changeActiveHand(true);
	//button platzieren
	gamestate = 0;
	if (button>2){
		Random randomGenerator = new Random();
		button = randomGenerator.nextInt(3);
		blind = 20;

		
	} else  {
		button = nextPlayer(button);
	}
	//blinds setzen
	if (player0.balance > 0 & player1.balance > 0 & player2.balance > 0){
		blind(getPlayer(nextPlayer(button)),blind/2);
		blind(getPlayer(nextPlayer(nextPlayer(button))),blind);
	}
	else {
		blind(getPlayer(button),blind/2);
		blind(getPlayer(nextPlayer(button)),blind);
	}
	player0.active = true;
	player1.active = true;
	player2.active = true;
			
	highestBet = blind;
	playersTurn = button;
	karten = deck.shuffleDeck();
	try {
		   writeCards(karten);
	    } catch (Exception e) {
	        System.out.println("fehler 83");
	    }
	
	continueBetting(playersTurn);

	
}
public void nextGameState(){
	pot = pot + player0.bet + player1.bet + player2.bet;
	player0.bet = 0;
	player1.bet = 0;
	player2.bet = 0;
	highestBet = 0;
	System.out.println("");
	if (gamestate < 5){
		gamestate ++;
		if(gamestate == 4) {changeActiveHand(false);}
		
		System.out.println("gamestate changed to: " + gamestate);
		continueBetting(nextPlayer(button));
	}
	
	
}

public void continueBetting(int acting) {
	PokerKI.setButtons();
	if (acting == 0){
		if (player0.active == false){
			//letzer am zug
			if(player1.bet == player2.bet | player1.allin | player2.allin){
				return;
			}
			
			
			continueBetting(nextPlayer(acting));
		}
		return;
	}
	
	

	if (getPlayer(acting).allin == false){
		//wenn big blind
		if (gamestate == 0 & getPlayer(acting).bet == blind & button == nextPlayer(acting)){
			calculateMove(acting);
			if (highestBet > blind){
				continueBetting(nextPlayer(acting));
				return;
			}
			return;
		}
		//wenn höher gebettet
		if (getPlayer(acting).bet < highestBet){
			calculateMove(acting);
			continueBetting(nextPlayer(acting));
			return;
		}
		//wenn noch nichts gebettet
		if (highestBet == 0){
			calculateMove(acting);
			if (acting == button & highestBet == 0){
				return;
			}
			continueBetting(nextPlayer(acting));
		}
	}
}
private void calculateMove(int acting) {
	
	if(gamestate == 0){
		calculatePreflopMove(acting);
	}
	
	
	
	Random randomGenerator = new Random();
	int randomInt = randomGenerator.nextInt(10);
	if (randomInt < 0){
		fold(getPlayer(acting));
		return;
	} else if(randomInt < 5){
		raise(getPlayer(acting), blind);
		return;
	} 
	call(getPlayer(acting));
	//to do
}
private void calculatePreflopMove(int acting) {
	
	
	
	
	
	
}
public int nextPlayer(int x){
	switch(x){
	case 0: if(player1.balance == 0) return 2;
	else return 1;
	case 1: if(player2.balance == 0) return 0;
	else return 2;
	case 2: if(player0.balance == 0) return 1;
	else return 0;
	}
	return -1;
}
public int prevPlayer(int x){
	switch(x){
	case 0: if(player2.balance == 0) return 1;
	else return 2;
	case 1: if(player0.balance == 0) return 2;
	else return 0;
	case 2: if(player1.balance == 0) return 0;
	else return 1;
	}
	return -1;
}
public Player getPlayer(int x){
	switch(x){
	case 0: return player0;
	case 1: return player1;
	}
	return player2;
}
public void blind(Player player, int amount){
	if (player.balance + player.bet> amount){
		player.balance = player.balance - (amount - player.bet);
		player.bet = amount;
		highestBet = player.bet;
	} else {
		player.bet = player.balance + player.bet;
		player.balance = 0;
		player.allin = true;
		highestBet = player.bet;
	}
}
public void raise(Player player, int amount){
	if(player == player0){
		System.out.println("0    " + gamestate + "    raise");
	}
	if(player == player1){
		System.out.println("1    " + gamestate + "    raise");
	}
	if(player == player2){
		System.out.println("2    " + gamestate + "    raise");
	}
	
	
	
	
	if (player.balance + player.bet> amount){
		player.balance = player.balance - (amount - player.bet);
		player.bet = amount;
		highestBet = player.bet;
	} else {
		player.bet = player.balance + player.bet;
		player.balance = 0;
		player.allin = true;
		highestBet = player.bet;
	}

	
	
	
	
	try {
		   writeAction(player, "raise", amount);
	    } catch (Exception e) {
	        System.out.println("fehler 252");
	    }
}
public void call(Player player){
	if(player == player0){
		System.out.println("0    " + gamestate + "    call");
	}
	if(player == player1){
		System.out.println("1    " + gamestate + "    call");
	}
	if(player == player2){
		System.out.println("2    " + gamestate + "    call");
	}

	if (player.balance + player.bet > highestBet){
		player.balance = player.balance - (highestBet - player.bet);
		player.bet = highestBet;
	} else {
		player.bet = player.balance + player.bet;
		player.balance = 0;
		player.allin = true;
	}
	   try {
		   writeAction(player, "call", player.bet);
		   writeWinProbability(karten);
		   
	    } catch (Exception e) {
	        System.out.println(e + " 280");
	    }
	
	
}
public void fold(Player player){
	if(player == player0){
		System.out.println("0    " + gamestate + "    fold");
	}
	if(player == player1){
		System.out.println("1    " + gamestate + "    fold");
	}
	if(player == player2){
		System.out.println("2    " + gamestate + "    fold");
	}
	
	
	
	player.active = false;
	 try {
		   writeAction(player, "fold", 0);
	    } catch (Exception e) {
	        System.out.println("fehler 302");
	    }
}

//returnt index des gewinnenden spielers
public int[] evaluate(){
	return evaluate(karten);
}
public int[] evaluate(int [] cards){
	int [] cards0 = {cards[0],cards[1],cards[2],cards[3],cards[4],cards[5],cards[6]};
	int [] cards1 = {cards[0],cards[1],cards[2],cards[3],cards[4],cards[7],cards[8]};
	int [] cards2 = {cards[0],cards[1],cards[2],cards[3],cards[4],cards[9],cards[10]};
	/*int a = 0;
	int b = 0;
	int c = 0;
	
	int[] array = DetermineWinner.compareThree(cards0, cards1, cards2);
	if(!player0.active) array[0] = -1;
	if(!player1.active) array[1] = -1;
	if(!player2.active) array[2] = -1;
	
	return array;

	
	/*if(player0.active & player1.active){
		int x = DetermineWinner.compareTwo(cards0, cards1);
		System.out.println("0 " + x + " 1");
		a = a + x;
		b = b - x;
	}
	if(player1.active & player2.active){
		int y = DetermineWinner.compareTwo(cards1, cards2);
		System.out.println("1 " + y + " 2");
		b = b + y;
		c = c - y;
	}
	if(player2.active & player0.active){
		int z = DetermineWinner.compareTwo(cards2, cards0);
		System.out.println("2 " + z + " 0");
		c = c + z;
		a = a - z;
	}
	if (a >= b & a >= c){
		return 0;
	}
	if (b >= a & b >= c){
		return 1;
	}
	return 2;
	//todo split pot
	
}

public int max(int[] array) {
	int max = 0;
	for(int i = 0; i < array.length; i++) {
		if(array[i] > max) max = array[i];
	}
	return max;
}

public void writeAction(Player player, String action, int amount)throws IOException{
	Writer output;
	output = new BufferedWriter(new FileWriter("ausgabe", true));
	if (player == player0){
		output.append("player0: ");
	}
	if (player == player1){
		output.append("player1: ");
	}
	if (player == player2){
		output.append("player2: ");
	}
	output.append(action + " " + amount);
	output.write(System.lineSeparator());
	
	output.close();
}
public void writeCards(int[] cards)throws IOException{
	Writer output;
	output = new BufferedWriter(new FileWriter("ausgabe", true));
	String cardsString = " " + cards[0] +" " + cards[1] +" " + cards[2] +" " + cards[3] +" " + cards[4] +" " + cards[5] +" " + cards[6] +" " + cards[7] +" " + cards[8] +" " + cards[9] +" " + cards[10] ;
	output.append(cardsString);
	output.write(System.lineSeparator());
	
	output.close();
}


	public void writeWinProbability(int[] cards)throws IOException{
		if (gamestate == 1) {
			Writer output;
			output = new BufferedWriter(new FileWriter("ausgabe", true));
			String prob = Integer.toString(mcs.winProbabilityOnFlop(cards, 0, 1, 2));
			System.out.println(prob);
			output.append(prob);
			output.write(System.lineSeparator());
			output.close();
		}
		if (gamestate == 2) {
			Writer output;
			output = new BufferedWriter(new FileWriter("ausgabe", true));
			String prob = Integer.toString(mcs.winProbabilityOnTurn(cards, 0, 1, 2));
			System.out.println(prob);
			output.append(prob);
			output.write(System.lineSeparator());
			output.close();
		}
}











}*/
