package ged.editpath.test;

import ged.editpath.EditPath;
import ged.editpath.EditPathFinder;
import ged.graph.DecoratedGraph;
import ged.graph.GraphConverter;
import ged.processor.CostContainer;

/**
 * Test class.
 * 
 * @author Roman Tekhov
 */
public class Test {

	
	public static void main(String[] args) {		
		try {
			CostContainer costContainer = new CostContainer();
			
			String fromDotExpr = DotGenerator.generate(5, 10, 4, "from", true);
			String toDotExpr = DotGenerator.generate(5, 10, 4, "to", true);
						
			DecoratedGraph from = GraphConverter.parse(fromDotExpr);
			DecoratedGraph to = GraphConverter.parse(toDotExpr);
			
			long start = System.currentTimeMillis();
			
			EditPath editPath = EditPathFinder.find(from, to, costContainer);
			
			long end = System.currentTimeMillis();
			
			System.out.println(end - start);
			System.out.println(editPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
