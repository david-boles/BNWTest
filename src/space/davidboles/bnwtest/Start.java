package space.davidboles.bnwtest;

import java.util.Scanner;

import space.davidboles.bnwtest.handlers.BasePageHandler;
import space.davidboles.bnwtest.handlers.CompleteHandler;
import space.davidboles.bnwtest.handlers.QuestionHandler;
import space.davidboles.bnwtest.handlers.TestInitHandler;
import space.davidboles.lib.database.Attribute;
import space.davidboles.lib.database.AttributeDefaultsPopulator;
import space.davidboles.lib.database.AttributeList;
import space.davidboles.lib.ht.tp.HTTPServerSimpleManager;
import space.davidboles.lib.ht.tp.handlers.Basic404Handler;
import space.davidboles.lib.program.ProgramFs;

public class Start {
	
	public static AttributeList web = null;
	public static AttributeList tests = null;
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			HTTPServerSimpleManager s = new HTTPServerSimpleManager(6437);
			
			web = (AttributeList) ProgramFs.loadObject(ProgramFs.getProgramFile("data/web"));
			tests = (AttributeList) ProgramFs.loadObject(ProgramFs.getProgramFile("data/tests"));
			if(web == null) web = new AttributeList();
			if(tests == null) tests = new AttributeList();
			WebPopulator webPop = new WebPopulator();
			webPop.populateDefaults(web);
			
			//s.createContext("/", new AttributeListHandler(web, "/", "", new Basic404Handler()));
			s.createContext("/", new Basic404Handler());
			s.createContext("/hello", new BasePageHandler(web, "base.html", "base.title", "base.icon.type", "base.icon.location", "base.css", "base.content", null));
			s.createContext("/start", new BasePageHandler(web, "base.html", "welcome.title", "base.icon.type", "base.icon.location", "base.css", "welcome.content", new String[][]{{"@!-css.additional-!@", "welcome.css"}}));
			s.createContext("/init_test", new TestInitHandler());
			s.createContext("/q", new QuestionHandler());
			s.createContext("/complete", new CompleteHandler());
			
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						System.out.println("-----SAVING_ALs-----");
						saveALs();
						try {
							Thread.sleep(1200000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
			
			while(!scanner.nextLine().equals("stop"));

			System.out.println("-----SAVING_ALs-----");
			saveALs();
			System.out.println("-----STOPPING_SERVER-----");
			s.stopServer();
			System.out.println("-----EXITING-----");
			System.exit(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printTests() {
		Attribute<?>[] all = Start.tests.getAll();
		for(int i = 0; i < all.length; i++) {
			System.out.println(all[i]);
		}
	}
	
	public static void saveALs() {
		boolean successful = ProgramFs.saveObject(ProgramFs.getProgramFile("data/web"), web) && ProgramFs.saveObject(ProgramFs.getProgramFile("data/tests"), tests);
		if(successful) System.out.println("Save successful");
		else System.out.println("Save failed, errors should have printed");
	}
}

class WebPopulator extends AttributeDefaultsPopulator {

	@Override
	protected AttributeList getDefaultsCopy() {
		AttributeList out = new AttributeList();
		
		//BASE
		out.addSet("base.html", new StringLoadingObject(ProgramFs.getProgramFile("webAs/base.html"), 1000, true));
		out.addSet("base.title", "Howdy");
		out.addSet("base.icon.type", "image/png");
		out.addSet("base.icon.location", "/");
		out.addSet("base.css", new StringLoadingObject(ProgramFs.getProgramFile("webAs/base.css"), 1000, true));
		out.addSet("base.content", "Hello World!");
		
		//WELCOME
		out.addSet("welcome.title", "Start BNW Caste Test!");
		out.addSet("welcome.content", new StringLoadingObject(ProgramFs.getProgramFile("webAs/welcome.html"), 1000, true));
		out.addSet("welcome.css", new StringLoadingObject(ProgramFs.getProgramFile("webAs/welcome.css"), 1000, true));
		
		//QUESTIONS
		out.addSet("question.title", "BNW Caste Test Question");
		out.addSet("question.content", new StringLoadingObject(ProgramFs.getProgramFile("webAs/question.html"), 1000, true));
		out.addSet("question.css", new StringLoadingObject(ProgramFs.getProgramFile("webAs/question.css"), 1000, true));
		
		out.addSet("question.q0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q0/q0text.html"), 1000, true));
		out.addSet("question.q0.a0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q0/q0answer0text.html"), 1000, true));
		out.addSet("question.q0.a1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q0/q0answer1text.html"), 1000, true));
		out.addSet("question.q0.a2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q0/q0answer2text.html"), 1000, true));
		out.addSet("question.q0.a3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q0/q0answer3text.html"), 1000, true));
		out.addSet("question.q0.a4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q0/q0answer4text.html"), 1000, true));
		
		out.addSet("question.q1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q1/q1text.html"), 1000, true));
		out.addSet("question.q1.a0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q1/q1answer0text.html"), 1000, true));
		out.addSet("question.q1.a1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q1/q1answer1text.html"), 1000, true));
		out.addSet("question.q1.a2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q1/q1answer2text.html"), 1000, true));
		out.addSet("question.q1.a3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q1/q1answer3text.html"), 1000, true));
		out.addSet("question.q1.a4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q1/q1answer4text.html"), 1000, true));
		
		out.addSet("question.q2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q2/q2text.html"), 1000, true));
		out.addSet("question.q2.a0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q2/q2answer0text.html"), 1000, true));
		out.addSet("question.q2.a1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q2/q2answer1text.html"), 1000, true));
		out.addSet("question.q2.a2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q2/q2answer2text.html"), 1000, true));
		out.addSet("question.q2.a3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q2/q2answer3text.html"), 1000, true));
		out.addSet("question.q2.a4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q2/q2answer4text.html"), 1000, true));
		
		out.addSet("question.q3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q3/q3text.html"), 1000, true));
		out.addSet("question.q3.a0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q3/q3answer0text.html"), 1000, true));
		out.addSet("question.q3.a1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q3/q3answer1text.html"), 1000, true));
		out.addSet("question.q3.a2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q3/q3answer2text.html"), 1000, true));
		out.addSet("question.q3.a3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q3/q3answer3text.html"), 1000, true));
		out.addSet("question.q3.a4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q3/q3answer4text.html"), 1000, true));
		
		out.addSet("question.q4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q4/q4text.html"), 1000, true));
		out.addSet("question.q4.a0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q4/q4answer0text.html"), 1000, true));
		out.addSet("question.q4.a1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q4/q4answer1text.html"), 1000, true));
		out.addSet("question.q4.a2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q4/q4answer2text.html"), 1000, true));
		out.addSet("question.q4.a3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q4/q4answer3text.html"), 1000, true));
		out.addSet("question.q4.a4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q4/q4answer4text.html"), 1000, true));
		
		out.addSet("question.q5", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q5/q5text.html"), 1000, true));
		out.addSet("question.q5.a0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q5/q5answer0text.html"), 1000, true));
		out.addSet("question.q5.a1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q5/q5answer1text.html"), 1000, true));
		out.addSet("question.q5.a2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q5/q5answer2text.html"), 1000, true));
		out.addSet("question.q5.a3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q5/q5answer3text.html"), 1000, true));
		out.addSet("question.q5.a4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q5/q5answer4text.html"), 1000, true));
		
		out.addSet("question.q6", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q6/q6text.html"), 1000, true));
		out.addSet("question.q6.a0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q6/q6answer0text.html"), 1000, true));
		out.addSet("question.q6.a1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q6/q6answer1text.html"), 1000, true));
		out.addSet("question.q6.a2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q6/q6answer2text.html"), 1000, true));
		out.addSet("question.q6.a3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q6/q6answer3text.html"), 1000, true));
		out.addSet("question.q6.a4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q6/q6answer4text.html"), 1000, true));
		
		out.addSet("question.q7", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q7/q7text.html"), 1000, true));
		out.addSet("question.q7.a0", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q7/q7answer0text.html"), 1000, true));
		out.addSet("question.q7.a1", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q7/q7answer1text.html"), 1000, true));
		out.addSet("question.q7.a2", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q7/q7answer2text.html"), 1000, true));
		out.addSet("question.q7.a3", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q7/q7answer3text.html"), 1000, true));
		out.addSet("question.q7.a4", new StringLoadingObject(ProgramFs.getProgramFile("webAs/q7/q7answer4text.html"), 1000, true));
		
		//COMPLETE
		out.addSet("complete.title", "BNW Caste Test Results");
		out.addSet("complete.content", new StringLoadingObject(ProgramFs.getProgramFile("webAs/complete.html"), 1000, true));
		out.addSet("complete.css", new StringLoadingObject(ProgramFs.getProgramFile("webAs/complete.css"), 1000, true));
		
		return out;
	}
	
}

