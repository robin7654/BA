import java.util.HashMap;
import java.util.Map;


public class TurnCollectedData {
	
	private int action;
	private int cardCombination;
	private int playerBB;
	private int highestBoardCard;
	private int wasRaisedBySomeoneElse;
	private int highestBoardCardIsInHandCombination;
	private int potSize;
	private int flushDraw;
	private int straightDraw;
	

	
	static Map<TurnCollectedData, RewardEntry> map = new HashMap<TurnCollectedData, RewardEntry>();
	
	public int getAction() { return action; }
	public int getcardCombination() { return cardCombination; }
	public int getPlayerBB() { return playerBB; }
	public int getHighestBoardCard() { return highestBoardCard; }
	public int getWasRaisedBySomeoneElse() { return wasRaisedBySomeoneElse; }
	public int getHighestBoardCardIsInHandCombination() { return highestBoardCardIsInHandCombination; }
	public int getPotSizeAtTurn() { return potSize; }
	public int getFlushDraw() { return flushDraw; }
	public int getStraightDraw() { return straightDraw; }
	

	
	public TurnCollectedData(int a, int b, int c, int d, int e, int f, int g, int h, int i){
		this.action = a;
		this.cardCombination = b;
		this.playerBB = c;
		this.highestBoardCard = d;
		this.wasRaisedBySomeoneElse = e;
		this.highestBoardCardIsInHandCombination = f;
		this.potSize = g;
		this.flushDraw = h;
		this.straightDraw = i;
	}
	
	public TurnCollectedData() {
		
	}
	
	public void createEntry(int value, int a, int b, int c, int d, int e, int f, int g, int h, int i){
		try {
			
			RewardEntry rewEnt = map.get(new TurnCollectedData(a,b,c,d,e,f,g,h,i));
			rewEnt.addEntry(value);
			
			
			map.put(new TurnCollectedData(a,b,c,d,e,f,g,h,i), rewEnt);
		}catch(Exception z) {
			map.put(new TurnCollectedData(a,b,c,d,e,f,g,h,i), new RewardEntry(value));
		}
	}
	
	public RewardEntry getEntry(int a, int b, int c, int d, int e, int f, int g, int h, int i){
			RewardEntry mapValue = map.get(new TurnCollectedData(a,b,c,d,e,f,g,h,i));
			return mapValue;
	}



	@Override
	public boolean equals(Object obj){
		if (obj != null && obj instanceof TurnCollectedData){
			TurnCollectedData dataObj = (TurnCollectedData) obj;
			
			boolean propertyA = action == dataObj.getAction();
			boolean propertyB = cardCombination == dataObj.getcardCombination();
			boolean propertyC = playerBB == dataObj.getPlayerBB();
			boolean propertyD = highestBoardCard == dataObj.getHighestBoardCard();
			boolean propertyE = wasRaisedBySomeoneElse == dataObj.getWasRaisedBySomeoneElse();
			boolean propertyF = highestBoardCardIsInHandCombination == dataObj.getHighestBoardCardIsInHandCombination();
			boolean propertyG =	potSize == dataObj.getPotSizeAtTurn();
			boolean propertyH = flushDraw == dataObj.getFlushDraw();
			boolean propertyI = straightDraw == dataObj.getStraightDraw();

			return propertyA && propertyB && propertyC && propertyD && propertyE && propertyF && propertyG && propertyH && propertyI ;
		}
		return false ; 
	}
	
	@Override
	public int hashCode(){
		int hash = 17;
		hash = hash * 31 * action;
		hash = hash * 31 * cardCombination;
		hash = hash * 31 * playerBB;
		hash = hash * 31 * highestBoardCard;
		hash = hash * 31 * wasRaisedBySomeoneElse;
		hash = hash * 31 * highestBoardCardIsInHandCombination;			
		hash = hash * 31 * potSize;
		hash = hash * 31 * flushDraw;
		hash = hash * 31 * straightDraw;
		return hash; 
	}

}
