
public class Strategies {
	int[][] range = {
		{1,2,2,2,2, 3,3,3,3,3, 3,3,3},	//A
		{2,1,2,2,2, 3,3,3,3,3, 3,3,3},	//K
		{2,2,1,2,2, 3,3,3,3,3, 6,6,6},	//Q
		{2,2,2,1,2, 3,3,3,3,3, 6,6,6},	//J
		{2,2,2,2,1, 3,3,3,3,3, 6,6,6},	//10
			
		{3,3,3,3,7, 4,7,8,8,8, 8,8,8},	//9
		{3,3,3,3,3, 7,4,7,8,8, 8,8,8},	//8
		{6,6,6,6,6, 8,7,4,8,9, 9,9,9},	//7
		{6,6,6,6,6, 8,8,8,4,8, 9,9,9},	//6
		{6,6,6,6,6, 9,9,9,8,4, 8,9,9},	//5
		
		{3,3,3,3,3, 9,9,9,9,8, 5,8,9},	//4
		{3,3,3,3,3, 9,9,9,9,9, 8,5,8},	//3
		{3,3,3,3,3, 9,9,9,9,9, 9,8,5},	//2
	};
	
	public int getRating(int i, int j) {
		if(i%4 == j%4) {
			if(i/4 > j/4) return range[i/4][j/4];
			else return range[j/4][i/4];
		}else {
			if(i/4 < j/4) return range[i/4][j/4];
			else return range[j/4][i/4];
		}
	}
	
	public int getButton(int playerNum) {	//0-Button	1-Kein Button
		if(GameController.button == playerNum) {
			return 0;
		} return 1;
	}
	
	public int getPlayerBB(int playerNum) {
		return ((GameController.player[playerNum].balance + GameController.player[playerNum].bet) / GameController.blind)/5;
	}
	
	public int getPotSizeInBB() {
		int pot = 0;
		for(int i = 0; i < GameController.player.length; i++) {
			pot += GameController.player[i].bet;
		}
		return ((GameController.mainPot + pot)/GameController.blind)/5;
	}
	
	public int getWasRaised() {
		if(GameController.highestBet > GameController.blind) return 1;
		return 0;
	}
	
	int[][][][][][] preFlopStrategy = new int[10][2][16][16][2][3];
	//0 - Card Rating
	//1 - Button Position
	//2 - BB before Hand / 5
	//3 - Pot Size in BB
	//4 - Was Raised
	//5 - Fold - Call - Raise
	
	
	//Flop Raiting
	//0 - Card Rating
	//1 - Button Position
	//2 - BB on Flop / 5
	//3 - Pot Size in BB
	//4 - Was Raised
	//5 - Fold - Call - Raise
	
}
