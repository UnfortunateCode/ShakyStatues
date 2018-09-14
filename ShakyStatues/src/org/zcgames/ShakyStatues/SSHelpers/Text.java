package org.zcgames.ShakyStatues.SSHelpers;

public class Text {

	/*
	 * Assumes a 3x3 Layout
	 * 
	 * "Boss" and "Lost" shouldn't appear on the game screen,
	 * but will be available in case of error/hacking.
	 * The index at length-1 should always be the error string.
	 * The index at length-2 should always be the boss string.
	 */ 
	public static final String [] LAYOUT_DIRECTIONS = {"North-West", "North", "North-East", 
												  		"West", "Center", "East",
												  		"South-West", "South", "South-East",
												  		"Boss", "Lost"};

	/*
	 * Assumes a 3x3 Layout
	 * 
	 * Sizes 0, 5, 7, 8 should not be used.
	 */
	public static final String[] LAYOUT_DESIGNATIONS = {"Antechamber", "Chamber", "Hall",
														"Walk", "Room", "Hollow",
														"Grand Hall", "Shadow", "Excursion",
														"Floor"};
}
