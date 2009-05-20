package ged.gui;

import ged.InputContainer;
import ged.Processor;
import ged.editoperation.CostContainer;
import ged.editpath.EditPath;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import att.grappa.Graph;

/**
 * 
 * @author Roman Tekhov
 */
class MainFrame extends JFrame implements View {

	private static final long serialVersionUID = 1L;
		
	private CostPanel costPanel;
	private DotInputPanel dotInputPane;
	private ComputePanel computePanel;
	
	
	MainFrame(Processor processor) {
		super("Graph Edit Distance Calculator");
								
		costPanel = new CostPanel();
		dotInputPane = new DotInputPanel();
		computePanel = new ComputePanel(this, processor);
		
		JPanel content = new JPanel(new GridBagLayout());
		getContentPane().add(content);
				
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.insets = new Insets(10, 0, 10, 0);
		
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		content.add(costPanel, constraints);
		
		constraints.gridy++;
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.BOTH;
		content.add(dotInputPane, constraints);
		
		constraints.gridy++;
		constraints.weighty = 0;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		content.add(computePanel, constraints);
				
		content.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
						
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	public InputContainer getInputContainer() {
		CostContainer costContainer = costPanel.getCostContainer();
		
		String fromDot = dotInputPane.getFromDot();
		String toDot = dotInputPane.getToDot();
		
		return new InputContainer(costContainer, fromDot, toDot);
	}


	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}


	public void showResult(Graph grappaGraph, EditPath editPath) {
		ResultFrame resultFrame = new ResultFrame(grappaGraph, editPath);
		
		resultFrame.setVisible(true);		
	}


	public void disableComputeTrigger() {
		computePanel.disableComputeButton();
	}


	public void enableComputeTrigger() {
		computePanel.enableComputeButton();
	}

}
