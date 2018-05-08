
public class PreflopStrategy {
	//1 fold
	//2 call
	//3 min r/f
	//4 min r/c
	//5 raise 2.5/c
	//9 raise 2.5/f
	//6 raise 3x/f
	//7 raise 3x/c
	//8 all in
	//0 3-5bb
	
	public int preflopMove(int dealer, int player, int opponent, int blind){
		return 0; 
	}
	
	
	
			
	int[][] range1 = {
			{8,8,8,8,8, 8,8,8,8,8, 8,8,8},
			{8,8,8,8,8, 8,8,8,8,1, 1,1,1},
			{8,8,8,8,8, 8,8,1,1,1, 1,1,1},
			{8,8,8,8,8, 8,8,1,1,1, 1,1,1},
			{8,8,8,8,8, 8,8,8,1,1, 1,1,1},
			
			{8,1,1,1,1, 8,8,8,1,1, 1,1,1},
			{8,1,1,1,1, 1,8,8,8,1, 1,1,1},
			{8,1,1,1,1, 1,1,8,8,1, 1,1,1},
			{8,1,1,1,1, 1,1,1,8,1, 1,1,1},
			{8,1,1,1,1, 1,1,1,1,8, 1,1,1},
			
			{8,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
			
	
	int[][] range2 = {
			{4,4,4,4,4, 4,8,8,8,8, 8,8,8},
			{8,4,3,3,3, 3,3,3,3,1, 1,1,1},
			{8,3,4,3,3, 3,3,3,3,1, 1,1,1},
			{8,3,3,4,3, 3,3,3,3,1, 1,1,1},
			{8,3,3,3,4, 3,3,3,3,1, 1,1,1},
			
			{8,3,3,3,3, 8,3,3,3,1, 1,1,1},
			{8,3,1,1,1, 3,8,3,3,1, 1,1,1},
			{8,1,1,1,1, 1,1,8,3,3, 1,1,1},
			{3,1,1,1,1, 1,1,1,8,3, 3,1,1},
			{3,1,1,1,1, 1,1,1,1,8, 3,1,1},
			
			{3,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{3,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{3,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range3 = {
			{4,4,4,4,4, 4,8,8,8,8, 8,8,8},
			{8,4,3,3,3, 3,3,3,3,3, 1,1,1},
			{8,3,4,3,3, 3,3,3,3,1, 1,1,1},
			{8,3,3,4,3, 3,3,3,3,1, 1,1,1},
			{8,3,3,3,8, 3,3,3,3,1, 1,1,1},
			
			{3,3,3,3,3, 8,3,3,3,1, 1,1,1},
			{3,3,3,3,3, 3,8,3,3,1, 1,1,1},
			{3,3,1,1,1, 1,1,8,3,3, 1,1,1},
			{3,1,1,1,1, 1,1,1,8,3, 3,1,1},
			{3,1,1,1,1, 1,1,1,1,8, 3,1,1},
			
			{3,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{3,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{3,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range4 = {
			{7,7,7,8,8, 8,8,8,8,8, 8,8,8},
			{8,7,7,1,1, 1,1,1,1,1, 1,1,1},
			{8,1,7,1,1, 1,1,1,1,1, 1,1,1},
			{8,1,1,7,1, 1,1,1,1,1, 1,1,1},
			{8,1,1,1,7, 1,1,1,1,1, 1,1,1},
			
			{8,1,1,1,1, 8,1,1,1,1, 1,1,1},
			{8,1,1,1,1, 1,8,1,1,1, 1,1,1},
			{8,1,1,1,1, 1,1,8,1,1, 1,1,1},
			{8,1,1,1,1, 1,1,1,8,1, 1,1,1},
			{8,1,1,1,1, 1,1,6,1,8, 1,1,1},
			
			{8,1,1,1,1, 1,1,1,6,1, 8,1,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range5 = {
			{7,7,7,7,8, 8,8,8,8,8, 8,8,8},
			{7,7,7,1,1, 1,1,1,1,1, 1,1,1},
			{8,1,7,1,1, 1,1,1,1,1, 1,1,1},
			{8,1,1,7,1, 1,1,1,1,1, 1,1,1},
			{8,1,1,1,7, 1,1,1,1,1, 1,1,1},
			
			{8,1,1,1,1, 7,1,1,1,1, 1,1,1},
			{8,1,1,1,1, 1,8,8,1,1, 1,1,1},
			{8,1,1,1,1, 1,1,8,8,1, 1,1,1},
			{8,1,1,1,1, 1,1,1,8,1, 1,1,1},
			{8,1,1,1,1, 1,1,6,1,8, 1,1,1},
			
			{8,1,1,1,1, 1,1,1,6,6, 8,1,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	
	int[][] range6 = {
			{8,8,8,8,8, 8,8,8,8,8, 8,8,8},
			{8,8,8,8,2, 2,2,2,2,2, 1,1,1},
			{8,8,8,2,2, 2,2,2,2,1, 1,1,1},
			{8,2,2,8,2, 2,2,2,1,1, 1,1,1},
			{8,2,2,2,8, 2,2,2,2,1, 1,1,1},
			
			{8,2,2,2,2, 8,2,2,1,1, 1,1,1},
			{8,2,2,2,2, 2,8,2,2,1, 1,1,1},
			{8,2,1,1,1, 1,2,8,2,2, 1,1,1},
			{8,1,1,1,1, 1,1,1,8,2, 1,1,1},
			{8,1,1,1,1, 1,1,1,1,8, 2,1,1},
			
			{8,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	
	int[][] range7 = {
			{7,8,8,8,8, 8,8,8,8,8, 8,8,8},
			{8,7,8,8,2, 2,2,2,2,2, 1,1,1},
			{8,2,7,2,2, 2,2,2,2,1, 1,1,1},
			{8,2,2,8,2, 2,2,2,1,1, 1,1,1},
			{8,2,2,2,8, 2,2,2,2,1, 1,1,1},
			
			{8,2,2,2,2, 8,2,2,2,1, 1,1,1},
			{8,2,2,2,2, 2,8,2,2,2, 1,1,1},
			{8,2,2,1,2, 2,2,8,2,2, 2,1,1},
			{2,2,1,1,1, 1,2,2,8,2, 2,1,1},
			{2,1,1,1,1, 1,1,1,2,8, 2,1,1},
			
			{2,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{2,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{2,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range8 = {
			{8,8,8,8,8, 8,8,8,8,8, 8,8,8},
			{8,8,8,8,8, 8,8,8,8,8, 1,1,1},
			{8,8,8,8,8, 8,8,8,8,1, 1,1,1},
			{8,8,8,8,8, 8,8,1,1,1, 1,1,1},
			{8,8,8,8,8, 8,8,1,1,1, 1,1,1},
			
			{8,8,1,1,1, 8,8,1,1,1, 1,1,1},
			{8,8,1,1,1, 1,8,8,1,1, 1,1,1},
			{8,8,1,1,1, 1,1,8,1,1, 1,1,1},
			{8,8,1,1,1, 1,1,1,8,1, 1,1,1},
			{8,8,1,1,1, 1,1,1,1,8, 1,1,1},
			
			{8,8,1,1,1, 1,1,1,1,1, 8,1,1},
			{8,8,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,8,1,1,1, 1,1,1,1,1, 1,1,8},
			};
			
	int[][] range9 = {
			{5,5,5,5,8, 8,8,8,8,8, 8,8,8},
			{8,5,8,8,8, 1,1,1,1,1, 1,9,9},
			{8,8,5,8,8, 1,1,1,1,1, 1,9,9},
			{8,8,8,5,8, 1,9,1,1,1, 1,1,1},
			{8,8,1,1,8, 1,1,9,9,1, 1,1,1},
			
			{8,1,1,1,1, 8,1,1,9,1, 1,1,1},
			{8,1,1,1,1, 1,8,1,9,9, 1,1,1},
			{8,1,1,1,1, 1,1,8,1,9, 9,1,1},
			{8,1,1,1,1, 1,1,1,8,1, 9,1,1},
			{8,1,1,1,1, 1,1,1,1,8, 1,1,1},
			
			{8,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range10 = {
			{7,7,7,7,7, 7,8,8,8,8, 8,8,8},
			{8,7,7,7,7, 1,1,1,1,1, 1,6,6},
			{8,7,7,7,7, 1,1,1,1,1, 1,6,6},
			{8,7,7,7,7, 1,6,1,1,1, 1,1,1},
			{8,7,1,1,7, 1,1,6,6,1, 1,1,1},
			
			{8,1,1,1,1, 7,1,1,6,1, 1,1,1},
			{8,1,1,1,1, 1,8,1,6,6, 1,1,1},
			{8,1,1,1,1, 1,1,8,1,6, 6,1,1},
			{8,1,1,1,1, 1,1,1,8,1, 6,1,1},
			{8,1,1,1,1, 1,1,1,1,8, 1,1,1},
			
			{8,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range11 = {
			{0,8,8,8,8, 8,8,8,8,8, 8,8,8},
			{8,0,8,8,8, 2,2,2,2,2, 2,2,2},
			{8,8,0,8,2, 2,2,2,2,2, 2,1,1},
			{8,8,8,8,2, 2,2,2,2,1, 1,1,1},
			{8,2,2,2,8, 2,2,2,2,1, 1,1,1},
			
			{8,2,2,2,2, 8,2,2,2,1, 1,1,1},
			{8,2,2,2,2, 2,8,2,2,2, 1,1,1},
			{8,2,2,1,1, 1,2,8,2,2, 1,1,1},
			{8,2,2,1,1, 1,1,2,8,2, 2,1,1},
			{8,2,1,1,1, 1,1,1,1,8, 2,1,1},
			
			{8,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range12 = {
			{0,8,8,8,8, 8,8,2,2,2, 2,2,2},
			{8,0,2,2,2, 2,2,2,2,2, 1,1,1},
			{8,2,0,2,2, 2,2,2,2,1, 1,1,1},
			{8,2,2,8,2, 2,2,2,1,1, 1,1,1},
			{8,2,2,2,8, 2,2,2,2,1, 1,1,1},
			
			{8,2,2,2,2, 8,2,2,2,1, 1,1,1},
			{8,2,2,2,2, 2,8,2,2,2, 1,1,1},
			{8,2,2,1,1, 2,2,8,2,2, 1,1,1},
			{8,2,2,1,1, 1,1,2,8,2, 2,1,1},
			{8,2,1,1,1, 1,1,1,2,8, 2,1,1},
			
			{8,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range13 = {
			{8,8,8,8,8, 8,8,8,8,8, 8,8,8},
			{8,8,8,8,8, 1,1,1,1,1, 1,1,1},
			{8,8,8,8,1, 1,1,1,1,1, 1,1,1},
			{8,8,1,8,1, 1,1,1,1,1, 1,1,1},
			{8,1,1,1,8, 1,1,1,1,1, 1,1,1},
			
			{8,1,1,1,1, 8,1,1,1,1, 1,1,1},
			{8,1,1,1,1, 1,8,1,1,1, 1,1,1},
			{8,1,1,1,1, 1,1,8,1,1, 1,1,1},
			{8,1,1,1,1, 1,1,1,8,1, 1,1,1},
			{1,1,1,1,1, 1,1,1,1,8, 1,1,1},
			
			{1,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{1,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{1,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range14 = {
			{0,8,8,8,8, 8,8,8,8,8, 8,8,8},
			{8,0,2,2,2, 1,1,1,1,1, 1,1,1},
			{8,2,0,2,2, 1,1,1,1,1, 1,1,1},
			{8,2,2,8,2, 1,1,1,1,1, 1,1,1},
			{8,2,1,1,8, 1,1,1,1,1, 1,1,1},
			
			{8,1,1,1,1, 8,1,1,1,1, 1,1,1},
			{8,1,1,1,1, 1,8,1,1,1, 1,1,1},
			{8,1,1,1,1, 1,1,8,1,1, 1,1,1},
			{8,1,1,1,1, 1,1,1,8,1, 1,1,1},
			{8,1,1,1,1, 1,1,1,1,8, 1,1,1},
			
			{1,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{1,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{1,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range15 = {
			{0,8,8,8,8, 8,8,8,8,8, 8,8,8},
			{8,0,2,2,2, 1,1,1,1,1, 1,1,1},
			{8,2,0,2,2, 2,1,1,1,1, 1,1,1},
			{8,2,2,8,2, 2,1,1,1,1, 1,1,1},
			{8,2,2,2,8, 2,2,1,1,1, 1,1,1},
			
			{8,1,1,1,1, 8,2,1,1,1, 1,1,1},
			{1,1,1,1,1, 1,8,2,1,1, 1,1,1},
			{1,1,1,1,1, 1,1,8,2,1, 1,1,1},
			{1,1,1,1,1, 1,1,1,8,1, 1,1,1},
			{1,1,1,1,1, 1,1,1,1,8, 1,1,1},
			
			{1,1,1,1,1, 1,1,1,1,1, 8,1,1},
			{1,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{1,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range16 = {
			{22,28,28,28,28, 28,28,28,28,28, 22,22, 8},
			{28,22,28,28,28, 22,22,22,10,10, 10, 8,10},
			{28,28,22,22,22, 22,10,10,10,10, 10,10,10},
			{28,22, 8,28,22, 10,10, 8, 8,10, 10,10,10},
			{8 ,28,10,10,28, 22,10,10,10,10, 10,10,10},
			
			{8,  8,10,10,10, 28,10,10,10,10, 10,10,10},
			{8, 10,10,10,10, 10,28,10,10,10, 10,21,21},
			{8, 10,10,21,21, 21,21,28, 8, 8, 10,21,21},
			{8, 10,10,21,21, 21,21,21,28, 8, 10,21,21},
			{8, 10,21,21,21, 21,21,21,21,28,  8,10,21},
			
			{8, 10,21,21,21, 21,21,21,21,21,  8,10,21},
			{8, 21,21,21,21, 21, 1,21,21,21, 21, 8,21},
			{8, 21,21,21,21, 21, 1, 1, 1, 1,  1, 1, 8},
			};
	
	int[][] range17 = {
			{4,4,4,4,4, 4,4,8,8,8, 8,8,8},
			{8,4,4,4,3, 3,2,2,2,2, 2,2,2},
			{8,8,4,3,3, 3,2,2,2,2, 2,2,2},
			{8,8,8,8,3, 3,2,2,2,2, 2,1,1},
			{8,8,8,8,8, 3,2,2,2,2, 1,1,1},
			
			{8,2,2,2,2, 8,2,2,2,2, 1,1,1},
			{8,2,2,2,2, 2,8,2,2,2, 1,1,1},
			{8,2,2,2,2, 2,2,8,2,2, 1,1,1},
			{8,2,2,2,2, 2,2,2,8,2, 2,2,1},
			{8,2,2,1,1, 1,2,2,2,8, 2,2,1},
			
			{8,2,1,1,1, 1,1,2,2,2, 8,2,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	
	int[][] range18 = {
			{4,4,4,4,4, 4,3,3,3,3, 3,3,3},
			{8,4,3,3,3, 3,3,3,3,2, 2,2,2},
			{8,3,4,3,3, 3,3,3,2,2, 2,2,2},
			{8,3,3,8,3, 3,3,3,2,2, 2,1,1},
			{8,3,3,3,8, 3,3,3,2,2, 1,1,1},
			
			{8,3,3,3,3, 8,3,3,2,2, 1,1,1},
			{8,3,2,2,3, 3,8,3,3,2, 1,1,1},
			{3,2,2,2,2, 2,2,8,3,3, 2,1,1},
			{3,2,2,2,2, 2,2,2,8,3, 2,2,1},
			{3,2,2,1,1, 1,2,2,2,8, 3,2,1},
			
			{3,2,1,1,1, 1,1,2,2,2, 8,2,1},
			{3,1,1,1,1, 1,1,1,1,1, 1,8,1},
			{3,1,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	int[][] range19 = {
			{7,7,7,8,8, 8,8,8,8,8, 8,8,8},
			{8,7,7,7,2, 2,2,2,2,2, 2,2,2},
			{8,8,7,7,2, 2,2,2,2,2, 2,2,2},
			{8,2,2,7,2, 2,2,2,2,2, 2,2,2},
			{8,2,2,2,7, 2,2,2,2,2, 2,1,1},
			
			{8,2,2,2,2, 8,2,2,2,2, 1,1,1},
			{8,2,2,2,2, 2,8,2,2,2, 1,1,1},
			{8,2,2,2,1, 1,2,8,2,2, 2,1,1},
			{8,2,2,1,1, 1,1,1,8,1, 2,1,1},
			{8,2,2,1,1, 1,1,1,1,8, 2,1,1},
			
			{8,2,2,1,1, 1,1,1,1,1, 8,1,1},
			{8,2,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,2,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	
	
	int[][] range20 = {
			{7,7,7,8,8, 8,8,8,8,8, 8,8,8},
			{8,7,7,2,2, 2,2,2,2,2, 2,2,2},
			{8,2,7,2,2, 2,2,2,2,2, 2,2,2},
			{8,2,2,7,2, 2,2,2,2,2, 2,2,2},
			{8,2,2,2,7, 2,2,2,2,2, 2,2,2},
			
			{8,2,2,2,2, 7,2,2,2,2, 1,1,1},
			{8,2,2,2,2, 2,8,2,2,2, 2,1,1},
			{8,2,2,2,2, 2,2,8,2,6, 6,1,1},
			{8,2,2,2,2, 1,2,2,8,6, 6,2,1},
			{8,2,2,1,1, 1,1,2,2,8, 2,2,2},
			
			{8,2,2,1,1, 1,1,1,1,1, 8,2,2},
			{8,2,1,1,1, 1,1,1,1,1, 1,8,1},
			{8,2,1,1,1, 1,1,1,1,1, 1,1,8},
			};
	

	
}

//1 fold
//2 call
//3 min r/f
//4 min r/c
//5 raise 2.5/c
//9 raise 2.5/f
//6 raise 3x/f
//7 raise 3x/c
//8 all in
//0 3-5bb
//10 L/CALL(FOLDvALLIN)





