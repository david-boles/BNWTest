package space.davidboles.bnwtest.objects;

import java.util.Random;

import space.davidboles.bnwtest.StringConstructingObject;
import space.davidboles.lib.database.AttributeList;

public class TestIDGen extends StringConstructingObject {

	static final Random random = new Random();
	static final String range = "qwertyuiopasdfghjklzxcvbnm";
	public static final int ID_LENGTH = 32;
	
	public TestIDGen(AttributeList list, String aIDPrefix) {
		super(list, aIDPrefix);
	}

	@Override
	protected String construct(AttributeList list, String aIDPrefix) {
		String newRand = this.genRand();
		while(list.getAttribute(aIDPrefix + newRand) != null) newRand = this.genRand();
		return newRand;
	}
	
	String genRand() {
		String out = "";
		for(int i = 0; i < ID_LENGTH; i++) {
			int rand = random.nextInt(range.length());
			out += range.charAt(rand);
		}
		return out;
	}

}
