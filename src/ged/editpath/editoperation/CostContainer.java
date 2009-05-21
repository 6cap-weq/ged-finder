package ged.editpath.editoperation;

import java.math.BigDecimal;

/**
 * 
 * @author Roman Tekhov
 */
public class CostContainer {
	
	private static final BigDecimal DEFAULT_COST = new BigDecimal("1.00").setScale(2);
	
	private BigDecimal edgeDeletionCost = DEFAULT_COST;
	private BigDecimal edgeInsertionCost = DEFAULT_COST;
	private BigDecimal nodeDeletionCost = DEFAULT_COST;
	private BigDecimal nodeInsertionCost = DEFAULT_COST;
	private BigDecimal nodeSubstitutionCost = DEFAULT_COST;
	
	
	public BigDecimal getEdgeDeletionCost() {
		return edgeDeletionCost;
	}
	
	public void setEdgeDeletionCost(BigDecimal edgeDeletionCost) {
		this.edgeDeletionCost = edgeDeletionCost;
	}
	
	public BigDecimal getEdgeInsertionCost() {
		return edgeInsertionCost;
	}
	
	public void setEdgeInsertionCost(BigDecimal edgeInsertionCost) {
		this.edgeInsertionCost = edgeInsertionCost;
	}
	
	public BigDecimal getNodeDeletionCost() {
		return nodeDeletionCost;
	}
	
	public void setNodeDeletionCost(BigDecimal nodeDeletionCost) {
		this.nodeDeletionCost = nodeDeletionCost;
	}
	
	public BigDecimal getNodeInsertionCost() {
		return nodeInsertionCost;
	}
	
	public void setNodeInsertionCost(BigDecimal nodeInsertionCost) {
		this.nodeInsertionCost = nodeInsertionCost;
	}
	
	public BigDecimal getNodeSubstitutionCost() {
		return nodeSubstitutionCost;
	}
	
	public void setNodeSubstitutionCost(BigDecimal nodeSubstitutionCost) {
		this.nodeSubstitutionCost = nodeSubstitutionCost;
	}

}
