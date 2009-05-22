package ged.gui;

import ged.processor.Processor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Computation trigger rendering and action 
 * handling component.
 * 
 * @author Roman Tekhov
 */
class ComputePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private View view;
	private Processor processor;
	
	private JButton computeButton;
	
	
	ComputePanel(View view, Processor processor) {
		this.view = view;
		this.processor = processor;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		computeButton = new JButton("Compute GED");
		computeButton.addActionListener(new ComputeButtonListener());
		
		add(Box.createHorizontalGlue());
		add(computeButton, Component.RIGHT_ALIGNMENT);
	}
	
	
	void disableComputeButton() {
		computeButton.setEnabled(false);
	}


	void enableComputeButton() {
		computeButton.setEnabled(true);
	}
	
	
	
	private class ComputeButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			GedProcessWorker worker = new GedProcessWorker(view, processor);
			worker.execute();
		}
	}

}
