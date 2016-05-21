package space.davidboles.bnwtest.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import space.davidboles.bnwtest.Start;
import space.davidboles.lib.ht.tp.HandlerFs;

public class CompleteHandler implements HttpHandler {
	
	protected BasePageHandler completePageAssembler = new BasePageHandler(Start.web, "base.html", "question.title", "base.icon.type", "base.icon.location", "base.css", "complete.content", new String[][]{{"@!-css.additional-!@", "complete.css"}});

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		System.out.println("-----NEW_COMPLETE_REQUEST-----");
		
		//Get URI path
		String deconstructed = arg0.getRequestURI().getPath();
		System.out.println(deconstructed);
		
		//Remove /q
		deconstructed = deconstructed.replaceFirst("/complete-", "");
		System.out.println(deconstructed);
		
		//Extract test ID and remove it and the following dash
		String tID = deconstructed.substring(0, TestInitHandler.ID_LENGTH);
		
		if(Start.tests.getAttribute(tID + ".test_id") == null) {//If test not found
			System.out.println("Test not found, redirecting...");
			HandlerFs.respondRedirect(arg0, HandlerFs.CODE_TEMPORARY_REDIRECT, "/init_test");//Redirect to test init
		}else {//If test found/*
			System.out.println("Test found, continuing...");
			
			int[] castes = new int[5];
			for(int i = 0; i < QuestionHandler.numQs; i++) {
				int resp = (Integer) Start.tests.getAttribute(tID + "." + i).getAttribute();
				if(resp != -1) castes[resp]++;
			}
			
			//Assemble and send question page.
			String[][] currentData = new String[][]{
				{"@!-alpha_percent-!@", ((castes[0]*100.0)/QuestionHandler.numQs) + "%"},
				{"@!-beta_percent-!@", ((castes[1]*100.0)/QuestionHandler.numQs) + "%"},
				{"@!-gamma_percent-!@", ((castes[2]*100.0)/QuestionHandler.numQs) + "%"},
				{"@!-delta_percent-!@", ((castes[3]*100.0)/QuestionHandler.numQs) + "%"},
				{"@!-epsilon_percent-!@", ((castes[4]*100.0)/QuestionHandler.numQs) + "%"},
			};
			
			this.completePageAssembler.handletimeHandle(arg0, currentData);
			
		}
		
	}
	

}
