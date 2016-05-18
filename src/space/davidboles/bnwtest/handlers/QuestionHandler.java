package space.davidboles.bnwtest.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import space.davidboles.bnwtest.Start;
import space.davidboles.lib.database.Attribute;
import space.davidboles.lib.ht.tp.HandlerFs;

public class QuestionHandler implements HttpHandler {
	
	public static final int numQs = 8;
	protected BasePageHandler questionPageAssembler = new BasePageHandler(Start.web, "base.html", "question.title", "base.icon.type", "base.icon.location", "base.css", "question.content", new String[][]{{"@!-css.additional-!@", "question.css"}});

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
		
		if(Start.tests.getAttribute(tID + ".test_id") == null) {//If test not found
			System.out.println("Test not found, redirecting...");
			HandlerFs.respondRedirect(arg0, HandlerFs.CODE_TEMPORARY_REDIRECT, "/init_test");//Redirect to test init
		}else {//If test found
			System.out.println("Test found, continuing...");
			if(deconstructed.length() > 0) {//If submission data
				//Save submission and redirect to next question or complete page.
				deconstructed = deconstructed.substring(1);
				int sID = Integer.decode(deconstructed);
				System.out.println("Selection: " + sID);
				
				String tRAID = tID + "." + qID;
				System.out.println("Setting " + tRAID + " to " + sID);
				Attribute<Integer> testQR = Start.tests.getAttribute(tRAID);
				testQR.setAttribute(sID);
				
				String redLoc;
				if(qID >= numQs-1) redLoc = "/complete-" + tID;
				else redLoc = "/q" + (qID+1) + "-" + tID;
				System.out.println("Redirecting to " + redLoc);
				HandlerFs.respondRedirect(arg0, HandlerFs.CODE_TEMPORARY_REDIRECT, redLoc);
			}else {//If no submission data
				//Assemble and send question page.
				int[] randCoresp = randomizeNumbering();
				
				String[][] currentData = new String[][]{
					{"@!-question_content-!@", Start.web.getAttribute("question.q"+qID).getAttribute().toString()},
					
					{"@!-pos_0_location-!@", arg0.getRequestURI().getPath() + "-" + randCoresp[0]},
					{"@!-pos_0_content-!@", Start.web.getAttribute("question.q"+qID+".a"+randCoresp[0]).getAttribute().toString()},

					{"@!-pos_1_location-!@", arg0.getRequestURI().getPath() + "-" + randCoresp[1]},
					{"@!-pos_1_content-!@", Start.web.getAttribute("question.q"+qID+".a"+randCoresp[1]).getAttribute().toString()},

					{"@!-pos_2_location-!@", arg0.getRequestURI().getPath() + "-" + randCoresp[2]},
					{"@!-pos_2_content-!@", Start.web.getAttribute("question.q"+qID+".a"+randCoresp[2]).getAttribute().toString()},

					{"@!-pos_3_location-!@", arg0.getRequestURI().getPath() + "-" + randCoresp[3]},
					{"@!-pos_3_content-!@", Start.web.getAttribute("question.q"+qID+".a"+randCoresp[3]).getAttribute().toString()},

					{"@!-pos_4_location-!@", arg0.getRequestURI().getPath() + "-" + randCoresp[4]},
					{"@!-pos_4_content-!@", Start.web.getAttribute("question.q"+qID+".a"+randCoresp[4]).getAttribute().toString()},
				};
				this.questionPageAssembler.handletimeHandle(arg0, currentData);
			}
		}
	}
	
	protected int[] randomizeNumbering() {
		Random random = new Random();
		
		ArrayList<Integer> possible = new ArrayList<>();
		possible.add(0);
		possible.add(1);
		possible.add(2);
		possible.add(3);
		possible.add(4);
		
		for(int i = 0; i < 20; i++) {
			int chosen = random.nextInt(5);
			int value = possible.get(chosen);
			possible.remove(chosen);
			possible.add(value);
		}
		
		Integer[] ints = new Integer[5];
		ints = possible.toArray(ints);
		int[] out = new int[5];
		for(int i = 0; i < out.length; i++) {
			out[i] = ints[i];
		}
		return out;
	}

}
