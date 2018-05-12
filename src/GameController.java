import java.awt.EventQueue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
	
	public static Deck deck = new Deck();
	public static PokerKI pki = new PokerKI();
	public static MonteCarloSimulation mcs = new MonteCarloSimulation();
	
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
	static int pot = 0;
	static int gamesTillLvChange = 9;
	static int gamesTillLvChangeC = 0;
	
	static int activePlayers = 3;
	static int activePlayerC = 0;
	
	
	public static void startNewGame() {
		player[0] = new Player(false, "Player");
		player[1] = new Player(true, "Bot1");
		player[2] = new Player(true, "Bot2");
		
		setActiveHand(true);
		setActiveGame(true);
		
		setBigBlindPosition(-1);
		
		resetBlinds();
		startNewHand();
	}
	public static void startNewHand() {
		setActiveHand(true);
		setGameState(0);
		moveBigBlindToNextPosition();
		checkBlindChange();
		setPot(0);
		setHighestBet(blind);
		cardDeck = deck.shuffleDeck();
		
		setBlinds();
		changeActivePlayer();
		
		pki.openCards(gameState);
		pki.updateAll();
		
		getNextMove();
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
		System.out.println("Button: " + button);
	}
	public static void setPot(int n){
		pot = n;
	}
	public static void setGameState(int n) {
		gameState = n;
	}
	public static void resetBlinds() {
		blind = startingBlind;
		gamesTillLvChangeC = 0;
	}
	public static void changeActivePlayer() {
		if(activePlayer < 0) setActivePlayer(button);
		else if(activePlayer == 2) setActivePlayer(0);
		else setActivePlayer(activePlayer + 1);
		
		if(!player[activePlayer].activeInGame) changeActivePlayer();
	}
	public static void setActivePlayer(int n) {
		activePlayer = n;
	}
	public static void setHighestBet(int n) {
		highestBet = n;
	}
	
	public static void setBigBlindPosition(int n) {
		bigBlindPosition = n;
		System.out.println("BigBlind: " + bigBlindPosition);
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
	public static void calcPot() {//TODO: SidePot
		setPot(pot + player[0].bet + player[1].bet + player[2].bet);
	}
	public static void checkBlindChange() {
		if(gamesTillLvChangeC == gamesTillLvChange) lvRaise();
		gamesTillLvChangeC += 1;
	}
	public static void lvRaise() {
		if(blind == 20) blind = 30;
		else if(blind == 30) blind = 40;
		else if(blind == 40) blind = 60;
		else if(blind == 60) blind = 80;
		else if(blind == 80) blind = 120;
		else if(blind == 120) blind = 160;
		else if(blind == 160) blind = 200;
		
		gamesTillLvChangeC = 0;
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
		if(activePlayerC == activePlayers) {
			changeGameState();
			if(!activeHand) return;
		}
		
		if(activeHand) {
			if(player[activePlayer].bot) {
				player[activePlayer].decideMove();
				getNextMove();
			}
		}
		pki.updateAll();
		pki.setButtons();
		
		
	}
	public static void changeGameState() {
		if(gameState < 4) {
			calcPot();
			player[0].setBet(0);
			player[1].setBet(0);
			player[2].setBet(0);
			setHighestBet(0);
			gameState++;
			activePlayer = getNextPlayerNum(button);
			pki.openCards(gameState);
			pki.updateAll();
			
			GameController.activePlayerC = 0;
			
			System.out.println("gameState: " + gameState);
		}
		if(gameState == 4) {
			setActiveHand(false);
			int[] winnerArray = evaluate();
			int max = max(winnerArray);
			int maxC = maxC(winnerArray, max);
			
			givePot(winnerArray, max, maxC);
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					
					
					pki.setBalanceNeutral(0);
					pki.setBalanceNeutral(1);
					pki.setBalanceNeutral(2);
					
					pot = 0;
					
					gameState++;
					changeGameState();
					
					pki.updateAll();
				}
			}, 2000);
		}
		/*else if(gameState == 5){
			startNewHand();
		}*/
	}
	
	public static void givePot(int[] winnerArray, int max, int maxC) {
		for(int i = 0; i < winnerArray.length; i++) {
			if(winnerArray[i] == max) {
				System.out.println("Winner: Player" + i);
				player[i].balance += pot/maxC;
				pki.setBalancePositive(i);
			}
			if(player[i].balance == 0) player[i].activeInGame = false;
		}
	}
	
	public static int[] evaluate() {
		int [] cards0 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[5],cardDeck[6]};
		int [] cards1 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[7],cardDeck[8]};
		int [] cards2 = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4],cardDeck[9],cardDeck[10]};
		
		int[] array = DetermineWinner.compareThree(cards0, cards1, cards2);
		if(!player[0].activeInHand) array[0] = -1;
		if(!player[0].activeInHand) array[1] = -1;
		if(!player[0].activeInHand) array[2] = -1;
		
		return array;
	}
	public static int max(int[] array) {
		int max = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] > max) max = array[i];
		}
		return max;
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
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pki.frame.pack();
					pki.frame.setVisible(true);
					
					pki.updateLog("Test1");
					pki.updateLog("Test2");
					pki.updateLog("Test1");
					pki.updateLog("Test2");
					pki.updateLog("Test1");
					pki.updateLog("Test2");
					pki.updateLog("Test1");
					pki.updateLog("Test2");
					pki.updateLog("Test1");
					pki.updateLog("Test2");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// TODO Auto-generated method stub
	}

}
