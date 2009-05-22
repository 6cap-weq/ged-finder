package ged.gui;

import ged.processor.Processor;

import javax.swing.SwingUtilities;

/**
 * Component encapsulating the GUI creation logic details.
 * 
 * @author Roman Tekhov
 */
public class GUICreator {
	
	public static void createAndShow(final Processor processor) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainFrame mainFrame = new MainFrame(processor);
				
				mainFrame.setVisible(true);
			}
		});
	}

}
