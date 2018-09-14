package org.zcgames.ShakyStatues.SSHelpers;

import java.util.Random;

import org.zcgames.Assets.ThreadHead;

public class Numericals {

	private static boolean isInitialized = false;
	public static final Percental<Integer> NUM_ROOMS = new Percental<Integer>();
	
	
	/*
	 * Numericals.initialize() should occur after AssetLoader.load()
	 */
	public static void initialize(ThreadHead threadHead) {
		if (!AssetLoader.isLoaded()) {
			AssetLoader.load(threadHead);
			if (!AssetLoader.isLoaded()) {
				return;
			}
		}
		
		// NUM_ROOMS
		float [] numRoomsP = {10,30,50,10};
		Integer [] numRoomsV = {2,3,4,5};
		NUM_ROOMS.setValues(numRoomsP, numRoomsV);
		
		// BOSS_CHOICE
		float [] bossP = Percental.decreasingPercentages(10, 4.0f/3.0f);
		
		
		
		isInitialized = true;
	}
	
	
	
	
	/*
	 * This is an array shuffling method.  It uses the Fisher Yates 
	 * algorithm as a base, but modified to prevent later elements
	 * from showing up too early.  At best, an element can appear
	 * as early as span-skip spaces before it does in the original list, 
	 * however an element can appear at any later point.
	 * 
	 * In essence, the first span elements are shuffled, then the next span
	 * elements starting at the (skip+1)th element are shuffled, and then
	 * the next span elements starting at the (2*skip+1)th element.
	 * 
	 * For example, for span = 5 and skip = 3, the first five elements 
	 * are shuffled, then the next set of five starting at the 4th element 
	 * of the previous set. Thus, the first three will always be elements from the
	 * first five; the fourth and fifth will be elements from the first
	 * eight; the 6th from 3rd to 8th; 7th & 8th from 3rd to 11th; etc.
	 * 
	 * The Fisher Yates Shuffle is unbiased, with O(n) time. To keep
	 * it unbiased, the jmin would remain 0 throughout. To get this functionality
	 * set skip to 0 or less. Setting skip >= span would shuffle each span items
	 * without any overlap.  
	 */
	public static Object[] fyShuffleFront(Object[] oList, int span, int skip, Random rand) {
		Object[] oListNew = new Object[oList.length];
		int i, j, jmin;
		int step;
		if (span <= 1) {
			// span == 1 would return the list as is by randomly shuffling each
			// element into only one available spot... the spot it started in.
			// span < 1 does not have any representation
			return oList;
		}
		if (skip <= 0) {
			step = oList.length;
		} else if (skip > span) {
			skip = span;
			step = 0;
		} else {
			step = span - skip;
		}
		
		for (i = 0; i < oList.length; ++i) {
			// find the minimum spot to switch with
			if (i < step) {
				jmin = 0;
			} else {
				jmin = ((i - step) / skip) * skip; // Integer division
			}
			j = rand.nextInt(i + 1 - jmin) + jmin;
			
			if (j != i) {
				// a switch is necessary
				oListNew[i] = oListNew[j];
			}
			
			oListNew[j] = oList[i];
		}
		
		return oListNew;
	}
	
	/*
	 * Random-less version for when a seed is not needed.
	 */
	public static Object[] fyShuffleFront(Object[] oList, int span, int skip) {
		return fyShuffleFront(oList, span, skip, new Random());
	}
	
	/*
	 * Shuffle from the back to the front
	 */
	public static Object[] fyShuffleBack(Object[] oList, int span, int skip, Random rand) {
		Object[] oListNewR = new Object[oList.length];
		Object[] oListNew = new Object[oList.length];
		int i, revI = oList.length - 1;
		
		// Reverse array
		for (i = 0; i <= revI; ++i) {
			oListNewR[i] = oList[revI - i];
		}
		
		oListNewR = fyShuffleFront(oListNewR, span, skip, rand);
		
		// Reverse again
		for (i = 0; i < revI; ++i) {
			oListNew[i] = oListNewR[revI-i];
		}
		
		return oListNew;
	}
	
	/*
	 * Random-less version for when a seed is not needed.
	 */
	public static Object[] fyShuffleBack(Object[] oList, int span, int skip) {
		return fyShuffleBack(oList, span, skip, new Random());
	}
}
