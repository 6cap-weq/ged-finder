package ged.gui;

import ged.graph.DotParseException;
import ged.processor.InputContainer;
import ged.processor.OutputContainer;
import ged.processor.Processor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

class GedProcessWorker extends SwingWorker<OutputContainer, Void> {
	
	private View view;
	private Processor processor;
	

	GedProcessWorker(View view, Processor processor) {
		this.view = view;
		this.processor = processor;
		
		addPropertyChangeListener(new ComputeTriggerDisablingListener());
	}


	@Override
	protected OutputContainer doInBackground() throws DotParseException {
		InputContainer inputContainer = view.getInputContainer();
		
		return processor.process(inputContainer);
	}


	@Override
	protected void done() {
		try {
			OutputContainer output = get();
			
			view.showResult(output.getGraph(), output.getEditPath());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			if(e.getCause() instanceof DotParseException) {
				DotParseException dotParseException = (DotParseException)e.getCause();
				
				view.showError("DOT parse error - " + dotParseException.getMessage());
			} else {
				view.showError("Unexpected error - " + e.getMessage());
			}
			
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
