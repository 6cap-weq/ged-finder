package ged.gui;

import ged.editpath.CostLimitExceededException;
import ged.graph.DotParseException;
import ged.processor.InputContainer;
import ged.processor.OutputContainer;
import ged.processor.Processor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

/**
 * A Swing worker performing the GED finding operations.
 * 
 * @author Roman Tekhov
 */
class GedProcessWorker extends SwingWorker<OutputContainer, Void> {
	
	private View view;
	

	GedProcessWorker(View view) {
		this.view = view;
		
		addPropertyChangeListener(new ComputeTriggerDisablingListener());
	}


	@Override
	protected OutputContainer doInBackground() throws DotParseException, CostLimitExceededException {
		InputContainer inputContainer = view.getInputContainer();
		
		return Processor.process(inputContainer);
	}


	@Override
	protected void done() {
		try {
			OutputContainer output = get();
			
			view.showResult(output.getGraph(), output.getEditPath());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			String message;
			if(e.getCause() instanceof DotParseException) {
				DotParseException dotParseException = (DotParseException)e.getCause();
				
				message = "DOT parse error - " + dotParseException.getMessage();
			} else if(e.getCause() instanceof CostLimitExceededException) {
				CostLimitExceededException limitException = (CostLimitExceededException)e.getCause();
				
				message = "Acceptable cost limit exceeded - " + limitException.getCost().toPlainString();
			} else {
				message = "Unexpected error - " + e.getMessage();
			}
			
			view.showError(message);
			
			e.printStackTrace();
		} finally {
			view.enableComputeTrigger();
		}
	}
	
	
	
	private class ComputeTriggerDisablingListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent event) {
			if(event.getPropertyName().equals("state") && event.getNewValue().
						equals(SwingWorker.StateValue.STARTED)) {
				view.disableComputeTrigger();
			}
		}

	}
	
}
