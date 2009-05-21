package ged.processor;

import ged.editpath.EditPath;
import att.grappa.Graph;

public class OutputContainer {
	
	private Graph graph;
	private EditPath editPath;
	
	
	public OutputContainer(Graph graph, EditPath editPath) {
		this.graph = graph;
		this.editPath = editPath;
	}

	
	public Graph getGraph() {
		return graph;
	}
	
	public EditPath getEditPath() {
		return editPath;
	}
	
}
