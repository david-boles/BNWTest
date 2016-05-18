package space.davidboles.bnwtest.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import space.davidboles.bnwtest.Start;
import space.davidboles.lib.database.Attribute;
import space.davidboles.lib.ht.tp.HandlerFs;

public class QuestionHandler implements HttpHandler {
	
	public static final int numQs = 8;

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		System.out.println("-----NEW_Q_REQUEST-----");
		
		//Get URI path
		String deconstructed = arg0.getRequestURI().getPath();
		System.out.println(deconstructed);
		
		//Remove /q
		deconstructed = deconstructed.replaceFirst("/q", "");
		System.out.println(deconstructed);
		
		//Extract question ID and remove it and the following dash
		int qID = Integer.decode(deconstructed.substring(0, 1));
		deconstructed = deconstructed.substring(2);
		System.out.println("Question ID: " + qID);
		System.out.println(deconstructed);
		
		//Extract test ID and remove it and the following dash
		String tID = deconstructed.substring(0, TestInitHandler.ID_LENGTH);
		deconstructed = deconstructed.substring(TestInitHandler.ID_LENGTH);
		System.out.println("Test ID: " + tID);
		System.out.println(deconstructed);
		
		if(deconstructed.length() > 0) {//Save submission and redirect to next question or complete page.
			deconstructed = deconstructed.substring(1);
			int sID = Integer.decode(deconstructed);
			System.out.println("Selection: " + sID);
			
			String tRAID = tID + "." + qID;
			System.out.println("Setting " + tRAID + " to " + sID);
			Attribute<Integer> testQR = Start.tests.getAttribute(tRAID);
			if(testQR == null) HandlerFs.respondRedirect(arg0, HandlerFs.CODE_TEMPORARY_REDIRECT, "/init_test");
			testQR.setAttribute(sID);
			
			String redLoc;
			if(qID >= numQs-1) redLoc = "/complete-" + tID;
			else redLoc = "/q" + (qID+1) + "-" + tID;
			System.out.println("Redirecting to " + redLoc);
			HandlerFs.respondRedirect(arg0, HandlerFs.CODE_TEMPORARY_REDIRECT, redLoc);
		}else {//Assemble and send question page.
			
		}
	}

}
