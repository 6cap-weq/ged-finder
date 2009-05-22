package ged.gui;

import ged.editpath.EditPath;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import att.grappa.Graph;

/**
 * Output displaying window.
 * 
 * @author Roman Tekhov
 */
class ResultFrame extends JFrame {

	private static final long serialVersionUID = 1L;
		
	ResultFrame(Graph grappaGraph, EditPath editPath) {
		super("Result");
				
		GraphPanel graphPanel = new GraphPanel(grappaGraph);
		EditOperationsPanel editOperationsPanel = new EditOperationsPanel(editPath);
		
		Dimension preferedSize = new Dimension(400, 700);
		graphPanel.setPreferredSize(preferedSize);
		editOperationsPanel.setPreferredSize(preferedSize);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				graphPanel, editOperationsPanel);
		
		splitPane.setResizeWeight(0.5);
		
		add(splitPane);
		
		pack();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
