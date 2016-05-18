package space.davidboles.bnwtest;

import java.io.File;
import java.io.Serializable;

import space.davidboles.lib.program.ProgramFs;

public class StringLoadingObject implements Serializable {
	protected File file;
	protected String fileContents = null;
	protected long timeToUpdate = 0;
	protected long lastUpdate = 0;
	protected boolean nullOnFail = false;
	
	public StringLoadingObject(File file, long timeToUpdate, boolean nullOnFail) {
		this.file = file;
		this.nullOnFail = nullOnFail;
		this.timeToUpdate = timeToUpdate;
		updateContentsIfDesired();
	}
	
	@Override
	public String toString() {
		updateContentsIfDesired();
		return String.copyValueOf(this.fileContents.toCharArray());
	}
	
	protected void updateContentsIfDesired() {
		if(this.lastUpdate + this.timeToUpdate < System.currentTimeMillis()) {
			this.updateContents();
		}
	}
	
	protected void updateContents() {
		this.fileContents = ProgramFs.loadString(file);
		if(!this.nullOnFail) this.fileContents = "";
		this.lastUpdate = System.currentTimeMillis();
	}
}
