package ged.editpath;

import ged.editoperation.CostContainer;
import ged.editoperation.EdgeDeletion;
import ged.editoperation.EdgeInsertion;
import ged.editoperation.EditOperation;
import ged.editoperation.NodeDeletion;
import ged.editoperation.NodeInsertion;
import ged.editoperation.NodeSubstitution;
import ged.graph.DecoratedGraph;
import ged.graph.DecoratedNode;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class EditPathFinder {
	
	
	public static EditPath find(DecoratedGraph from, DecoratedGraph to, CostContainer costContainer) {
		NavigableSet<EditPath> open = new TreeSet<EditPath>();
		
		init(from, to, open, costContainer);
		
		while(true) {
			EditPath minimumCostPath = open.pollFirst();
			
			if(minimumCostPath.isComplete()) {
				return minimumCostPath;
			}
			
			process(from, to, minimumCostPath, open, costContainer);
		}
	}

	

	private static void init(DecoratedGraph from, DecoratedGraph to, 
			NavigableSet<EditPath> open, CostContainer costContainer) {
		
		DecoratedNode firstNode = from.getNextNode(null);
		for(DecoratedNode toNode : to.getNodes()) {			
			NodeEditPath substitutionNodeEditPath = new NodeEditPath();
			
			substitutionNodeEditPath.setFrom(firstNode);
			substitutionNodeEditPath.setTo(toNode);
			
			EditOperation substitution = new NodeSubstitution(firstNode, toNode, costContainer);
			substitutionNodeEditPath.addEditOperation(substitution);
			
			EditPath substitutionEditPath = new EditPath(from, to);
			substitutionEditPath.addNodeEditPath(substitutionNodeEditPath);
			open.add(substitutionEditPath);
		}
		
		EditPath deletionEditPath = new EditPath(from, to);
		processNodeDeletion(from, to, firstNode, deletionEditPath, costContainer, open);
	}	
	

	private static void process(DecoratedGraph from, DecoratedGraph to, EditPath minimumCostPath,
			NavigableSet<EditPath> open, CostContainer costContainer) {
	
		Set<DecoratedNode> mappedFromNodes = minimumCostPath.getMappedFromNodes();
		
		Set<DecoratedNode> mappedToNodes = minimumCostPath.getMappedToNodes();
		Set<DecoratedNode> unmappedToNodes = to.getRestNodes(mappedToNodes);
		
		if(mappedFromNodes.size() < from.getNodeNumber()) {
			DecoratedNode nextFromNode = from.getNextNode(mappedFromNodes);
			
			for(DecoratedNode unmappedToNode : unmappedToNodes) {
				EditPath substitutionEditPath = minimumCostPath.copy();
				
				NodeEditPath substitutionNodeEditPath = new NodeEditPath();
				
				substitutionNodeEditPath.setFrom(nextFromNode);
				substitutionNodeEditPath.setTo(unmappedToNode);
				
				for(DecoratedNode adjacentFrom : nextFromNode.getAdjacent()) {
					if(mappedFromNodes.contains(adjacentFrom)) {
						DecoratedNode adjacentTo = substitutionEditPath.getMapped(adjacentFrom);
						
						if(!unmappedToNode.isAdjacent(adjacentTo)) {
							EditOperation edgeDeletion = new EdgeDeletion(nextFromNode, adjacentFrom, costContainer);
							substitutionNodeEditPath.addEditOperation(edgeDeletion);
						}
					}
				}
				
				for(DecoratedNode adjacentTo : unmappedToNode.getAdjacent()) {
					if(mappedToNodes.contains(adjacentTo)) {
						DecoratedNode adjacentFrom = substitutionEditPath.getMappedReverse(adjacentTo);
						
						if(!nextFromNode.isAdjacent(adjacentFrom)) {
							EditOperation edgeInsertion = new EdgeInsertion(unmappedToNode, adjacentTo, costContainer);
							substitutionNodeEditPath.addEditOperation(edgeInsertion);
						}
					}
				}
				
				if(from.isDirected()) {
					for(DecoratedNode accessedByFrom : nextFromNode.getAccessedBy()) {
						if(mappedFromNodes.contains(accessedByFrom)) {
							DecoratedNode accessedByTo = substitutionEditPath.getMapped(accessedByFrom);
							
							if(!unmappedToNode.isAccessedBy(accessedByTo)) {
								EditOperation edgeDeletion = new EdgeDeletion(accessedByFrom, nextFromNode, costContainer);
								substitutionNodeEditPath.addEditOperation(edgeDeletion);
							}
						}
					}
					
					for(DecoratedNode accessedByTo : unmappedToNode.getAccessedBy()) {
						if(mappedToNodes.contains(accessedByTo)) {
							DecoratedNode accessedByFrom = substitutionEditPath.getMappedReverse(accessedByTo);
							
							if(!nextFromNode.isAccessedBy(accessedByFrom)) {
								EditOperation edgeInsertion = new EdgeInsertion(accessedByTo, unmappedToNode, costContainer);
								substitutionNodeEditPath.addEditOperation(edgeInsertion);
							}
						}
					}
				}
				
				EditOperation substitution = new NodeSubstitution(nextFromNode, unmappedToNode, costContainer);
				substitutionNodeEditPath.addEditOperation(substitution);
				
				substitutionEditPath.addNodeEditPath(substitutionNodeEditPath);
				open.add(substitutionEditPath);
			}
			
			processNodeDeletion(from, to, nextFromNode, minimumCostPath.copy(), costContainer, open);
		} else {
			processNodeInsertion(from, minimumCostPath, unmappedToNodes, costContainer, open);
		}
	}

	
	private static void processNodeDeletion(DecoratedGraph from, DecoratedGraph to, 
			DecoratedNode node, EditPath deletionEditPath,
			CostContainer costContainer, NavigableSet<EditPath> open) {
		
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
		open.add(deletionEditPath);
	}
	
	
	private static void processNodeInsertion(DecoratedGraph from, EditPath editPath,
			Set<DecoratedNode> unmappedToNodes, CostContainer costContainer, 
			NavigableSet<EditPath> open) {
		
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
		open.add(insertionPath);
	}
	
}
