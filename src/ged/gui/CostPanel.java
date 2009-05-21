package ged.gui;

import ged.editoperation.CostContainer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.text.NumberFormatter;

/**
 * 
 * @author Roman Tekhov
 */
class CostPanel extends JPanel {

	private static final long serialVersionUID = 1L;
		
	private GridBagConstraints constraints;
	
	private JFormattedTextField nodeInsertionCostField;
	private JFormattedTextField edgeInsertionCostField; 
	private JFormattedTextField nodeDeletionCostField; 
	private JFormattedTextField edgeDeletionCostField;
	private JFormattedTextField nodeSubstitutionCostField; 
	
	private JPanel content;
	
	
	CostPanel() {
		content = new JPanel();
		
		content.setLayout(new GridBagLayout());		
				
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(2, 5, 2, 5);
				
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		nodeInsertionCostField = initTextField("Node insertion");
		
		constraints.gridx++;
		
		edgeInsertionCostField = initTextField("Edge insertion");
		
		constraints.gridx = 0;
		constraints.gridy++;
		
		nodeDeletionCostField = initTextField("Node deletion");
		
		constraints.gridx++;
		
		edgeDeletionCostField = initTextField("Edge deletion");
		
		constraints.gridx = 0;
		constraints.gridy++;
		
		nodeSubstitutionCostField = initTextField("Node substitution");
				
		content.setBorder(BorderFactory.createTitledBorder(BorderFactory.
				createEtchedBorder(EtchedBorder.LOWERED), "Edit operation costs"));
		
		add(content);
	}
	
	
	private JFormattedTextField initTextField(String label) {
		JFormattedTextField field = new JFormattedTextField(new NumberFormatter());
		
		field.setValue(1);
		field.setColumns(5);
		
		constraints.anchor = GridBagConstraints.EAST;
		
		content.add(new JLabel(label + ": "), constraints);
		
		constraints.gridx++;
		
		content.add(field, constraints);
		
		return field;
	}
	
	
	CostContainer getCostContainer() {
		Number nodeInsertionCost = (Number)nodeInsertionCostField.getValue();
		Number edgeInsertionCost = (Number)edgeInsertionCostField.getValue();
		Number nodeDeletionCost = (Number)nodeDeletionCostField.getValue();
		Number edgeDeletionCost = (Number)edgeDeletionCostField.getValue();
		Number nodeSubstitutionCost = (Number)nodeSubstitutionCostField.getValue();
		
		CostContainer costContainer = new CostContainer();
		
		if(nodeInsertionCost != null) {
			costContainer.setNodeInsertionCost(BigDecimal.valueOf(nodeInsertionCost.doubleValue()).setScale(2));
		}
		
		if(edgeInsertionCost != null) {
			costContainer.setEdgeInsertionCost(BigDecimal.valueOf(edgeInsertionCost.doubleValue()).setScale(2));
		}
		
		if(nodeDeletionCost != null) {
			costContainer.setNodeDeletionCost(BigDecimal.valueOf(nodeDeletionCost.doubleValue()).setScale(2));
		}
		
		if(edgeDeletionCost != null) {
			costContainer.setEdgeDeletionCost(BigDecimal.valueOf(edgeDeletionCost.doubleValue()).setScale(2));
		}
		
		if(nodeSubstitutionCost != null) {
			costContainer.setNodeSubstitutionCost(BigDecimal.valueOf(nodeSubstitutionCost.doubleValue()).setScale(2));
		}
		
		return costContainer;
	}
	
}
