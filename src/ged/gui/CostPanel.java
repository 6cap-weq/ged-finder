package ged.gui;

import ged.editpath.editoperation.CostContainer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.text.NumberFormatter;

/**
 * Cost input rendering component.
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
	private JFormattedTextField acceptanceLimitCostField;
	
	private JPanel content;
	
	
	CostPanel() {
		content = new JPanel();
		
		content.setLayout(new GridBagLayout());		
				
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(2, 5, 2, 5);
				
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		nodeInsertionCostField = initTextField("Node insertion", 1D);
		
		constraints.gridx++;
		
		edgeInsertionCostField = initTextField("Edge insertion", 1D);
		
		constraints.gridx = 0;
		constraints.gridy++;
		
		nodeDeletionCostField = initTextField("Node deletion", 1D);
		
		constraints.gridx++;
		
		edgeDeletionCostField = initTextField("Edge deletion", 1D);
		
		constraints.gridx = 0;
		constraints.gridy++;
		
		nodeSubstitutionCostField = initTextField("Node substitution", 1D);
		
		constraints.gridx++;
		
		acceptanceLimitCostField = initTextField("Acceptance limit", null);
				
		content.setBorder(BorderFactory.createTitledBorder(BorderFactory.
				createEtchedBorder(EtchedBorder.LOWERED), "Edit operation costs"));
		
		add(content);
	}
	
	
	private JFormattedTextField initTextField(String label, Double initialValue) {
		JFormattedTextField field = new JFormattedTextField(new CostNumberFormat());
		
		field.setValue(initialValue);
		field.setColumns(5);
		
		constraints.anchor = GridBagConstraints.EAST;
		
		content.add(new JLabel(label + ": "), constraints);
		
		constraints.gridx++;
		
		content.add(field, constraints);
		
		return field;
	}
	
	
	private static class CostNumberFormat extends NumberFormatter {

		private static final long serialVersionUID = 1L;
		
		
		private CostNumberFormat() {
			setValueClass(Double.class);
			setMinimum(0D);
		}
		

		@Override
		public Object stringToValue(String text) throws ParseException {
			if(text == null || "".equals(text)) {
				return null;
			}
			
			return super.stringToValue(text);
		}
	}
	
	
	CostContainer getCostContainer() {
		Number nodeInsertionCost = (Number)nodeInsertionCostField.getValue();
		Number edgeInsertionCost = (Number)edgeInsertionCostField.getValue();
		Number nodeDeletionCost = (Number)nodeDeletionCostField.getValue();
		Number edgeDeletionCost = (Number)edgeDeletionCostField.getValue();
		Number nodeSubstitutionCost = (Number)nodeSubstitutionCostField.getValue();
		Number acceptanceLimitCost = (Number)acceptanceLimitCostField.getValue();
		
		CostContainer costContainer = new CostContainer();
		
		if(nodeInsertionCost != null) {
			costContainer.setNodeInsertionCost(convertToBigDecimal(nodeInsertionCost));
		}
		
		if(edgeInsertionCost != null) {
			costContainer.setEdgeInsertionCost(convertToBigDecimal(edgeInsertionCost));
		}
		
		if(nodeDeletionCost != null) {
			costContainer.setNodeDeletionCost(convertToBigDecimal(nodeDeletionCost));
		}
		
		if(edgeDeletionCost != null) {
			costContainer.setEdgeDeletionCost(convertToBigDecimal(edgeDeletionCost));
		}
		
		if(nodeSubstitutionCost != null) {
			costContainer.setNodeSubstitutionCost(convertToBigDecimal(nodeSubstitutionCost));
		}
		
		if(acceptanceLimitCost != null) {
			costContainer.setAcceptanceLimitCost(convertToBigDecimal(acceptanceLimitCost));
		}
		
		return costContainer;
	}


	private BigDecimal convertToBigDecimal(Number value) {
		return BigDecimal.valueOf(value.doubleValue()).setScale(2);
	}
	
}
