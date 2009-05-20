package ged.editpath;

import ged.graph.DecoratedGraph;
import ged.graph.DecoratedNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditPath implements Comparable<EditPath> {
		
	private DecoratedGraph from;
	private DecoratedGraph to;
	
	private List<NodeEditPath> nodeEditPaths = new ArrayList<NodeEditPath>();
	
	private Double cost;
	
	private boolean complete;
	
	private Set<DecoratedNode> mappedFromNodes;
	private Set<DecoratedNode> mappedToNodes;
	
	
	EditPath(DecoratedGraph from, DecoratedGraph to) {
		this.from = from;
		this.to = to;
	}
	
	
	public List<NodeEditPath> getNodeEditPaths() {
		return nodeEditPaths;
	}

	void addNodeEditPath(NodeEditPath nodeEditPath) {
		nodeEditPaths.add(nodeEditPath);
	}
	
	
	public double getCost() {
		if(cost == null) {
			cost = 0.0;
			
			for(NodeEditPath nodeEditPath : nodeEditPaths) {
				cost += nodeEditPath.getCost();
			}
		}
		
		return cost;
	}
	
	
	public int compareTo(EditPath editPath) {
		return Double.compare(getCost(), editPath.getCost());
	}
	

	boolean isComplete() {
		return complete;
	}

	void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	
	Set<DecoratedNode> getMappedFromNodes() {
		if(mappedFromNodes == null) {
			mappedFromNodes = new HashSet<DecoratedNode>();
			
			for(NodeEditPath nodeEditPath : nodeEditPaths) {
				if(nodeEditPath.getFrom() != null) {
					mappedFromNodes.add(nodeEditPath.getFrom());
				}
			}
		}
		
		return mappedFromNodes;
	}
	
	
	Set<DecoratedNode> getMappedToNodes() {
		if(mappedToNodes == null) {
			mappedToNodes = new HashSet<DecoratedNode>();
			
			for(NodeEditPath nodeEditPath : nodeEditPaths) {
				if(nodeEditPath.getTo() != null) {
					mappedToNodes.add(nodeEditPath.getTo());
				}
			}
		}
		
		return mappedToNodes;
	}
	
	
	EditPath copy() {
		EditPath copy = new EditPath(getFrom(), getTo());
		
		copy.getNodeEditPaths().addAll(getNodeEditPaths());
		
		return copy;
	}
	
	
	public DecoratedNode getMapped(DecoratedNode from) {
		for(NodeEditPath nodeEditPath : nodeEditPaths) {
			if(nodeEditPath.getFrom() != null && 
					nodeEditPath.getFrom().equals(from)) {
				return nodeEditPath.getTo();
			}
		}
		
		return null;
	}
	
	public DecoratedNode getMappedReverse(DecoratedNode to) {
		for(NodeEditPath nodeEditPath : nodeEditPaths) {
			if(nodeEditPath.getTo() != null && 
					nodeEditPath.getTo().equals(to)) {
				return nodeEditPath.getFrom();
			}
		}
		
		return null;
	}

	
	
	public DecoratedGraph getFrom() {
		return from;
	}

	public DecoratedGraph getTo() {
		return to;
	}


	
	@Override
	public String toString() {		
		return new StringBuilder("Edit path from '").
			append(from.getGrappaGraph().getName()).
			append("' to '").append(to.getGrappaGraph().getName()).
			append("' (").append(getCost()).append(")").
			toString();
	}
		
}
