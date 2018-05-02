import java.util.Random;


public class Deck {
	
	public int [] shuffleDeck(){
		int [] shuffledDeck = new int [11];
		Random randomGenerator = new Random();
		for (int i = 0; i < shuffledDeck.length; i++){
			int ran;
			ran = randomGenerator.nextInt(52);
			while (contains(shuffledDeck, ran)){
				ran = randomGenerator.nextInt(52);
			}
			shuffledDeck[i] = ran;
		}
		return shuffledDeck;
	}
	private boolean contains (int [] arr, int a) {
		for (int i = 0; i < arr.length; i++){
			if ( a == arr[i]){
				return true;
			}
		}
		return false;
	}

			
}
