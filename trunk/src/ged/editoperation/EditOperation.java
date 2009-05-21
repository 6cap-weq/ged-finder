package ged.editoperation;

import java.math.BigDecimal;

public abstract class EditOperation {
	
	private CostContainer costContainer;
	
	
	public EditOperation(CostContainer costContainer) {
		this.costContainer = costContainer;
	}


	public BigDecimal getCost() {
		return doGetCost(costContainer).setScale(2);
	}


	protected abstract BigDecimal doGetCost(CostContainer costContainer);
	
}
