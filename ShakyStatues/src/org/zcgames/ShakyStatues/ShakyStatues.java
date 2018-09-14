package org.zcgames.ShakyStatues;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import org.zcgames.Assets.ThreadHead;
import org.zcgames.Assets.ThreadHolder;
import org.zcgames.ShakyStatues.SSHelpers.AssetLoader;
import org.zcgames.ShakyStatues.SSHelpers.Numericals;
import org.zcgames.ShakyStatues.Screens.ErrorScreen;
import org.zcgames.ShakyStatues.Screens.SplashScreen;


/*
 * Shaky Statues is a game about delving down procedurally
 * generated levels, and playing the game Statues or
 * Red Light/Green Light with the mobs that appear.
 * 
 * 
 * Assumptions:
 * It is assumed that each floor will have a 3x3 layout, 
 * with rooms taking 1x1, 1x2, 1x3, 2x2, 2x3, or 3x3 spaces.
 * The 3x3 layout is reserved for Boss Levels.
 * To increase/decrease the size of the floors, the following
 * will need to be changed:
 * SSHelpers > Text > LAYOUT_NAMES
 * GameWorld > Room > getName()
 */
public class ShakyStatues extends Game {
	
	private ThreadHead threadHead;

	@Override
	public void create() {
		
		threadHead = new ThreadHead();
		threadHead.attach(new ThreadHolder(new Runnable() {
			public void run() {
				AssetLoader.load(threadHead);
				if (!AssetLoader.isLoaded()) {
					setScreen(new ErrorScreen(AssetLoader.getError()));
				}
				Numericals.initialize(threadHead);
			}
		}));
		threadHead.begin();
		
		setScreen(new SplashScreen(this));
		
	}


	public ThreadHead getThreadHead() {
		return threadHead;
	}
	
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
	
}
