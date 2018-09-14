package org.zcgames.ShakyStatues.GameObjects;

import java.util.Random;

import org.zcgames.ShakyStatues.GameWorld.GameWorld;

public class GameStatue extends GameObject {
	
	protected FrameStability keyFrameStabilities[];
	protected int frameNumber;
	protected float elapsed;
	protected boolean isStable;
	protected Random r;
	protected GameWorld world;
	
	protected class FrameStability {
		public float stability;
		public float duration;
		public float framePercs[];
		public static final int PREV_PERC = 0;
		public static final int CURR_PERC = 1;
		public static final int NEXT_PERC = 2;
		
		public FrameStability() {
			stability = 0.0f;
			duration = 0.0f;
			framePercs = new float[3];
			framePercs[PREV_PERC] = 0.0f;
			framePercs[CURR_PERC] = 0.0f;
			framePercs[NEXT_PERC] = 0.0f;
		}
		
		public FrameStability (float stability, float duration, float prevPercent, float currPercent, float nextPercent) {
			this.stability = stability;
			this.duration = duration;
			framePercs = new float[3];
			framePercs[PREV_PERC] = prevPercent;
			framePercs[CURR_PERC] = currPercent;
			framePercs[NEXT_PERC] = nextPercent;
		}
	}

	public GameStatue(float x, float y, int width, int height, GameWorld world) {
		super(x, y, width, height);
		
		this.world = world;
		keyFrameStabilities = new FrameStability[8];
		initializeStabilities();
	}
	
	protected void initializeStabilities() {
		for (int i = 0; i < keyFrameStabilities.length; i++) {
			keyFrameStabilities[i] = new FrameStability();
		}
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if (world.isWatching()) {
			if (!isStable) {
				elapsed += delta;
				if (elapsed > keyFrameStabilities[frameNumber].duration) {
					/*
					 * The statue ends up in one of three frames (prev, curr, or next)
					 * Once frame has been chosen, there is a chance of still being
					 * off balance.  
					 * 
					 * At worst, the statue will end up at a standing frame (0 or 4).
					 * Testing needs to be completed to determine whether leaving the
					 * chances alone is fine, or if the stability should increase over
					 * iterations.  To do that, create a variable int shakeNum, set it
					 * to 0 in stop().  During this update, multiply the Stability of
					 * the following frame by 1.1^shakeNum.
					 */
					
					// Determine following frameNumber
					float frameChance = r.nextFloat();
					float a[] = keyFrameStabilities[frameNumber].framePercs;
					int i;
					for (i = 0; i < a.length; ++i) {
						if (a[i] >= frameChance) {
							switch(i) {
							case 0:
								--frameNumber;
								break;
							case 2:
								++frameNumber;
								break;
							}
							frameNumber %= 8;
						} else {
							frameChance -= a[i];
						}
					}
					
					// Determine if shaky
					isStable = r.nextFloat() < keyFrameStabilities[frameNumber].stability;
					elapsed = 0.0f;
				} 
				// If elapsed < duration, isStable remains false, renderer shows shaky animation
			}
			// If stable, will remain stable
		} else {
			// Player is turned away
			move(delta);
		}
	}
	
	public void move(float delta) {
		// override-able method to let each statue move in its
		// own way.  Update should not be overridden.
	}
	
	public void stop() {
		isStable = r.nextFloat() < keyFrameStabilities[frameNumber].stability;
		elapsed = 0.0f;
	}

}
