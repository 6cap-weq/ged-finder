package ged.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import att.grappa.Graph;

/**
 * @author Roman Tekhov
 */
public class DecoratedGraph {
	
	private String name;
	private Set<DecoratedNode> nodes;
	private Graph grappaGraph;
	
	
	public DecoratedGraph(String name, Set<DecoratedNode> nodes, Graph grappaGraph) {
		this.name = name;
		this.nodes = nodes;
		this.grappaGraph = grappaGraph;
	}

	
	public Set<DecoratedNode> getNodes() {
		return nodes;
	}
	
		
	public int getNodeNumber() {
		return nodes.size();
	}
	
	
	public DecoratedNode getNextNode(Collection<DecoratedNode> nodes) {
		if(nodes != null) {
			for(DecoratedNode fromNode : nodes) {
				for(DecoratedNode adjacent : fromNode.getAdjacent()) {
					if(!nodes.contains(adjacent)) {
						return adjacent;
					}
				}
			}
		}		
		
		for(DecoratedNode node : this.nodes) {
			if(nodes == null || !nodes.contains(node)) {
				return node;
			}
		}
		
		return null;
	}
	
	
	public Collection<DecoratedNode> getRestNodes(Collection<DecoratedNode> nodes) {
		List<DecoratedNode> restNodes = new ArrayList<DecoratedNode>();
		
		for(DecoratedNode node : this.nodes) {
			if(!nodes.contains(node)) {
				restNodes.add(node);
			}
		}
		
		return restNodes;
	}


	public Graph getGrappaGraph() {
		return grappaGraph;
	}
	
	
	public boolean isDirected() {
		return grappaGraph.isDirected();
	}
	
	
	@Override
	public String toString() {
		return name + " " + nodes;
	}

}
