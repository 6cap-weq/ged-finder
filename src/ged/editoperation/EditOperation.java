package ged.editoperation;

public abstract class EditOperation {
	
	private CostContainer costContainer;
	
	
	public EditOperation(CostContainer costContainer) {
		this.costContainer = costContainer;
	}


	public double getCost() {
		return doGetCost(costContainer);
	}


	protected abstract double doGetCost(CostContainer costContainer);
	
}
