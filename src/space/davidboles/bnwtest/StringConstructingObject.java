package space.davidboles.bnwtest;

import java.io.Serializable;

import space.davidboles.lib.database.AttributeList;

public abstract class StringConstructingObject implements Serializable {
	
	private AttributeList list;
	private String aIDPrefix;
	
	public StringConstructingObject(AttributeList list, String aIDPrefix) {
		this.list = list;
		this.aIDPrefix = aIDPrefix;
	}
	
	@Override
	public String toString() {
		return String.copyValueOf(this.construct(this.list, this.aIDPrefix).toCharArray());
	}
	
	protected abstract String construct(AttributeList list, String aIDPrefix);
}
