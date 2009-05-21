package ged.editpath.editoperation;

import ged.graph.DecoratedNode;

import java.math.BigDecimal;

/**
 * Node insertion edit operation.
 * 
 * @author Roman Tekhov
 */
public class NodeInsertion extends EditOperation {
	
	private DecoratedNode insertedNode;

		
	public NodeInsertion(DecoratedNode insertedNode, CostContainer costContainer) {
		super(costContainer);
		
		this.insertedNode = insertedNode;
	}

	
	public DecoratedNode getInsertedNode() {
		return insertedNode;
	}

	public void setInsertedNode(DecoratedNode insertedNode) {
		this.insertedNode = insertedNode;
	}


	@Override
	protected BigDecimal doGetCost(CostContainer costContainer) {
		return costContainer.getNodeInsertionCost();
	}
	
	
	@Override
	public String toString() {
		return new StringBuilder("Insertion of node ").
			append(insertedNode.getLabel()).
			append(" (").append(getCost()).append(")").
			toString();
	}
	
}
