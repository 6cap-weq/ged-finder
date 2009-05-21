package ged.editpath;

import ged.graph.DecoratedGraph;
import ged.graph.DecoratedNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A partial or complete mapping between two graphs.
 * Wraps a collection of individual {@link NodeEditPath}
 * instances. The overall cost of the edit path is the sum 
 * all node edit path costs.
 * 
 * @author Roman Tekhov
 */
public class EditPath implements Comparable<EditPath> {
		
	private DecoratedGraph from;
	private DecoratedGraph to;
	
	private List<NodeEditPath> nodeEditPaths = new ArrayList<NodeEditPath>();
	
	private BigDecimal cost;
	
	private boolean complete;
	
	private List<DecoratedNode> mappedFromNodes;
	private List<DecoratedNode> mappedToNodes;
	
	
	EditPath(DecoratedGraph from, DecoratedGraph to) {
		this.from = from;
		this.to = to;
	}
	
	
	/**
	 * @return all individual node edit paths
	 */
	public List<NodeEditPath> getNodeEditPaths() {
		return nodeEditPaths;
	}
	
	/**
	 * Adds a new individual node edit path to this edit path
	 * @param nodeEditPath
	 */
	void addNodeEditPath(NodeEditPath nodeEditPath) {
		nodeEditPaths.add(nodeEditPath);
	}
	
	
	/**
	 * @return overall cost of this edit path
	 */
	public BigDecimal getCost() {
		if(cost == null) {
			cost = new BigDecimal("0.00");
			
			for(NodeEditPath nodeEditPath : nodeEditPaths) {
				cost = cost.add(nodeEditPath.getCost()).setScale(2);
			}
		}
		
		return cost;
	}
	
	
	public int compareTo(EditPath editPath) {
		return getCost().compareTo(editPath.getCost());
	}
	
	
	/**
	 * @return a boolean value indicating whether this edit path is complete
	 * 			or not (i.e. it contains mappings for all nodes)
	 */
	boolean isComplete() {
		return complete;
	}

	void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	
	/**
	 * @return list of all mapped nodes of the source graph
	 */
	List<DecoratedNode> getMappedFromNodes() {
		if(mappedFromNodes == null) {
			mappedFromNodes = new ArrayList<DecoratedNode>();
			
			for(NodeEditPath nodeEditPath : nodeEditPaths) {
				if(nodeEditPath.getFrom() != null) {
					mappedFromNodes.add(nodeEditPath.getFrom());
				}
			}
		}
		
		return mappedFromNodes;
	}
	
	
	/**
	 * @return list of all mapped nodes of the destination graph
	 */
	List<DecoratedNode> getMappedToNodes() {
		if(mappedToNodes == null) {
			mappedToNodes = new ArrayList<DecoratedNode>();
			
			for(NodeEditPath nodeEditPath : nodeEditPaths) {
				if(nodeEditPath.getTo() != null) {
					mappedToNodes.add(nodeEditPath.getTo());
				}
			}
		}
		
		return mappedToNodes;
	}
	
	
	/**
	 * Creates and returns a copy of this edit path which
	 * contains the same set of individual node edit paths.
	 * 
	 * @return copy of this edit path
	 */
	EditPath copy() {
		EditPath copy = new EditPath(getFrom(), getTo());
		
		copy.getNodeEditPaths().addAll(getNodeEditPaths());
		
		return copy;
	}
	
	
	
	/**
	 * Gets a mapped node of the destination graph which
	 * corresponds to the given node of the source graph.
	 * 
	 * @param from source graph node
	 * 
	 * @return corresponding destination graph node or 
	 * 			<code>null</code> if not found
	 */
	public DecoratedNode getMapped(DecoratedNode from) {
		for(NodeEditPath nodeEditPath : nodeEditPaths) {
			if(nodeEditPath.getFrom() != null && 
					nodeEditPath.getFrom().equals(from)) {
				return nodeEditPath.getTo();
			}
		}
		
		return null;
	}
	
	
	/**
	 * Gets a mapped node of the source graph which
	 * corresponds to the given node of the destination 
	 * graph.
	 * 
	 * @param to destination graph node
	 * 
	 * @return corresponding source graph node or 
	 * 			<code>null</code> if not found
	 */
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
