package ged.editpath.editoperation;

import ged.graph.DecoratedNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EdgeInsertion extends EditOperation {
	
	private final List<DecoratedNode> insertedEdgeNodes = new ArrayList<DecoratedNode>(2);
	
	
	public EdgeInsertion(DecoratedNode first, DecoratedNode second, 
			CostContainer costContainer) {
		super(costContainer);
		
		insertedEdgeNodes.add(first);
		insertedEdgeNodes.add(second);
	}

	
	public List<DecoratedNode> getAddedEdgeNodes() {
		return insertedEdgeNodes;
	}

	
	@Override
	protected BigDecimal doGetCost(CostContainer costContainer) {
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
