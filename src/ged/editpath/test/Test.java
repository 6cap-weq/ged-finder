package ged.editpath.test;

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
		test(5, 10);
	}


	private static void test(int fromNodeCount, int toNodeCount) {
		try {
			double duration = 0;

			int n = 10;

			for(int i = 0; i < n; i++) {
				CostContainer costContainer = new CostContainer();

				String fromDotExpr = DotGenerator.generate(fromNodeCount, fromNodeCount * 3, 4, "from", true);
				String toDotExpr = DotGenerator.generate(toNodeCount, toNodeCount * 3, 4, "to", true);

				DecoratedGraph from = GraphConverter.parse(fromDotExpr);
				DecoratedGraph to = GraphConverter.parse(toDotExpr);

				long start = System.currentTimeMillis();

				EditPathFinder.find(from, to, costContainer);

				long end = System.currentTimeMillis();

				duration += end - start;
			}

			duration /= n;

			System.out.println(fromNodeCount + ",  " + toNodeCount + " - " + duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
