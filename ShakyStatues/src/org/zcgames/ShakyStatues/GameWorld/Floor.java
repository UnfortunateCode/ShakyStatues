package org.zcgames.ShakyStatues.GameWorld;

import java.util.Random;

import org.zcgames.ShakyStatues.SSHelpers.Numericals;

public class Floor {

	private GameWorld world;
	private int numRooms;
	private Random rand;
	
	private Room[] path;
	private int[] floorPlan;
	
	public Floor(GameWorld gameWorld) {
		world = gameWorld;
		rand = world.getRandom();
		numRooms = Numericals.NUM_ROOMS.getRandom(rand);
	}

	public int[] getFloorPlan() {
		return floorPlan;
	}

}
