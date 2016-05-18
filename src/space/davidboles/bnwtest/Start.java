package space.davidboles.bnwtest;

import space.davidboles.bnwtest.handlers.BasePageHandler;
import space.davidboles.bnwtest.handlers.QuestionHandler;
import space.davidboles.bnwtest.handlers.TestInitHandler;
import space.davidboles.bnwtest.objects.TestIDGen;
import space.davidboles.lib.database.AttributeDefaultsPopulator;
import space.davidboles.lib.database.AttributeList;
import space.davidboles.lib.ht.tp.HTTPServerSimpleManager;
import space.davidboles.lib.ht.tp.handlers.Basic404Handler;
import space.davidboles.lib.program.ProgramFs;

public class Start {
	
	public static AttributeList web = null;
	public static AttributeList tests = null;
	
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
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		//TID GEN
		out.addSet("new_id", new TestIDGen(Start.tests, ""));
		
		return out;
	}
	
}

