package ged;

import ged.editpath.EditPath;
import ged.editpath.EditPathFinder;
import ged.graph.DecoratedGraph;
import ged.graph.DotParseException;
import ged.graph.GraphConverter;
import att.grappa.Graph;

/**
 * 
 * @author Roman Tekhov
 */
public class Processor {
	
	
	public OutputContainer process(InputContainer inputContainer) throws DotParseException {
		DecoratedGraph first = GraphConverter.parse(inputContainer.getFromDot());
		DecoratedGraph second = GraphConverter.parse(inputContainer.getToDot());
		
		if(first.isDirected() && !second.isDirected() || 
				!first.isDirected() && second.isDirected()) {
			throw new DotParseException("Can't compare directed and undirected graphs!");
		}
		
		EditPath editPath = EditPathFinder.find(first, second, inputContainer.getCostContainer());
		
		Graph graph = GraphConverter.combine(editPath);
				
		return new OutputContainer(graph, editPath);
	}

}
