package ged.graph;

import java.util.HashSet;
import java.util.Set;

import att.grappa.Node;

/**
 * 
 * @author Roman Tekhov
 */
public class DecoratedNode {

	private String label;
	
	private final Set<DecoratedNode> adjacent = new HashSet<DecoratedNode>();
	
	private final Set<DecoratedNode> accessedBy = new HashSet<DecoratedNode>();
	
	private Node grappaNode;
	
	
	public DecoratedNode(Node grappaNode) {
		this.grappaNode = grappaNode;
		
		label = grappaNode.getName();
	}

	
	public String getLabel() {
		return label;
	}

	public Set<DecoratedNode> getAdjacent() {
		return adjacent;
	}
	
	public void addAdjacent(DecoratedNode node) {
		adjacent.add(node);
	}
	
	public boolean isAdjacent(DecoratedNode node) {
		return adjacent.contains(node);
	}
	
	
	
	public Set<DecoratedNode> getAccessedBy() {
		return accessedBy;
	}
	
	public void addAccessedBy(DecoratedNode node) {
		accessedBy.add(node);
	}
	
	public boolean isAccessedBy(DecoratedNode node) {
		return accessedBy.contains(node);
	}	

	
	public Node getGrappaNode() {
		return grappaNode;
	}
	
	
	@Override
	public String toString() {
		return label;
	}

}
