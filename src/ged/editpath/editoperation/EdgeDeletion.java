package ged.editpath.editoperation;

import ged.graph.DecoratedNode;
import ged.processor.CostContainer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Edge deletion edit operation.
 * 
 * @author Roman Tekhov
 */
public class EdgeDeletion extends EditOperation {
	
	private final List<DecoratedNode> deletedEdgeNodes = new ArrayList<DecoratedNode>(2);

	
	public EdgeDeletion(DecoratedNode first, DecoratedNode second, 
				CostContainer costContainer) {
		super(costContainer);
		
		deletedEdgeNodes.add(first);
		deletedEdgeNodes.add(second);
	}
	
		
	public List<DecoratedNode> getDeletedEdgeNodes() {
		return deletedEdgeNodes;
	}
	
	
	@Override
	protected BigDecimal doGetCost(CostContainer costContainer) {
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
