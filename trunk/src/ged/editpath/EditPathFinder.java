package ged.editpath;

import ged.editpath.editoperation.CostContainer;
import ged.editpath.editoperation.EdgeDeletion;
import ged.editpath.editoperation.EdgeInsertion;
import ged.editpath.editoperation.EditOperation;
import ged.editpath.editoperation.NodeDeletion;
import ged.editpath.editoperation.NodeInsertion;
import ged.editpath.editoperation.NodeSubstitution;
import ged.graph.DecoratedGraph;
import ged.graph.DecoratedNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Graph edit distance finding algorithm.
 * 
 * @author Roman Tekhov
 */
public class EditPathFinder {
	
	
	/**
	 * Finds the edit path between the two given graphs considering
	 * the costs from the provided cost container.
	 * 
	 * @param from source graph
	 * @param to destination graph
	 * @param costContainer edit operation cost container
	 * 
	 * @return complete edit distance between the two graphs
	 */
	public static EditPath find(DecoratedGraph from, DecoratedGraph to, CostContainer costContainer) {
		// Priority queue for the (partial) edit paths. 
		// Paths are ordered by their total costs in ascending order.
		Queue<EditPath> open = new PriorityQueue<EditPath>();
		
		// Initial operations before the main loop.
		init(from, to, open, costContainer);
		
		// Main processing iterations.
		while(true) {
			// Retrieve the (partial) edit path with the smallest total cost from the queue.
			EditPath minimumCostPath = open.poll();
			
			// If all source graph nodes are mapped return the path as the output.
			if(minimumCostPath.isComplete()) {
				return minimumCostPath;
			}
			
			// Process with the path by extending it further.
			process(from, to, minimumCostPath, open, costContainer);
		}
	}

	
	private static void init(DecoratedGraph from, DecoratedGraph to, 
			Queue<EditPath> open, CostContainer costContainer) {
		
		// Get the first random node of the source graph
		DecoratedNode firstNode = from.getNextNode(null);
		
		// Add substitutions for the first source node and each destination node.
		for(DecoratedNode toNode : to.getNodes()) {			
			NodeEditPath substitutionNodeEditPath = new NodeEditPath();
			
			substitutionNodeEditPath.setFrom(firstNode);
			substitutionNodeEditPath.setTo(toNode);
			
			EditOperation substitution = new NodeSubstitution(firstNode, toNode, costContainer);
			substitutionNodeEditPath.addEditOperation(substitution);
			
			boolean firstNodeSelfAdjacent = firstNode.isAdjacent(firstNode);
			boolean toNodeSelfAdjacent = toNode.isAdjacent(toNode);
			
			if(firstNodeSelfAdjacent && !toNodeSelfAdjacent) {
				EditOperation edgeDeletion = new EdgeDeletion(firstNode, firstNode, costContainer);
				substitutionNodeEditPath.addEditOperation(edgeDeletion);
			} else if(!firstNodeSelfAdjacent && toNodeSelfAdjacent) {
				EditOperation edgeInsertion = new EdgeInsertion(toNode, toNode, costContainer);
				substitutionNodeEditPath.addEditOperation(edgeInsertion);
			}
			
			EditPath substitutionEditPath = new EditPath(from, to);
			substitutionEditPath.addNodeEditPath(substitutionNodeEditPath);
			open.offer(substitutionEditPath);
		}
		
		// Add deletion of the first source node.
		EditPath deletionEditPath = new EditPath(from, to);
		processNodeDeletion(from, to, firstNode, deletionEditPath, costContainer, open);
	}	
	

	private static void process(DecoratedGraph from, DecoratedGraph to, EditPath minimumCostPath,
			Queue<EditPath> open, CostContainer costContainer) {
				
		Collection<DecoratedNode> mappedFromNodes = new ArrayList<DecoratedNode>();
		Collection<DecoratedNode> mappedToNodes = new ArrayList<DecoratedNode>();
					
		for(NodeEditPath nodeEditPath : minimumCostPath.getNodeEditPaths()) {
			if(nodeEditPath.getFrom() != null) {
				mappedFromNodes.add(nodeEditPath.getFrom());
			}
			
			if(nodeEditPath.getTo() != null) {
				mappedToNodes.add(nodeEditPath.getTo());
			}
		}		
		
		Collection<DecoratedNode> unmappedToNodes = to.getRestNodes(mappedToNodes);
		
		if(mappedFromNodes.size() < from.getNodeNumber()) {
			DecoratedNode nextFromNode = from.getNextNode(mappedFromNodes);
			
			processNodeSubstitution(from, minimumCostPath, mappedFromNodes, mappedToNodes, 
					unmappedToNodes, nextFromNode, costContainer, open);
			
			processNodeDeletion(from, to, nextFromNode, minimumCostPath, costContainer, open);
		} else {
			processNodeInsertion(from, minimumCostPath, unmappedToNodes, costContainer, open);
		}
	}


	private static void processNodeSubstitution(DecoratedGraph from,
			EditPath editPath, Collection<DecoratedNode> mappedFromNodes,
			Collection<DecoratedNode> mappedToNodes, 
			Collection<DecoratedNode> unmappedToNodes, 
			DecoratedNode fromNode, CostContainer costContainer, 
			Queue<EditPath> open) {
		
		for(DecoratedNode unmappedToNode : unmappedToNodes) {
			EditPath substitutionEditPath = editPath.copy();
			
			NodeEditPath substitutionNodeEditPath = new NodeEditPath();
			
			substitutionNodeEditPath.setFrom(fromNode);
			substitutionNodeEditPath.setTo(unmappedToNode);
			
			EditOperation substitution = new NodeSubstitution(fromNode, unmappedToNode, costContainer);
			substitutionNodeEditPath.addEditOperation(substitution);
						
			for(DecoratedNode adjacentFrom : fromNode.getAdjacent()) {
				DecoratedNode adjacentTo = null;
				
				if(!adjacentFrom.equals(fromNode)) {
					if(mappedFromNodes.contains(adjacentFrom)) {
						adjacentTo = substitutionEditPath.getMapped(adjacentFrom);
					}
				} else {
					adjacentTo = unmappedToNode;
				}
								
				if(adjacentTo != null && !unmappedToNode.isAdjacent(adjacentTo)) {
					EditOperation edgeDeletion = new EdgeDeletion(fromNode, adjacentFrom, costContainer);
					substitutionNodeEditPath.addEditOperation(edgeDeletion);
				}
			}
			
			for(DecoratedNode adjacentTo : unmappedToNode.getAdjacent()) {
				DecoratedNode adjacentFrom = null;
				
				if(!adjacentTo.equals(unmappedToNode)) {
					if(mappedToNodes.contains(adjacentTo)) {
						adjacentFrom = substitutionEditPath.getMappedReverse(adjacentTo);
					}
				} else {
					adjacentFrom = fromNode;
				}
								
				if(adjacentFrom != null && !fromNode.isAdjacent(adjacentFrom)) {
					EditOperation edgeInsertion = new EdgeInsertion(unmappedToNode, adjacentTo, costContainer);
					substitutionNodeEditPath.addEditOperation(edgeInsertion);
				}
			}
			
			if(from.isDirected()) {
				for(DecoratedNode accessedByFrom : fromNode.getAccessedBy()) {
					if(mappedFromNodes.contains(accessedByFrom)) {
						DecoratedNode accessedByTo = substitutionEditPath.getMapped(accessedByFrom);
						
						if(!unmappedToNode.isAccessedBy(accessedByTo)) {
							EditOperation edgeDeletion = new EdgeDeletion(accessedByFrom, fromNode, costContainer);
							substitutionNodeEditPath.addEditOperation(edgeDeletion);
						}
					}
				}
				
				for(DecoratedNode accessedByTo : unmappedToNode.getAccessedBy()) {
					if(mappedToNodes.contains(accessedByTo)) {
						DecoratedNode accessedByFrom = substitutionEditPath.getMappedReverse(accessedByTo);
						
						if(!fromNode.isAccessedBy(accessedByFrom)) {
							EditOperation edgeInsertion = new EdgeInsertion(accessedByTo, unmappedToNode, costContainer);
							substitutionNodeEditPath.addEditOperation(edgeInsertion);
						}
					}
				}
			}
							
			substitutionEditPath.addNodeEditPath(substitutionNodeEditPath);
			open.offer(substitutionEditPath);
		}
	}

	
	private static void processNodeDeletion(DecoratedGraph from, DecoratedGraph to, 
			DecoratedNode node, EditPath deletionEditPath,
			CostContainer costContainer, Queue<EditPath> open) {
		
		deletionEditPath = deletionEditPath.copy();
		
		NodeEditPath deletionNodeEditPath = new NodeEditPath();
		
		deletionNodeEditPath.setFrom(node);
		
		EditOperation deletion = new NodeDeletion(node, costContainer);
		deletionNodeEditPath.addEditOperation(deletion);
		
		for(DecoratedNode adjucentNode : node.getAdjacent()) {
			EditOperation edgeDeletion = new EdgeDeletion(node, adjucentNode, costContainer);
			deletionNodeEditPath.addEditOperation(edgeDeletion);
		}
		
		if(from.isDirected()) {
			for(DecoratedNode accessedByNode : node.getAccessedBy()) {
				EditOperation edgeDeletion = new EdgeDeletion(accessedByNode, node, costContainer);
				deletionNodeEditPath.addEditOperation(edgeDeletion);
			}
		}
		
		deletionEditPath.addNodeEditPath(deletionNodeEditPath);
		open.offer(deletionEditPath);
	}
	
	
	private static void processNodeInsertion(DecoratedGraph from, EditPath editPath,
			Collection<DecoratedNode> unmappedToNodes, CostContainer costContainer, 
			Queue<EditPath> open) {
		
		EditPath insertionPath = editPath.copy();
		
		for(DecoratedNode unmappedToNode : unmappedToNodes) {
			NodeEditPath insertionNodeEditPath = new NodeEditPath();
			
			insertionNodeEditPath.setTo(unmappedToNode);
			
			EditOperation insertion = new NodeInsertion(unmappedToNode, costContainer);
			insertionNodeEditPath.addEditOperation(insertion);
			
			insertionPath.addNodeEditPath(insertionNodeEditPath);
			
			for(DecoratedNode adjucentNode : unmappedToNode.getAdjacent()) {
				EditOperation edgeInsertion = new EdgeInsertion(unmappedToNode, adjucentNode, costContainer);
				insertionNodeEditPath.addEditOperation(edgeInsertion);
			}
			
			if(from.isDirected()) {
				for(DecoratedNode accessedByNode : unmappedToNode.getAccessedBy()) {
					EditOperation edgeInsertion = new EdgeInsertion(accessedByNode, unmappedToNode, costContainer);
					insertionNodeEditPath.addEditOperation(edgeInsertion);
				}
			}
		}
		
		insertionPath.setComplete(true);
		open.offer(insertionPath);
	}

}
