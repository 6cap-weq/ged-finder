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
	
	/**
	 * @return input data container from view
	 */
	InputContainer getInputContainer();
	
	
	/**
	 * Displays an error.
	 * 
	 * @param message error message text
	 */
	void showError(String message);
	
	
	/**
	 * Displays the result data.
	 * 
	 * @param grappaGraph combined result graph
	 * @param editPath result edit path
	 */
	void showResult(Graph grappaGraph, EditPath editPath);
	
	
	/**
	 * Disables the computation trigger element.
	 */
	void disableComputeTrigger();
	
	
	/**
	 * Enables the computation trigger element.
	 */
	void enableComputeTrigger();

}
