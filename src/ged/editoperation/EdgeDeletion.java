package ged.editoperation;

import ged.graph.DecoratedNode;

import java.util.HashSet;
import java.util.Set;

public class EdgeDeletion extends EditOperation {
	
	private final Set<DecoratedNode> deletedEdgeNodes = new HashSet<DecoratedNode>(2);

	
	public EdgeDeletion(DecoratedNode first, DecoratedNode second, 
				CostContainer costContainer) {
		super(costContainer);
		
		deletedEdgeNodes.add(first);
		deletedEdgeNodes.add(second);
	}
	
		
	public Set<DecoratedNode> getDeletedEdgeNodes() {
		return deletedEdgeNodes;
	}
	
	
	@Override
	protected double doGetCost(CostContainer costContainer) {
		return costContainer.getEdgeDeletionCost();
	}
	
	
	@Override
	public String toString() {
		return new StringBuilder("Deletion of edge ").
			append(deletedEdgeNodes).
			append(" (").append(getCost()).append(")").
			toString();
	}
}
