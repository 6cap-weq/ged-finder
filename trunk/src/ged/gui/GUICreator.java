package ged.gui;

import javax.swing.SwingUtilities;

/**
 * Component encapsulating the GUI creation logic details.
 * 
 * @author Roman Tekhov
 */
public class GUICreator {
	
	public static void createAndShow() {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainFrame mainFrame = new MainFrame();
				
				mainFrame.setVisible(true);
			}
		});
	}

}
