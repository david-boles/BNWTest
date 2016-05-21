package space.davidboles.bnwtest;

import space.davidboles.lib.database.AttributeDefaultsPopulator;
import space.davidboles.lib.database.AttributeList;

public class TestsPopulator extends AttributeDefaultsPopulator {
	
	protected final String testID;
	
	public TestsPopulator(String testID) {
		this.testID = testID;
	}
	
	@Override
	protected AttributeList getDefaultsCopy() {
		AttributeList out = new AttributeList();
		
		out.addSet("test_id", this.testID);
		out.addSet("0", -1);
		out.addSet("1", -1);
		out.addSet("2", -1);
		out.addSet("3", -1);
		out.addSet("4", -1);
		out.addSet("5", -1);
		out.addSet("6", -1);
		out.addSet("7", -1);
		
		
		return out;
	}
	
}