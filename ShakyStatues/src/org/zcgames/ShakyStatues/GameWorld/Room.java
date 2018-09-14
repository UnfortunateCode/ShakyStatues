package org.zcgames.ShakyStatues.GameWorld;

import org.zcgames.ShakyStatues.SSHelpers.Text;
import org.zcgames.ShakyStatues.SSHelpers.Theme;

public class Room {

	private int index;
	private String direction;
	private Theme theme;
	private int size;
	private Floor floor;
	
	public Room (Floor floor, int index) {
		this.floor = floor;
		this.index = index;
		
		setSize();
		setDirection();
		setTheme();
	}
	
	/*
	 * setSize polls the floor plan to determine
	 * how many squares are designated for this room.
	 * 
	 * Warning: Errors from the FloorPlan will propagate
	 * through this function.
	 */
	private void setSize() {
		int [] fp = floor.getFloorPlan();
		int s = 0;
		for (int i : fp) {
			if (i == index) {
				++s;
			}
		}
		size = s;
	}
	
	
	/*
	 * Assumes a 3x3 Layout
	 * 
	 * setName pulls the direction of the room from SSHelpers > Text
	 * which will be used in getTitle.
	 * 
	 * example title: North Wing - Egyptian Hall
	 * direction = North
	 * theme = Egyptian
	 * size = 2 (corresponding to Hall)
	 * 
	 * Each room size has a different square to pull the direction
	 * from, and with each room being able to be rotated around
	 * the floor, a calculation needs to be performed to determine
	 * which index in the floor layout should be used as the
	 * direction.
	 */
	private void setDirection() {
		int [] fp = floor.getFloorPlan();
		int i;
		switch (size) {
		case 1:
			// A room of size 1 will only appear
			// once on the layout. Thus it takes its
			// direction from that one square
			for (i = 0; i < fp.length; ++i) {
				if (fp[i] == index) {
					direction = Text.LAYOUT_DIRECTIONS[i];
				}
			}
			break;
		case 2:
			// A room of size 2 will have one side
			// and either one corner or the center.
			// thus it takes its direction from the side,
			// which is located in one of the odd
			// indices.
			for (i = 1; i < fp.length; i += 2) {
				if (fp[i] == index) {
					direction = Text.LAYOUT_DIRECTIONS[i];
				}
			}
			break;
		case 3:
			// A room of size 3 should take its direction
			// from the center location. Thus the
			// second index is the one used.
			boolean flag = false;
			for (i = 0; i < fp.length; ++i) {
				if (fp[i] == index) {
					if (!flag) {
						flag = true;
					} else {
						direction = Text.LAYOUT_DIRECTIONS[i];
					}
				}
			}
			break;
		case 4:
			// A room of size 4 will always take
			// one corner, the center, and two
			// adjacent sides.  Thus the direction should
			// come from the corner.  Both corners
			// and the center have even indices, so
			// the index 4 (the center) should be
			// ignored.
			for (i = 0; i < fp.length; i += 2) {
				if (i == 4) {
					continue;
				} else if (fp[i] == index) {
					direction = Text.LAYOUT_DIRECTIONS[i];
				}
			}
			break;
		case 6:
			// A room of size 6 will take three of
			// the four sides. The direction should come
			// from the side opposite of the side belonging
			// to a different room. To determine this, find
			// the side belonging to a different room, and 
			// choose the opposite:
			// 1<->7 & 3<->5 or 8-i 
			for (i = 1; i < fp.length; i += 2) {
				if (fp[i] != index) {
					// i is in [1,8] so 8-i will range within [7,0]
					// and thus be a safe index.
					direction = Text.LAYOUT_DIRECTIONS[8-i];
				}
			}
			break;
		case 9:
			// The title for the boss wing will not use a 
			// direction. But to prevent errors, the direction
			// will be set and used internally.
			direction = Text.LAYOUT_DIRECTIONS[Text.LAYOUT_DIRECTIONS.length-2];
		default:
			// This direction should never appear in game, but
			// if there is a huge problem, or if the game was
			// hacked, a fun title can be used.
			direction = Text.LAYOUT_DIRECTIONS[Text.LAYOUT_DIRECTIONS.length-1];
		}
		
	}
	
	/*
	 * setTheme looks at the previous
	 */
	private void setTheme() {
		// TODO
	}

	/*
	 * getTitle pulls together the data to create the title used
	 * on the game screen for the room being played in.
	 */
	public String getTitle() {
		if (direction == Text.LAYOUT_DIRECTIONS[Text.LAYOUT_DIRECTIONS.length-2]) {
			// This is the boss room, and should only include the flavor and the
			// designation
			return theme + " " + Text.LAYOUT_DESIGNATIONS[size];
		}
		return direction + " Wing: " + theme + " " + Text.LAYOUT_DESIGNATIONS[size];
	}
	

}
