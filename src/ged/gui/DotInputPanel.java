package ged.gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

class DotInputPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextArea from;
	private JTextArea to;
	
	
	DotInputPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		from = new JTextArea();
		to = new JTextArea();
		
		initTextArea(from);
		add(Box.createHorizontalStrut(10));
		initTextArea(to);
				
		setBorder(BorderFactory.createTitledBorder(BorderFactory.
				createEtchedBorder(EtchedBorder.LOWERED), "Input graphs in DOT language format"));
	}
	
	
	private void initTextArea(JTextArea textArea) {
		JScrollPane areaScrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		areaScrollPane.setPreferredSize(new Dimension(300, 400));
		
		add(areaScrollPane);
	}
	
	
	
	String getFromDot() {
		return from.getText();
	}
	
	String getToDot() {
		return to.getText();
	}

}
