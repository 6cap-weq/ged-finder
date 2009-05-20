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
	
	private Set<DecoratedNode> adjacent = new HashSet<DecoratedNode>();
	
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
	
	
	public boolean isAdjucent(DecoratedNode node) {
		return adjacent.contains(node);
	}

	
	public Node getGrappaNode() {
		return grappaNode;
	}
	
	
	@Override
	public String toString() {
		return label;
	}

}
