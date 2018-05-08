
public class Player {
	int balance;
	int bet;
	boolean activeInGame;
	boolean activeInHand;
	boolean allIn;
	public Player(){
		balance = 500;
		activeInGame = true;
		activeInHand = true;
		bet = 0;
		allIn = false;
	}
	
	public void raise(int n) {
		if(balance + bet > n) {
			balance = balance - (n - bet);
			bet = n;
		}
		else {
			bet += balance;
			allIn = true;
		}
	}
	public void fold(){
		activeInHand = false;
	}
	 
}
