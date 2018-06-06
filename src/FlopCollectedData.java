import java.util.HashMap;
import java.util.Map;


public class FlopCollectedData {

	private int cardRating;
	private int playerBB;
	private int highestBoardCard;
	private int wasRaisedBySomeoneElse;
	private int highestBoardCardIsInHandCombination;
	private int potSizeAtFlop;
	private int flushDraw;
	private int straightDraw;
	
	
	
	static Map<CollectedData, RewardEntry> map = new HashMap<CollectedData, RewardEntry>();
	
	public int getcardRating() { return cardRating; }
	public int getPlayerBB() { return playerBB; }
	public int getHighestBoardCard() { return highestBoardCard; }
	public int getWasRaisedBySomeoneElse() { return wasRaisedBySomeoneElse; }
	public int getHighestBoardCardIsInHandCombination() { return highestBoardCardIsInHandCombination; }
	public int getPotSizeAtFlop() { return potSizeAtFlop; }
	public int getFlushDraw() { return flushDraw; }
	public int getStraightDraw() { return straightDraw; }
	

	
	public FlopCollectedData(int a, int b, int c, int d, int e, int f, int g, int h){
		this.cardRating = a;
		this.playerBB = b;
		this.highestBoardCard = c;
		this.wasRaisedBySomeoneElse = d;
		this.highestBoardCardIsInHandCombination = e;
		this.potSizeAtFlop = f;
		this.flushDraw = g;
		this.straightDraw = h;
	}
	
	public FlopCollectedData() {
		
	}
	
	public void createEntry(int value, int a, int b, int c, int d, int e, int f, int g, int h){
		try {
			
			RewardEntry rewEnt = map.get(new CollectedData(a,b,c,d,e,f,g,h));
			rewEnt.addEntry(value);
			
			
			map.put(new CollectedData(a,b,c,d,e,f,g,h), rewEnt);
		}catch(Exception z) {
			map.put(new CollectedData(a,b,c,d,e,f,g,h), new RewardEntry(value));
		}
	}
	
	public RewardEntry getEntry(int a, int b, int c, int d, int e, int f, int g, int h){
			RewardEntry mapValue = map.get(new CollectedData(a,b,c,d,e,f,g,h));
			return mapValue;
	}



	@Override
	public boolean equals(Object obj){
		if (obj != null && obj instanceof CollectedData){
			CollectedData dataObj = (CollectedData) obj;
			
			
			boolean propertyA = cardRating == dataObj.getcardRating();
			boolean propertyB = playerBB == dataObj.getPlayerBB();
			boolean propertyC = highestBoardCard == dataObj.getHighestBoardCard();
			boolean propertyD = wasRaisedBySomeoneElse == dataObj.getWasRaisedBySomeoneElse();
			boolean propertyE = highestBoardCardIsInHandCombination == dataObj.getHighestBoardCardIsInHandCombination();
			boolean propertyF =	potSizeAtFlop == dataObj.getPotSizeAtFlop();
			boolean propertyG = flushDraw == dataObj.getFlushDraw();
			boolean propertyH = straightDraw == dataObj.getStraightDraw();

			return propertyA && propertyB && propertyC && propertyD && propertyE && propertyF;
		}
		return false ; 
	}
	
	@Override
	public int hashCode(){
		int hash = 17;
		hash = hash * 31 * cardRating;
		hash = hash * 31 * playerBB;
		hash = hash * 31 * highestBoardCard;
		hash = hash * 31 * wasRaisedBySomeoneElse;
		hash = hash * 31 * highestBoardCardIsInHandCombination;			
		hash = hash * 31 * potSizeAtFlop;
		hash = hash * 31 * flushDraw;
		hash = hash * 31 * straightDraw;
		return hash; 
	}

}
