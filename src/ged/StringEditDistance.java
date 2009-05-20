package ged;

import org.apache.commons.lang.StringUtils;

/**
 * String edit distance computation.
 * 
 * @author Roman Tekhov
 */
public class StringEditDistance {
	
	
	public static double calculateCoefficient(String str1, String str2) {
		String a, b;
		
		// determine the shorter string
		if(str1.length() < str2.length()) {
			a = str1;
			b = str2;
		} else {
			a = str2;
			b = str1;
		}
		
		double editDistance = StringUtils.getLevenshteinDistance(a, b);
		
		return editDistance / ((double)b.length());
	}
}
