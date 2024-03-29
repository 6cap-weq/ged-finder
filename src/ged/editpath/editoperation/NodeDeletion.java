package ged.editpath.editoperation;

import ged.graph.DecoratedNode;
import ged.processor.CostContainer;

import java.math.BigDecimal;

/**
 * Node deletion edit operation.
 * 
 * @author Roman Tekhov
 */
public class NodeDeletion extends EditOperation {
	
	private DecoratedNode deletedNode;

	
	public NodeDeletion(DecoratedNode deletedNode, CostContainer costContainer) {
		super(costContainer);
		
		this.deletedNode = deletedNode;
	}

	
	public void setDeletedNode(DecoratedNode deletedNode) {
		this.deletedNode = deletedNode;
	}

	public DecoratedNode getDeletedNode() {
		return deletedNode;
	}
	
	
	@Override
	protected BigDecimal doGetCost(CostContainer costContainer) {
		return costContainer.getNodeDeletionCost();
	}


	@Override
	public String toString() {
		return new StringBuilder("Deletion of node ").
			append(deletedNode.getLabel()).
			append(" (").append(getCost()).append(")").
			toString();
	}
	
}
