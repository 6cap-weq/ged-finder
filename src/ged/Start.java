package ged;

import ged.gui.GUICreator;

public class Start {
	
	public static void main(String[] args) {
		Processor processor = new Processor();
		
		GUICreator.createAndShow(processor);
	}

}
