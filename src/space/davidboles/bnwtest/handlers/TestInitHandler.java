package space.davidboles.bnwtest.handlers;

import java.io.IOException;
import java.util.Random;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import space.davidboles.bnwtest.Start;
import space.davidboles.bnwtest.TestsPopulator;

public class TestInitHandler implements HttpHandler {

	static final Random random = new Random();
	static final String range = "qwertyuiopasdfghjklzxcvbnm";
	public static final int ID_LENGTH = 32;
	
	@Override
	public void handle(HttpExchange t) throws IOException {
		String testID = getSafeRand();
		t.getResponseHeaders().set("Location", "/q0-" + testID);
		t.sendResponseHeaders(307, 0);
		
		TestsPopulator testPop = new TestsPopulator(testID);
		testPop.populateDefaults(Start.tests, testID+".");
	}
	
	

	protected String getSafeRand() {
		String newRand = this.genRand();
		while(Start.tests.getAttribute(newRand) != null) newRand = this.genRand();
		return newRand;
	}
	
	protected String genRand() {
		String out = "";
		for(int i = 0; i < ID_LENGTH; i++) {
			int rand = random.nextInt(range.length());
			out += range.charAt(rand);
		}
		return out;
	}

}
