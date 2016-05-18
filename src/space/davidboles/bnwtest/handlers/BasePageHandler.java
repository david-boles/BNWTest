package space.davidboles.bnwtest.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import space.davidboles.lib.database.AttributeList;
import space.davidboles.lib.ht.tp.HandlerFs;

public class BasePageHandler implements HttpHandler {

	protected AttributeList list;
	protected String basePageID;
	protected String titleID;
	protected String iconTypeID;
	protected String iconLocationID;
	protected String cssContentID;
	protected String bodyContentID;
	protected String [][] additional;
	
	public BasePageHandler(AttributeList list, String basePageID, String titleID, String iconTypeID, String iconLocationID, String cssContentID, String bodyContentID, String[][] additional) {
		this.list = list;
		this.basePageID = basePageID;
		this.titleID = titleID;
		this.iconTypeID = iconTypeID;
		this.iconLocationID = iconLocationID;
		this.cssContentID = cssContentID;
		this.bodyContentID = bodyContentID;
		this.additional = additional;
	}
	
	public void handletimeHandle(HttpExchange arg0, String[][] handletimeAdditional) throws IOException {
		String page = list.getAttribute(this.basePageID).getAttribute().toString();
		String title = list.getAttribute(this.titleID).getAttribute().toString();
		String iconType = list.getAttribute(this.iconTypeID).getAttribute().toString();
		String iconLocation = list.getAttribute(this.iconLocationID).getAttribute().toString();
		String cssContent = list.getAttribute(this.cssContentID).getAttribute().toString();
		String bodyContent = list.getAttribute(this.bodyContentID).getAttribute().toString();
		
		page = page.replaceAll("@!-head.title-!@", title);
		page = page.replaceAll("@!-icon.type-!@", iconType);
		page = page.replaceAll("@!-icon.location-!@", iconLocation);
		page = page.replaceAll("@!-css.content-!@", cssContent);
		page = page.replaceAll("@!-body.content-!@", bodyContent);
		
		if(this.additional != null) {
			for (int set = 0; set < this.additional.length; set++) {
				String data = list.getAttribute(this.additional[set][1]).getAttribute().toString();
				page = page.replaceAll(this.additional[set][0], data);
			}
		}
		
		if(handletimeAdditional != null) {
			for (int set = 0; set < handletimeAdditional.length; set++) {
				String data = handletimeAdditional[set][1];
				page = page.replaceAll(handletimeAdditional[set][0], data);
			}
		}
		
		//System.out.println("----PAGE----");
		//System.out.println(page);
		
		HandlerFs.respondHTML(arg0, 200, page);
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		handletimeHandle(arg0, null);
	}
}
