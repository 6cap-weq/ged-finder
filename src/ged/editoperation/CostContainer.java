package ged.editoperation;

/**
 * 
 * @author Roman Tekhov
 */
public class CostContainer {
	
	private static final double DEFAULT_COST = 1;
	
	private double edgeDeletionCost = DEFAULT_COST;
	private double edgeInsertionCost = DEFAULT_COST;
	private double nodeDeletionCost = DEFAULT_COST;
	private double nodeInsertionCost = DEFAULT_COST;
	private double nodeSubstitutionCost = DEFAULT_COST;
	
	
	public double getEdgeDeletionCost() {
		return edgeDeletionCost;
	}
	
	public void setEdgeDeletionCost(double edgeDeletionCost) {
		this.edgeDeletionCost = edgeDeletionCost;
	}
	
	public double getEdgeInsertionCost() {
		return edgeInsertionCost;
	}
	
	public void setEdgeInsertionCost(double edgeInsertionCost) {
		this.edgeInsertionCost = edgeInsertionCost;
	}
	
	public double getNodeDeletionCost() {
		return nodeDeletionCost;
	}
	
	public void setNodeDeletionCost(double nodeDeletionCost) {
		this.nodeDeletionCost = nodeDeletionCost;
	}
	
	public double getNodeInsertionCost() {
		return nodeInsertionCost;
	}
	
	public void setNodeInsertionCost(double nodeInsertionCost) {
		this.nodeInsertionCost = nodeInsertionCost;
	}
	
	public double getNodeSubstitutionCost() {
		return nodeSubstitutionCost;
	}
	
	public void setNodeSubstitutionCost(double nodeSubstitutionCost) {
		this.nodeSubstitutionCost = nodeSubstitutionCost;
	}

}
