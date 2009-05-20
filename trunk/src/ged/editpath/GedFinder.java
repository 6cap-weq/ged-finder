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

public class GedFinder {
	
	
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
		
		NodeEditPath deletionNodeEditPath = new NodeEditPath();
		
		deletionNodeEditPath.setFrom(firstNode);
		
		EditOperation deletion = new NodeDeletion(firstNode, costContainer);
		deletionNodeEditPath.addEditOperation(deletion);
		
		EditPath deletionEditPath = new EditPath(from, to);
		deletionEditPath.addNodeEditPath(deletionNodeEditPath);
		open.add(deletionEditPath);
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
				
				for(DecoratedNode adjucentFrom : nextFromNode.getAdjacent()) {
					if(mappedFromNodes.contains(adjucentFrom)) {
						DecoratedNode adjucentTo = substitutionEditPath.getMapped(adjucentFrom);
						
						if(!unmappedToNode.isAdjucent(adjucentTo)) {
							EditOperation edgeDeletion = new EdgeDeletion(nextFromNode, adjucentFrom, costContainer);
							substitutionNodeEditPath.addEditOperation(edgeDeletion);
						}
					}
				}
				
				for(DecoratedNode adjucentTo : unmappedToNode.getAdjacent()) {
					if(mappedToNodes.contains(adjucentTo)) {
						DecoratedNode adjucentFrom = substitutionEditPath.getMappedReverse(adjucentTo);
						
						if(!nextFromNode.isAdjucent(adjucentFrom)) {
							EditOperation edgeInsertion = new EdgeInsertion(unmappedToNode, adjucentTo, costContainer);
							substitutionNodeEditPath.addEditOperation(edgeInsertion);
						}
					}
				}
				
				EditOperation substitution = new NodeSubstitution(nextFromNode, unmappedToNode, costContainer);
				substitutionNodeEditPath.addEditOperation(substitution);
				
				substitutionEditPath.addNodeEditPath(substitutionNodeEditPath);
				open.add(substitutionEditPath);
			}
						
			NodeEditPath deletionNodeEditPath = new NodeEditPath();
			
			deletionNodeEditPath.setFrom(nextFromNode);
			
			EditOperation deletion = new NodeDeletion(nextFromNode, costContainer);
			deletionNodeEditPath.addEditOperation(deletion);
			
			EditPath deletionEditPath = minimumCostPath.copy();
			deletionEditPath.addNodeEditPath(deletionNodeEditPath);
			open.add(deletionEditPath);
		} else {
			EditPath insertionPath = minimumCostPath.copy();
			
			for(DecoratedNode unmappedToNode : unmappedToNodes) {
				NodeEditPath insertionNodeEditPath = new NodeEditPath();
				
				insertionNodeEditPath.setTo(unmappedToNode);
				
				EditOperation insertion = new NodeInsertion(unmappedToNode, costContainer);
				insertionNodeEditPath.addEditOperation(insertion);
				
				insertionPath.addNodeEditPath(insertionNodeEditPath);
			}
			
			insertionPath.setComplete(true);
			open.add(insertionPath);
		}
	}
	
}
