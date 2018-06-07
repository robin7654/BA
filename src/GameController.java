import java.awt.EventQueue;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class GameController {
	
	//public static DeckHelper deck = new DeckHelper();
	public static PokerKI pki = new PokerKI();
	public static MonteCarloSimulation mcs = new MonteCarloSimulation();
	public static Strategies str = new Strategies();
	
	public static CollectedData cD = new CollectedData();
	public static FlopCollectedData cDF = new FlopCollectedData();
	public static TurnCollectedData cDT = new TurnCollectedData();
	public static RiverCollectedData cDR = new RiverCollectedData();
	public static DSWriter dsW = new DSWriter();
	
	static int[] cardDeck = new int[11];
	
	public static boolean activeHand = false;
	public static boolean activeGame = false;
	
	
	static Player[] player = new Player[3];
	
	static int button = -1;
	static int bigBlindPosition = -1;
	static int activePlayer = -1;
	static int blind;
	static int startingBlind = 20;
	static int highestBet;
	static int gameState = -1;
	static int mainPot = 0;
	static int sidePot = 0;
	static int gamesTillLvChange = 9;
	static int gamesTillLvChangeCount = 0;
	static int w1 = 0;
	static int w2 = 0;
	static int w3 = 0;
	
	
	public static void startNewGame() {
		player[0] = new Player(false, true, "Player", 0);
		player[1] = new Player(true, false, "Bot1", 1);
		player[2] = new Player(true, false, "Bot2", 2);
		
		setGameState(5);
		
		//setActiveHand(true);
		setActiveGame(true);
		
		setBigBlindPosition(-1);
		
		resetBlinds();
		
		//startNewHand();
		
		/*for(int i = 0; i < 100; i++) {
			if(activeGame) {
				startNewHand();
				System.out.println(i);
			}
		}
		System.out.println("finish");
		System.out.println(blind);*/
	}
	public static void startNewHand() {		
		setActiveHand(true);
		setAllPlayerToNewHand();
		setGameState(0);
		moveBigBlindToNextPosition();
		checkBlindChange();
		setMainPot(0);
		setSidePot(0);
		setHighestBet(blind);
		cardDeck = DeckHelper.createShuffledDeck();
		
		setBlinds();
		//changeActivePlayer();
		setActivePlayer(button);
		
		pki.openCards(gameState);
		pki.updateAll();
		
		//System.out.println("Button: " + button);
		//System.out.println("BB: " + bigBlindPosition);
		
		try {
			writeNewHandToTxt();
		} catch (IOException e) {e.printStackTrace();}
		
		
		while(activeHand && player[activePlayer].bot) getNextMove();
		if(player[activePlayer].acted == true) {
			changeGameState();
		}
		
		//if(!player[0].bot) player[0].saveSituation();
		
		//System.out.println("Fin " + player[0].balance +" "+ player[1].balance +" "+ player[2].balance);
		//System.out.println("Hand finished\n");
	}
	
	public static void playX(int x) {
		
		for(int i = 0; i < x; i++) {	
			
			startNewGame();
			
			
			player[0].bot = true;
			player[0].rand = true;
			
			//startNewGame();
			
			while(isActiveGame()) {
				startNewHand();
			}
			System.out.println("G:" + i);
			if(player[0].balance == 1500) w1++;
			if(player[1].balance == 1500) w2++;
			if(player[2].balance == 1500) w3++;
			
			
			//startNewGame();
			
			
		}
		
		player[0].bot = false;
		player[0].rand = false;
		
		System.out.println("Winner: " + w1 + " " + w2 + " " + w3);
		
		
		
	}
	
	public static boolean isActionAllowed() {
		int j = 0;
		for(int i = 0; i < player.length; i++) {
			if(player[i].allIn) j++;
		}
		int k = 0;
		for(int i = 0; i < player.length; i++) {
			if(!player[i].activeInHand) k++;
		}
		if(j>1 || k>1) return false;
		return true;
	}
	
	public static void setAllPlayerToNewHand() {
		for(int i = 0; i < player.length; i++) {
			player[i].acted = false;
			if(player[i].balance == 0) player[i].activeInGame = false;
			if(player[i].activeInGame) player[i].activeInHand = true;
			else player[i].activeInHand = false;
			player[i].allIn = false;
			player[i].playsForSidePot = false;
			//player[i].balancePreFlop = player[i].balance;
		}
	}
	
	public static boolean isActiveGame() {
		int f = 0;
		if(!player[0].activeInGame) f++;
		if(!player[1].activeInGame) f++;
		if(!player[2].activeInGame) f++;
		if(f > 1) return false;
		return true;
	}
	
	
	
	public static void setBlinds() {		
		if(!player[getNextPlayerNum(bigBlindPosition)].activeInGame)
			setButton(getNextPlayerNum(getNextPlayerNum(bigBlindPosition)));
		else setButton(getNextPlayerNum(bigBlindPosition));
		
		if(!player[getPreviousPlayerNum(bigBlindPosition)].activeInGame) 
			player[getPreviousPlayerNum(getPreviousPlayerNum(bigBlindPosition))].setBlind(blind/2);
		else player[getPreviousPlayerNum(bigBlindPosition)].setBlind(blind/2);
		
		player[bigBlindPosition].setBlind(blind);
	}
	public static void setButton(int n) {
		button = n;
		//System.out.println("Button: " + button);
	}
	public static void setMainPot(int n){
		mainPot = n;
	}
	public static void setSidePot(int n) {
		sidePot = n;
	}
	
	public static void setGameState(int n) {
		gameState = n;
	}
	public static void resetBlinds() {
		blind = startingBlind;
		gamesTillLvChangeCount = 0;
	}
	public static void changeActivePlayer() {
		if(activePlayer < 0) setActivePlayer(button);
		else if(activePlayer == 2) setActivePlayer(0);
		else setActivePlayer(activePlayer + 1);
		
		if(!player[activePlayer].activeInGame || !player[activePlayer].activeInHand) changeActivePlayer();
	}
	public static void setActivePlayer(int n) {
		activePlayer = n;
	}
	public static void setHighestBet(int n) {
		highestBet = n;
	}
	public static int biggestStack() {
		//int n = 0;
		int[] array = new int[3];
		for(int i = 0; i < 3; i++)
			array[i] = player[i].balance + player[i].bet;
		
		return max(array);
	}
	public static void setBigBlindPosition(int n) {
		bigBlindPosition = n;
		//System.out.println("BigBlind: " + bigBlindPosition);
	}

	public static void moveBigBlindToNextPosition() {
		
		
		if(bigBlindPosition == -1) {
			Random randomGenerator = new Random();
			setBigBlindPosition(randomGenerator.nextInt(3));
		}else if(bigBlindPosition == 2) {
			setBigBlindPosition(0);
		}else {
			setBigBlindPosition(bigBlindPosition + 1);
		}
		
		if(!player[bigBlindPosition].activeInGame) moveBigBlindToNextPosition();
	}
	public static void calcPot() {
		int[] bets = new int[3];
		for(int i = 0; i < player.length; i++) {
			bets[i] = player[i].bet;
		}
		for(int i = 0; i < bets.length; i++) {
		}
		int min = minWithoutZero(bets);
		int pot = 0;

		for(int i = 0; i < bets.length; i++) {
			if(player[i].bet > 0) {
				pot += min;
				player[i].bet -= min;
			}
		}
		mainPot += pot;
		pot = 0;
		//System.out.println("Main Pot: " + mainPot);
		
		
		
		
		for(int i = 0; i < player.length; i++) {
			bets[i] = player[i].bet;
		}
		
		
		min = minWithoutZero(bets);
		
		for(int i = 0; i < bets.length; i++) {
			if(player[i].bet > 0) {
				pot += min;
				player[i].bet -= min;
				player[i].playsForSidePot = true;
			}
		}
		
		
		sidePot += pot;
		//System.out.println("SidePot: " + sidePot);
		
		for(int i = 0; i < player.length; i++) {
			if(player[i].bet > 0) player[i].balance += player[i].bet;
			player[i].bet = 0;
		}
		
	}
	public static void checkBlindChange() {
		if(gamesTillLvChangeCount == gamesTillLvChange) lvRaise();
		gamesTillLvChangeCount += 1;
	}
	public static void lvRaise() {
		if(blind == 0) blind = 0;
		else if(blind == 20) blind = 30;
		else if(blind == 30) blind = 40;
		else if(blind == 40) blind = 60;
		else if(blind == 60) blind = 80;
		else if(blind == 80) blind = 120;
		else if(blind == 120) blind = 160;
		else if(blind == 160) blind = 200;
		
		gamesTillLvChangeCount = 0;
	}
	
	public static int getNextPlayerNum(int n) {
		if(n == 2) return 0;
		else return n + 1;
	}
	public static int getPreviousPlayerNum(int n) {
		if(n == 0) return 2;
		else return n - 1;
	}	
	
	static public void getNextMove() {
		//System.out.print(activePlayer + " " +player[activePlayer].acted + " ");
		
		if(player[activePlayer].acted == true) {
			changeGameState();
		}	
		if(activeHand && player[activePlayer].activeInHand) {
			//if(!player[activePlayer].bot) player[activePlayer].saveSituation();
			if(player[activePlayer].bot && player[activePlayer].acted == false) {
				if(!player[activePlayer].allIn) {
					player[activePlayer].decideMove();
				}else player[activePlayer].acted = true;
				//getNextMove();
			}
			else if(!isActionAllowed()) {
				player[activePlayer].acted = true;
				//getNextMove();
			}
		}else if(!player[activePlayer].activeInHand) {
			player[activePlayer].acted = true;
			changeActivePlayer();
		}
		pki.updateAll();
		pki.setButtons();
		
		
	}
	public static void changeGameState() {
		if(gameState < 4) {
			
			try {
				writeToTxt(player[0].bet + "," + player[1].bet + "," + player[2].bet + "|");
				writeToTxt(player[0].action + "," + player[1].action + "," + player[2].action + "|");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			calcPot();
			player[0].bet = 0;
			player[1].bet = 0;
			player[2].bet = 0;
			setHighestBet(0);
			gameState++;
			activePlayer = getNextPlayerNum(button);
			pki.openCards(gameState);
			pki.updateAll();
			
			for(int i = 0; i < player.length; i++) {
				player[i].acted = false;
			}
			
			
			
			//System.out.println("gameState: " + gameState);
			//if(gameState == 1) pki.addToLog("Flop opens");
			//else if(gameState == 2) pki.addToLog("Turn opens");
			//else if(gameState == 3) pki.addToLog("River opens");
			
			/*if(gameState == 1) System.out.println("Flop opens\n");
			else if(gameState == 2) System.out.println("Turn opens\n");
			else if(gameState == 3) System.out.println("River opens\n");*/
			
			//pki.addToLog("Pot is " + pot);
		}
		if(gameState == 4) {
			setActiveHand(false);
			int[] winnerArray = evaluate();
			int max = max(winnerArray);
			int maxC = maxC(winnerArray, max);
			//pki.addToLog("Showdown");
			
			givePot(winnerArray, max, maxC);
			
			
			
			/*Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					
					
					pki.setBalanceNeutral(0);
					pki.setBalanceNeutral(1);
					pki.setBalanceNeutral(2);
					
					pot = 0;
					
					gameState++;
					changeGameState();
					
					pki.updateAll();
					if(!isActiveGame()) setActiveGame(false);
				}
			}, 0);*/
			
			pki.setBalanceColorNeutral(0);
			pki.setBalanceColorNeutral(1);
			pki.setBalanceColorNeutral(2);
			
			mainPot = 0;
			
			gameState++;
			//changeGameState();
			pki.setButtons();
			pki.updateAll();
			
			/*for(int i = 0; i < player.length; i++) {
				player[i].writeSituation();
			}*/
			
			if(!isActiveGame()) setActiveGame(false);
			
			
			try {
				writeBalanceToTxt();
				writeNewLineToTxt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*try {
				readFromTxt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}else if(gameState == 5){
				
		}
	}
	
	public static void givePot(int[] winnerArray, int max, int maxC) {
		//System.out.println(player[0].balance + " " + " " + player[1].balance + " " + player[2].balance);
		
		if(sidePot == 0) {
			//System.out.println("pot wird zugesprochen");
			//System.out.println("Pot: " + mainPot);
			//System.out.println("Max: " + max);
			for(int i = 0; i < winnerArray.length; i++) {
				if(winnerArray[i] == max) {
					player[i].balance += mainPot/maxC;
					//pki.setBalanceColorPositive(i);
					//System.out.println(player[i].playerName + " wins " + mainPot/maxC);
				}
				if(player[i].balance == 0) player[i].activeInGame = false;
			}
		}
		else {
			int[] winnerArrayForSidePot = evaluateForSidePot();
			int maxForSidePot = max(winnerArrayForSidePot);
			int maxForSidePotCount = maxC(winnerArrayForSidePot, maxForSidePot);
			
			for(int i = 0; i < winnerArray.length; i++) {
				if(winnerArrayForSidePot[i] == maxForSidePot) {
					player[i].balance += sidePot/maxForSidePotCount;
					//System.out.println(player[i].playerName + " wins " + sidePot/maxForSidePotCount);
					//pki.setBalanceColorPositive(i);
				}
			}
			
			for(int i = 0; i < winnerArray.length; i++) {
				if(winnerArray[i] == max) {
					player[i].balance += mainPot/maxC;
					//pki.setBalanceColorPositive(i);
					//System.out.println(player[i].playerName + " wins " + mainPot/maxC);
				}
				if(player[i].balance == 0) player[i].activeInGame = false;
			}
			
			
		}
	}
	
	public static int[] evaluate() {
		int [] cards0 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[5],cardDeck[6]};
		int [] cards1 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[7],cardDeck[8]};
		int [] cards2 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[9],cardDeck[10]};
		
		int[] array = DetermineWinner.compareThree(cards0, cards1, cards2);
		if(!player[0].activeInHand) array[0] = -1;
		if(!player[1].activeInHand) array[1] = -1;
		if(!player[2].activeInHand) array[2] = -1;
		
		return array;
	}
	
	public static int[] evaluateForSidePot() {
		int [] cards0 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[5],cardDeck[6]};
		int [] cards1 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[7],cardDeck[8]};
		int [] cards2 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[9],cardDeck[10]};
		
		int[] array = DetermineWinner.compareThree(cards0, cards1, cards2);
		if(!player[0].activeInHand || !player[0].playsForSidePot) array[0] = -1;
		if(!player[1].activeInHand || !player[1].playsForSidePot) array[1] = -1;
		if(!player[2].activeInHand || !player[2].playsForSidePot) array[2] = -1;
		
		return array;
	}
	public static int max(int[] array) {
		int max = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] > max) max = array[i];
		}
		return max;
	}
	public static int minWithoutZero(int[] array) {
		int min = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] != 0) min = array[i];
		}
		
		for(int i = 0; i < array.length; i++) {
			if(array[i] < min && array[i] != 0) min = array[i];
		}
		return min;
	}
	public static int maxC(int[] array, int max) {
		int maxC = 0;
		for(int i = 0; i<array.length; i++) {
			if(array[i] == max) maxC++;
		}
		return maxC;
	}
	
	public static int[] evaluate(int[] cards){
		int [] cards0 = {cards[0],cards[1],cards[2],cards[3],cards[4],cards[5],cards[6]};
		int [] cards1 = {cards[0],cards[1],cards[2],cards[3],cards[4],cards[7],cards[8]};
		int [] cards2 = {cards[0],cards[1],cards[2],cards[3],cards[4],cards[9],cards[10]};
		
		int[] array = DetermineWinner.compareThree(cards0, cards1, cards2);
		if(!player[0].activeInHand) array[0] = -1;
		if(!player[1].activeInHand) array[1] = -1;
		if(!player[2].activeInHand) array[2] = -1;
		
		return array;
	}
	
	public static void setActiveHand(boolean b) {
		activeHand = b;
		pki.setButtons();
	}
	public static void setActiveGame(boolean b) {
		activeGame = b;
		pki.setButtons();
	}
	
	
	public static void writeNewHandToTxt() throws IOException{		
		writeCardsToTxt();
		writeNewLineToTxt();
		writeBalanceToTxt();
		writeToTxt(button + "|");
		writeToTxt(blind + "|");
	}
	
	public static void writeBalanceToTxt() throws IOException{
		
		Writer output;
		output = new BufferedWriter(new FileWriter("ausgabe", true));
		String stringToAppend = (player[0].balance + player[0].bet) + "," + (player[1].balance + player[1].bet) + "," + (player[2].balance + player[2].bet) + "|";
		output.append(stringToAppend);
		output.close();
	}
	
	public static void writeCardsToTxt() throws IOException{
		Writer output;
		output = new BufferedWriter(new FileWriter("ausgabe", true));
		String stringToAppend = "";
		for (int i = 0; i < cardDeck.length; i++){
			stringToAppend += cardDeck[i] + " ";
		}
		output.append(stringToAppend);
		output.close();
	}
	
	public static void writeNewLineToTxt() throws IOException{
		Writer output;
		output = new BufferedWriter(new FileWriter("ausgabe", true));
		output.write(System.lineSeparator());
		output.close();
	}
	
	public static void writeToTxt(String s) throws IOException{
		Writer output;
		output = new BufferedWriter(new FileWriter("ausgabe", true));
		String stringToAppend = s;
		output.append(stringToAppend);
		output.close();	
	}
	
	public static void readFromTxt() throws IOException {
		FileReader fr = new FileReader("ausgabe");
		BufferedReader br = new BufferedReader(fr);
		
		String line0 = br.readLine();
		String line1 = br.readLine();
 		while(line0 != null) {
			dsW.writeInDS(line0, line1);
			
			//System.out.println(line0 + " " + line1);
			try {
				line0 = br.readLine();
				line1 = br.readLine();
			}catch(Exception e) {
				
			}
			
		}
 		
 		br.close();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pki.frame.pack();
					pki.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	
		// TODO Auto-generated method stub
		/*for(int i = 0; i < 1000; i++) {
			str.strategy[1][1][1][1][1][1] += 1;
			System.out.println(str.strategy[1][1][1][1][1][1]);
		}*/
	}


}
