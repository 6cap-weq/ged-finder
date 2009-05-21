package ged.editoperation;

import ged.StringEditDistance;
import ged.graph.DecoratedNode;

public class NodeSubstitution extends EditOperation {
	
	private DecoratedNode from;
	
	private DecoratedNode to;
	
	
	public NodeSubstitution(DecoratedNode from, DecoratedNode to, CostContainer costContainer) {
		super(costContainer);
		
		this.from = from;
		this.to = to;
	}

	
	public DecoratedNode getFrom() {
		return from;
	}

	public void setFrom(DecoratedNode from) {
		this.from = from;
	}

	
	public DecoratedNode getTo() {
		return to;
	}

	public void setTo(DecoratedNode to) {
		this.to = to;
	}
	
	
	@Override
	protected double doGetCost(CostContainer costContainer) {
		double coefficient = StringEditDistance.calculateCoefficient(from.getLabel(), to.getLabel());
		
		return costContainer.getNodeSubstitutionCost() * coefficient;
	}
	
	
	@Override
	public String toString() {
		return new StringBuilder("Substitution of node ").
			append(from.getLabel()).
			append(" with node ").append(to.getLabel()).
			append(" (").append(getCost()).append(")").
			toString();
	}

}