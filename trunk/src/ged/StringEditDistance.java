package ged;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

/**
 * String edit distance computation.
 * 
 * @author Roman Tekhov
 */
public class StringEditDistance {
	
	
	public static BigDecimal calculateCoefficient(String str1, String str2) {
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
		
		return BigDecimal.valueOf(editDistance).
				divide(BigDecimal.valueOf(b.length()).setScale(2), 
					2, RoundingMode.HALF_EVEN);
	}
}
