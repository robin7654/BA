import java.util.stream.IntStream;
import java.util.Arrays;

public class DetermineWinner {
	
	public static int compareTwo(int [] p1, int [] p2){
		int [] a = playersHand(p1);
		int [] b = playersHand(p2);
		
		for (int i = 1; i < a.length; i++) {
			a[i] = (int) a[i] / 4;
			b[i] = (int) b[i] / 4;
		}
		
		
		
		//System.out.println(Arrays.toString(a));
		//System.out.println(Arrays.toString(b));
		
		
		for (int i = 0; i < 6; i++){
			if (a[i] < b[i]){
				return 1;
			}
			if (a[i] > b[i]){
				return -1;
			}
		}
		return 0;
	}
	
	public static int[] compareThree(int[] p1, int[] p2, int[] p3) {
		int[] array = new int[3];
		array[0] = 0;
		array[1] = 0;
		array[2] = 0;
		
		
		int [] a = playersHand(p1);
		int [] b = playersHand(p2);
		int [] c = playersHand(p3);
		
		for (int i = 1; i < a.length; i++) {
			a[i] = (int) a[i] / 4;
			b[i] = (int) b[i] / 4;
			c[i] = (int) c[i] / 4;
		}
		
		if(compareTwo(p1,p2) == 1) array[0]++;
		if(compareTwo(p1,p3) == 1) array[0]++;
		if(compareTwo(p2,p1) == 1) array[1]++;
		if(compareTwo(p2,p3) == 1) array[1]++;
		if(compareTwo(p3,p1) == 1) array[2]++;
		if(compareTwo(p3,p2) == 1) array[2]++;
		
		System.out.println("Spieler0 Platz: " + array[0] + " " + Arrays.toString(p1));
		System.out.println("Spieler1 Platz: " + array[1] + " " + Arrays.toString(p2));
		System.out.println("Spieler2 Platz: " + array[2] + " " + Arrays.toString(p3));
		
		
		return array;
	}
	
	
	
	
	public static void playersFive(int[] cards) {

//		isFlush(cards, 0);
//		isFlush(cards, 1);
//		isFlush(cards, 2);
//		isFlush(cards, 3);
//
//		//int [] test = {8,28,32,37,43,26,29};
//		isHighCard(cards);
//		isPair(cards);
//		isTwoPair(cards);
//		isTrips(cards);
//		isStraight(cards);
//		//isStraight(test);
//		isFull(cards);
//		isQuads(cards);



		if (isPair(cards) != null){
			//System.out.println(Arrays.toString(addKicker(cards,isPair(cards))));
		}
		return;
	}
	public static int bestCards(int[] cards0, int[] cards1){
		if (cards0[0] > cards1[0]){
			return 1;
		}else if (cards0[0] < cards1[0]) {
			return 2;
		}
		for (int i = 1; i < cards0.length; i ++){
			if ((int)cards0[i]/4 < (int)cards1[i]/4){
				return 1;
			}else if ((int)cards0[i]/4 > (int)cards1[i]/4) {
				return 2;
			}
		}
		return 0;
	}

	public static int[] playersHand(int[] cards) {
		int[] playersHand = new int[6];
				
		if(isQuads(cards)!= null){
			System.out.println("quads");
			playersHand[0]=1;
			int[] playersFive = addKicker(cards,isQuads(cards));
			for(int i = 1; i < playersHand.length; i++){
				playersHand[i] = playersFive[i-1];
			}
			return playersHand;
		}else if (isFull(cards)!= null){
			playersHand[0]=2;
			int[] playersFive = isFull(cards);
			for(int i = 1; i < playersHand.length; i++){
				playersHand[i] = playersFive[i-1];
			}
			return playersHand;
		}if(isFlush(cards)!= null){
			playersHand[0]=3;
			int[] playersFive = isFlush(cards);
			for(int i = 1; i < playersHand.length; i++){
				playersHand[i] = playersFive[i-1];
			}
			return playersHand;
		}else if (isStraight(cards)!= null){
			playersHand[0]=4;
			int[] playersFive = isStraight(cards);
			for(int i = 1; i < playersHand.length; i++){
				playersHand[i] = playersFive[i-1];
			}
			return playersHand;
		}else if(isTrips(cards)!= null){
			playersHand[0]=5;
			int[] playersFive = addKicker(cards,isTrips(cards));
			for(int i = 1; i < playersHand.length; i++){
				playersHand[i] = playersFive[i-1];
			}
			return playersHand;
		}else if(isTwoPair(cards)!= null){
			playersHand[0]=6;
			int[] playersFive = addKicker(cards,isTwoPair(cards));
			for(int i = 1; i < playersHand.length; i++){
				playersHand[i] = playersFive[i-1];
			}
			return playersHand;
		}else if(isPair(cards)!= null){
			playersHand[0]=7;
			int[] playersFive = addKicker(cards,isPair(cards));
			for(int i = 1; i < playersHand.length; i++){
				playersHand[i] = playersFive[i-1];
			}
			return playersHand;
		}else {
			playersHand[0]=8;
			int [] x = {min(cards)};
			int[] playersFive = addKicker(cards,x);
			for(int i = 1; i < playersHand.length; i++){
				playersHand[i] = playersFive[i-1];
			}
			return playersHand;
		}


	}
	public static int[] isFlush(int[] arr) {
		
		for(int i = 0; i < 4; i++){
			if(isFlush(arr,i)!= null){
				return isFlush(arr,i);
			}
		}
		return null;
		
	}

	public static int[] isFlush(int[] arr, int suite) {
		int[] suites = new int[arr.length];
		int[] flush = new int[5];
		int index = 0;
		Arrays.sort(arr);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] % 4 == suite) {
				suites[i] = 1;
			} else
				suites[i] = 0;
		}
		if (IntStream.of(suites).sum() >= 5) {
			for (int i = 0; i < arr.length; i++) {
				if (suites[i] == 1 && index < 5) {
					flush[index] = arr[i];
					index++;
				}
			}
			System.out.println("flush");
			return flush;
		}
		return null;
	}

	public static int[] isStraight(int[] arr) {
		Arrays.sort(arr);
		int[] straight = new int[5];
		int[] values = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			values[i] = (int) arr[i] / 4;
		}

		int count = 0;
		straight[0] = arr[0];
		for (int i = 0; i < arr.length -1; i++) {
			if (values[i] == values[i+1] -1){
				straight[count+1] = arr[i+1];
				count++;
				if (count == 4){
					System.out.println("straight");
					return straight; 
				}
			} else if(values[i] != values[i+1]){
				count = 0;
				straight[0]=arr[i+1];
			}
		}
		return null;
	}


	public static int[] isQuads(int[] arr) {
		Arrays.sort(arr);
		int[] quads = new int[4];
		int[] occurrence = occurrence(arr);
		//vierling
		if (max(occurrence) == 4) {
			int counter = 0;
			for (int j = 0; j < arr.length; j++) {
				if (occurrence[j] == 4) {
					quads[counter] = arr[j];

					counter++;
					if (counter==quads.length){
						break;
					}
				}
			}
			return quads;
		}
		return null;

	}

	public static int[] isTrips(int[] arr){
		Arrays.sort(arr);
		int[] trips = new int[3];
		int[] occurrence = occurrence(arr);
		//drilling
		if (max(occurrence) == 3) {
			int counter = 0;
			for (int j = 0; j < arr.length; j++) {
				if (occurrence[j] == 3) {
					trips[counter] = arr[j];

					counter++;
					if (counter==trips.length){
						break;
					}
				}
			}
			return trips;
		}
		return null;
	}


	public static int[] isFull(int[] arr){
		Arrays.sort(arr);
		int[] full = new int[5];
		int[] occurrence = occurrence(arr);
		//fullHouse
		if (max(occurrence) == 3 && IntStream.of(occurrence).sum() - occurrence.length > 7 ) {
			int counter = 0;
			for (int j = 0; j < arr.length; j++) {
				if (occurrence[j] == 3) {
					full[counter] = arr[j];

					counter++;
					if (counter==3){
						break;
					}
				}
			}
			for (int j = 0; j < arr.length; j++) {
				if (occurrence[j] >= 2 ) {
					if (arr[j]!=full[0] & arr[j]!=full[1] & arr[j]!=full[2])
						full[counter] = arr[j];

					counter++;
					if (counter==5){
						break;
					}
				}
			}

			return full;
		}
		return null;
	}


	public static int[] isTwoPair(int[] arr){
		Arrays.sort(arr);
		int[] twoPair = new int[4];
		int[] occurrence = occurrence(arr);
		//twoPair
		if (max(occurrence) == 2 && IntStream.of(occurrence).sum() - occurrence.length >3 ) {
			int counter = 0;
			for (int j = 0; j < arr.length; j++) {
				if (occurrence[j] == 2) {
					twoPair[counter] = arr[j];	

					counter++;
					if (counter==twoPair.length){
						break;
					}
				}

			}

			return twoPair;
		}
		return null;
	}

	public static int[] isPair(int[] arr){
		Arrays.sort(arr);
		int[] pair = new int[2];
		int[] occurrence = occurrence(arr);
		//Pair
		if (max(occurrence) == 2) {
			int counter = 0;
			for (int j = 0; j < arr.length; j++) {
				if (occurrence[j] == 2) {
					pair[counter] = arr[j];

					counter++;
					if (counter==pair.length){
						break;
					}
				}
			}

			return pair;
		}
		return null;
	}


	//kicker anhängen; all cards muss sortiert sein
	public static int[] addKicker(int[] allCards, int[] pairedCards){
		
		int[] playingFive = new int[5];
		for (int i = 0; i < pairedCards.length; i++){
			playingFive[i] = pairedCards[i];
		}
		
		int counter = pairedCards.length;
		for (int j = 0; j < allCards.length; j++){
			if (contains(pairedCards, allCards[j]) == false ){
				playingFive[counter] = allCards[j];

				counter++;
				if (counter==playingFive.length){
					break;
				}

			}
		}
		return playingFive;
	}

	//highCards
	public static int[] isHighCard(int[] arr){
		Arrays.sort(arr);
		int[] playingFive = new int[5];
		for (int i = 0; i < Math.min(5, arr.length); i++){
			playingFive[i] = arr[i];
		}
		System.out.println(Arrays.toString(playingFive));
		return playingFive;
	}




	public static int[] occurrence(int[] arr){
		int[] values = new int[arr.length];
		int[] occurrence = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			values[i] = (int) arr[i] / 4;
		}
		for (int i = 0; i < arr.length; i++) {
			occurrence[i] = 0;
			for (int j = 0; j < arr.length; j++) {
				if (values[i] == values[j]) {
					occurrence[i]++;
				}
			}
		}
		return occurrence;


	}
	public static boolean contains(int[] arr, int a){
		for (int i = 0; i < arr.length; i++){
			if (a == arr[i]){
				return true;
			}
		}
		return false;

	}
	//max wert aus array
	public static int max(int[] arr){
		int max = arr[0];
		for (int i = 0; i < arr.length; i++){
			if (arr[i] > max){
				max = arr[i];
			}
		}
		return max;
	}
	public static int min(int[] arr){
		int min = arr[0];
		for (int i = 0; i < arr.length; i++){
			if (arr[i] < min){
				min = arr[i];
			}
		}
		return min;
	}
	
}
