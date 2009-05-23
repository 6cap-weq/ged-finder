package ged.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import att.grappa.Graph;
import att.grappa.GrappaPanel;

/**
 * Component for displaying the combined result graph visually.
 * 
 * Delegates to Grappa's {@link GrappaPanel} to create the graphical
 * representation.
 * 
 * @author Roman Tekhov
 */
class GraphPanel extends JPanel {

	private static final long serialVersionUID = 1L;
		
	
	GraphPanel(Graph grappaGraph) {
		JPanel graphPanel = new GrappaPanel(grappaGraph);
		
		JScrollPane scrollPane = new JScrollPane(graphPanel);
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.weightx = 1;
		
		add(scrollPane, constraints);
	}

}
