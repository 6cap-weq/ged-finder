package ged.processor;

import ged.editpath.EditPath;
import ged.editpath.EditPathFinder;
import ged.graph.DecoratedGraph;
import ged.graph.DotParseException;
import ged.graph.GraphConverter;
import att.grappa.Graph;

/**
 * Component responsible for evaluating the entire edit distance
 * computation procedure together with related pre- and post-activities.
 * 
 * @author Roman Tekhov
 */
public class Processor {
	
	
	public static OutputContainer process(InputContainer inputContainer) throws DotParseException {
		// Parse the input graphs
		DecoratedGraph first = GraphConverter.parse(inputContainer.getFromDot());
		DecoratedGraph second = GraphConverter.parse(inputContainer.getToDot());
		
		if(first.isDirected() && !second.isDirected() || 
				!first.isDirected() && second.isDirected()) {
			throw new DotParseException("Can't compare directed and undirected graphs!");
		}
		
		// Calculate the edit path
		EditPath editPath = EditPathFinder.find(first, second, inputContainer.getCostContainer());
		
		// Form the combined result graph
		Graph graph = GraphConverter.combine(editPath);
		
		// Display result
		return new OutputContainer(graph, editPath);
	}

}
