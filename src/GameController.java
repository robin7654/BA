import java.awt.Color;
import java.util.Random;

public class GameController {
	
	public static Deck deck = new Deck();
	public static PokerKI pki = new PokerKI();
	public static MonteCarloSimulation mcs = new MonteCarloSimulation();
	
	static int[] cardDeck = new int[11];
	
	public static boolean activeHand = false;
	public static boolean activeGame = false;
	
	
	Player[] player = new Player[3];
	
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
	
	
	public void startNewGame() {
		changeActiveHand(true);
		changeActiveGame(true);
		
		setBigBlindPosition(-1);
		player[0] = new Player(false);
		player[1] = new Player(true);
		player[2] = new Player(true);
		resetBlinds();
		startHand();
	}
	public void startHand() {
		changeActiveHand(true);
		setGameState(0);
		moveBigBlindToNextPosition();
		checkBlindChange();
		setPot(0);
		setHighestBet(blind);
		cardDeck = deck.shuffleDeck();
		
		setBlinds();
		changeActivePlayer();
		
		getNextMove();
	}
	
	
	
	public void setBlinds() {
		player[bigBlindPosition].raise(blind);
		
		if(!player[getNextPlayerNum(bigBlindPosition)].activeInGame) 
			setButton(getNextPlayerNum(getNextPlayerNum(bigBlindPosition)));
		else setButton(getNextPlayerNum(bigBlindPosition));
		
		if(!player[getPreviousPlayerNum(bigBlindPosition)].activeInGame)
			player[getPreviousPlayerNum(getPreviousPlayerNum(bigBlindPosition))].raise(blind/2);
		else player[getPreviousPlayerNum(bigBlindPosition)].raise(blind/2);	
	}
	public void setButton(int n) {
		button = n;
	}
	public void setPot(int n){
		pot = n;
	}
	public void setGameState(int n) {
		gameState = n;
	}
	public void resetBlinds() {
		blind = startingBlind;
		gamesTillLvChange = 0;
	}
	public void changeActivePlayer() {
		if(activePlayer < 0) activePlayer = button;
		else if(activePlayer == 2) activePlayer = 0;
		else activePlayer += 1;
		
		if(!player[activePlayer].activeInGame) changeActivePlayer();
	}
	public void setActivePlayer(int n) {
		activePlayer = n;
	}
	public void setHighestBet(int n) {
		highestBet = n;
	}
	
	public void setBigBlindPosition(int n) {
		bigBlindPosition = n;
	}

	public void moveBigBlindToNextPosition() {
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
	public void calcPot() {//TODO: SidePot
		setPot(pot + player[0].bet + player[1].bet + player[2].bet);
	}
	public void checkBlindChange() {
		if(gamesTillLvChangeC == gamesTillLvChange) lvRaise();
		gamesTillLvChangeC += 1;
	}
	public void lvRaise() {
		if(blind == 20) blind = 30;
		else if(blind == 30) blind = 40;
		else if(blind == 40) blind = 60;
		else if(blind == 60) blind = 80;
		else if(blind == 80) blind = 120;
		else if(blind == 120) blind = 160;
		else if(blind == 160) blind = 200;
		
		gamesTillLvChangeC = 0;
	}
	
	public int getNextPlayerNum(int n) {
		if(n == 2) return 0;
		else return n + 1;
	}
	public int getPreviousPlayerNum(int n) {
		if(n == 0) return 2;
		else return n - 1;
	}	
	
	public void getNextMove() {
		
		if(activePlayerC == activePlayer) {
			changeGameState();
			return;
		}
		
		if(activeHand) {
			if(player[activePlayer].bot) player[activePlayer].decideMove();
		}
	}
	public void changeGameState() {
		if(gameState < 4) {
			pot = pot + player[0].bet + player[1].bet + player[2].bet;
			player[0].setBet(0);
			player[1].setBet(0);
			player[2].setBet(0);
			setHighestBet(0);
			gameState++;
			pki.openCards(gameState);
			
			System.out.println("gameState: " + gameState);
		}
		if(gameState == 4) {
			int[] winnerArray = evaluate();
			int max = max(winnerArray);
			
			if(max == 2) {
				for(int i = 0; i < winnerArray.length; i++) {
					
				}
			}
			
			Timer
		}
	}
	
	public void givePot(int[] winnerArray, int max) {
		if(max == 2) {
			for(int i = 0; i < winnerArray.length; i++) {
				if(winnerArray[i] == 2) {
					System.out.println("Winner: Player" + i);
					player[i].balance += pot;
					
					pki.setBalancePositive(i);
				}
			}
		}else if(max == 1) {
			
		}
	}
	
	public int[] evaluate() {
		//TODO
		int[] array = new int[5];
		return array;
	}
	public int max(int[] array) {
		return 0;
	}
	
	
	public static void changeActiveHand(boolean b) {
		activeHand = b;
		pki.setButtons();
	}
	public void changeActiveGame(boolean b) {
		activeGame = b;
		pki.setButtons();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
