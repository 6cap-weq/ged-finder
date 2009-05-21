package ged.editpath;

import ged.editpath.editoperation.EditOperation;
import ged.graph.DecoratedNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NodeEditPath {
	
	private DecoratedNode from;
	private DecoratedNode to;
	
	private List<EditOperation> editOperations = new ArrayList<EditOperation>();
	
	private BigDecimal cost;
	
	
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


	public List<EditOperation> getEditOperations() {
		return editOperations;
	}

	void setEditOperations(List<EditOperation> editOperations) {
		this.editOperations = editOperations;
	}
	
	void addEditOperation(EditOperation editOperation) {
		editOperations.add(editOperation);
	}
	
	
	public BigDecimal getCost() {
		if(cost == null) {
			cost = new BigDecimal("0.00");
			
			for(EditOperation editOperation : editOperations) {
				cost = cost.add(editOperation.getCost()).setScale(2);
			}
		}
		
		return cost;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(from == null && to != null) {
			sb.append("Insertion of node '").
				append(to.getLabel());
		} else if(to == null && from != null) {
			sb.append("Deletion of node '").
			append(from.getLabel());
		} else if(from != null && to != null) {
			sb.append("Substitution of nodes '").
				append(from.getLabel()).
				append("' and '").
				append(to.getLabel());
		}
		
		return sb.append("' (").append(getCost()).append(")").
			toString();
	}

}
