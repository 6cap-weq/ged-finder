package ged.editoperation;

import ged.graph.DecoratedNode;

import java.util.HashSet;
import java.util.Set;

public class EdgeInsertion extends EditOperation {
	
	private final Set<DecoratedNode> insertedEdgeNodes = new HashSet<DecoratedNode>(2);
	
	
	public EdgeInsertion(DecoratedNode first, DecoratedNode second, 
			CostContainer costContainer) {
		super(costContainer);
		
		insertedEdgeNodes.add(first);
		insertedEdgeNodes.add(second);
	}

	
	public Set<DecoratedNode> getAddedEdgeNodes() {
		return insertedEdgeNodes;
	}

	
	@Override
	protected double doGetCost(CostContainer costContainer) {
		return costContainer.getEdgeInsertionCost();
	}

	
	@Override
	public String toString() {
		return new StringBuilder("Insertion of edge ").
			append(insertedEdgeNodes).
			append(" (").append(getCost()).append(")").
			toString();
	}
}
