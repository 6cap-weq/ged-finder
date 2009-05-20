package ged.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import att.grappa.Graph;
import att.grappa.GrappaPanel;

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
