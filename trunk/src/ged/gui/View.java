package ged.gui;

import ged.editpath.EditPath;
import ged.processor.InputContainer;
import att.grappa.Graph;

/**
 * Defines operations for accessing and manipulating view
 * components.
 * 
 * @author Roman Tekhov
 */
public interface View {
	
	
	InputContainer getInputContainer();
	
	
	void showError(String message);
	
	
	void showResult(Graph grappaGraph, EditPath editPath);
	
	
	void disableComputeTrigger();
	
	
	void enableComputeTrigger();

}
