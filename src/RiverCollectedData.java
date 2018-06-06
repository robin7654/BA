import java.util.HashMap;
import java.util.Map;


public class RiverCollectedData {
	
	private int action;
	private int cardCombination;
	private int playerBB;
	private int highestBoardCard;
	private int wasRaisedBySomeoneElse;
	private int highestBoardCardIsInHandCombination;
	private int potSize;
	

	
	static Map<RiverCollectedData, RewardEntry> map = new HashMap<RiverCollectedData, RewardEntry>();
	
	public int getAction() { return action; }
	public int getcardCombination() { return cardCombination; }
	public int getPlayerBB() { return playerBB; }
	public int getHighestBoardCard() { return highestBoardCard; }
	public int getWasRaisedBySomeoneElse() { return wasRaisedBySomeoneElse; }
	public int getHighestBoardCardIsInHandCombination() { return highestBoardCardIsInHandCombination; }
	public int getPotSizeAtRiver() { return potSize; }

	
	public RiverCollectedData(int a, int b, int c, int d, int e, int f, int g){
		this.action = a;
		this.cardCombination = b;
		this.playerBB = c;
		this.highestBoardCard = d;
		this.wasRaisedBySomeoneElse = e;
		this.highestBoardCardIsInHandCombination = f;
		this.potSize = g;
	}
	
	public RiverCollectedData() {
		
	}
	
	public void createEntry(int value, int a, int b, int c, int d, int e, int f, int g){
		try {
			
			RewardEntry rewEnt = map.get(new RiverCollectedData(a,b,c,d,e,f,g));
			rewEnt.addEntry(value);
			
			
			map.put(new RiverCollectedData(a,b,c,d,e,f,g), rewEnt);
		}catch(Exception z) {
			map.put(new RiverCollectedData(a,b,c,d,e,f,g), new RewardEntry(value));
		}
	}
	
	public RewardEntry getEntry(int a, int b, int c, int d, int e, int f, int g){
			RewardEntry mapValue = map.get(new RiverCollectedData(a,b,c,d,e,f,g));
			return mapValue;
	}



	@Override
	public boolean equals(Object obj){
		if (obj != null && obj instanceof RiverCollectedData){
			RiverCollectedData dataObj = (RiverCollectedData) obj;
			
			boolean propertyA = action == dataObj.getAction();
			boolean propertyB = cardCombination == dataObj.getcardCombination();
			boolean propertyC = playerBB == dataObj.getPlayerBB();
			boolean propertyD = highestBoardCard == dataObj.getHighestBoardCard();
			boolean propertyE = wasRaisedBySomeoneElse == dataObj.getWasRaisedBySomeoneElse();
			boolean propertyF = highestBoardCardIsInHandCombination == dataObj.getHighestBoardCardIsInHandCombination();
			boolean propertyG =	potSize == dataObj.getPotSizeAtRiver();

			return propertyA && propertyB && propertyC && propertyD && propertyE && propertyF && propertyG ;
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
		return hash; 
	}

}
