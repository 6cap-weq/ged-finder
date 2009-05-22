package ged;

import ged.gui.GUICreator;
import ged.processor.Processor;

/**
 * Application starter.
 * 
 * @author Roman Tekhov
 */
public class Start {
	
	public static void main(String[] args) {
		Processor processor = new Processor();
		
		GUICreator.createAndShow(processor);
	}

}
