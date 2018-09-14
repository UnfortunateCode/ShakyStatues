package org.zcgames.ShakyStatues.SSHelpers;

import java.util.Random;

public class Percental <T> {
	
	private float [] percentages;
	private T [] values;
	private boolean initialized;
	
	public Percental() {
		initialized = false;
	}
	
	/*
	 * setValues accepts an array of percentages (in any form)
	 * and an array of values.  The two arrays will be set to
	 * identical lengths, by either ignoring extraneous percentages,
	 * or by adding 0% for additional values. Finally, setValues
	 * normalizes the percentages to sum up to 1.0.
	 * 
	 * To create a uniform distribution: either create an array
	 * the same length of values, and set all elements to the same
	 * non-zero positive number; or use the single argument setValues.
	 * 
	 * This instance will not be usable until setValues has successfully
	 * completed.
	 */
	public void setValues(float[] percentages, T[] values) {
		this.values = values;
		
		// Check to ensure arrays are the same length
		if (percentages.length != values.length) {
			if (percentages.length > values.length){
				// Remove unnecessary percentages
				this.percentages = new float[values.length];
				System.arraycopy(percentages, 0, this.percentages, 0, values.length);
				
			} else {
				// Set extra percentages to 0%
				this.percentages = new float[values.length];
				System.arraycopy(percentages, 0, this.percentages, 0, percentages.length);
				int i;
				for (i = percentages.length; i < values.length; ++i) {
					this.percentages[i] = 0;
				}
			}
		} else {
			this.percentages = percentages;
		}
		
		// Normalize
		int i;
		float sum = 0.0f;
		for (i = 0; i < this.percentages.length; ++i) {
			if (this.percentages[i] < 0) {
				this.percentages[i] = 0;
			} else {
				sum += this.percentages[i];
			}
		}
		if (sum == 0) {
			sum = 1.0f;
		}
		for (i = 0; i < this.percentages.length; ++i) {
			this.percentages[i] /= sum;
		}
		
		initialized = true;
		
	}
	
	/*
	 * The single argument setValues creates a uniform
	 * distribution for the given values.  If the values
	 * argument is an empty list, the function will fail
	 * and the instance will remain unusuable.
	 */
	public void setValues(T[] values) {
		if (values.length == 0) {
			return;
		}
		
		float [] p = new float[values.length];
		int i;
		for (i = 0; i < p.length; ++i) {
			p[i] = 1.0f / p.length;
		}
		setValues (p, values);
	}
	
	/*
	 * getRandom is the meat of the class. This function will
	 * return a random value based on the percentages given.
	 * 
	 * The function accepts a Random for the case of reusable
	 * seeds.
	 */
	public T getRandom(Random r) {
		if (!initialized) {
			return null;
		}
		
		float f = r.nextFloat();
		int i;
		for (i = 0; i < percentages.length; ++i) {
			if (f <= percentages[i]) {
				return values[i];
			}
			f -= percentages[i];
		}
		
		// Satisfy Eclipse's need for a return statement
		return values[0];
	}
	
	// Seedless version of getRandom.
	public T getRandom() {
		return getRandom(new Random());
	}

	/*
	 * decreasingPercentages creates an array of percentages such that
	 * dp[i] / dp[i+1] = increaseFactor. Thus, a 2 element array with a
	 * factor of 2 would result in [2/3, 1/3].
	 * 
	 * Note, that an array of n > values.length size can be given to
	 * setValues, and the renormalization will keep the increase factor.
	 */
	public static float[] decreasingPercentages(int numTerms, float increaseFactor) {
		float[] dp = new float[numTerms];
		
		float sum = 0.0f;
		int i;
		for (i = 0; i < numTerms; ++i) {
			sum += Math.pow(increaseFactor, i); // auto convert to and from double
		}
		
		float a = 1.0f / sum;
		dp[numTerms-1] = a;
		
		for (i = numTerms - 2; i >= 0; --i) {
			dp[i] = increaseFactor * dp[i+1];
		}
		
		return dp;
	}
}
