package org.zcgames.ShakyStatues.GameWorld;

import java.util.Random;

import org.zcgames.ShakyStatues.SSHelpers.AssetLoader;
import org.zcgames.ShakyStatues.SSHelpers.Numericals;
import org.zcgames.ShakyStatues.SSHelpers.Theme;

public class GameWorld {

	private boolean isWatching;
	private int floorsLeft;
	
	private Floor currFloor;
	private Theme[] themeList;
	private Theme[] exhibitList;
	
	private Random rand;
	
	/*
	 * Constructors
	 */
	public GameWorld() {
		isWatching = false;
		floorsLeft = -1;
		
		rand = new Random();
		currFloor = null;
		themeList = null;
		exhibitList = null;
	}
	
	public void newGame() {
		// Set up levels
		themeList = (Theme[])Numericals.fyShuffleFront(AssetLoader.THEME_HEAP.toArray(new Theme[0]), 5, 3);
		exhibitList = (Theme[])Numericals.fyShuffleBack(themeList, 3, 2);
		
		floorsLeft = rand.nextInt(3)+3;
		currFloor = new Floor(this);
		
	}
	
	/*
	 * Getters
	 */
	public boolean isWatching() {
		return isWatching;
	}
	
	public int floorsLeft() {
		return floorsLeft;
	}
	
	public Random getRandom() {
		return rand;
	}
	
	public Floor currFloor() {
		return currFloor;
	}

}
