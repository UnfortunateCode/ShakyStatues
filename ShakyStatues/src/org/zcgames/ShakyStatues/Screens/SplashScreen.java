package org.zcgames.ShakyStatues.Screens;


import org.zcgames.ShakyStatues.ShakyStatues;
import org.zcgames.ShakyStatues.SSHelpers.AssetLoader;

import com.badlogic.gdx.Screen;

/**
 * Splash Screen shows a quick splash of Company Name
 * and Game Name while loading assets needed for the game.
 * 
 * If the assets take too long, the splash repeats/goes into
 * a waiting animation.
 * 
 * @author Paul
 *
 */
public class SplashScreen implements Screen {
	ShakyStatues shakyStatues;
	boolean displayedOnce = false;
	
	public SplashScreen(ShakyStatues ss) {
		shakyStatues = ss;
		
	}

	@Override
	public void render(float delta) {
		System.err.println("Loading is done? " + shakyStatues.getThreadHead().isDone());
		if (displayedOnce && shakyStatues.getThreadHead().isDone()) {
			//shakyStatues.setScreen(new MenuScreen());
		}
		
		// TODO Auto-generated method stub
		// Renders the Splash Screen.
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		System.err.println("SplashScreen has been resized.");
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		// Called once when this screen is set as the Game Screen
		//  Sets up the screen, each update will be handled by render()
		System.err.println("SplashScreen has been set.");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		// Called when this screen is no longer being displayed in the game.
		//  For the Splash Screen, this should just dispose everything
		System.err.println("SplashScreen has been hidden.");
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		// Called when user hits Home Button or has an Incoming Call.
		// Should cause ShakyStatues.threadHolder.pauseThread().
		// -possibly give user choice to load app data in background,
		//  in which case SS.tH.pT should not be called. Other logic
		//  may be needed.

		System.err.println("SplashScreen has been paused.");
		shakyStatues.getThreadHead().pause();
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		// Once user returns to game. Check if data is fully loaded,
		//  and if so, go directly to MenuScreen. Note, this may break
		//  the Splash Loads At Least Once.

		System.err.println("SplashScreen has been resumed.");
		if (shakyStatues.getThreadHead().isDone()) {
			//shakyStatues.setScreen(new MenuScreen());
		}
		shakyStatues.getThreadHead().resume();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		// Unlikely to have any resources in this screen, but release
		//  them here if necessary.

		System.err.println("SplashScreen has been disposed.");
		
	}

}
