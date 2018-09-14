package org.zcgames.ShakyStatues.GameObjects;

import com.badlogic.gdx.math.Vector2;

public class GameObject {

	protected Vector2 position;
	protected int width;
	protected int height;
	
	public GameObject(float x, float y, int width, int height) {
		position.x = x;
		position.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update(float delta) {
		// Code that every game object runs during an update
	}
	
	/*
	 * Getters
	 */
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getBottom() {
		return position.y + height;
	}
	
	public float getRight() {
		return position.x + width;
	}
	
	public float getCenterX() {
		return position.x + (width / 2.0f);
	}
	
	public float getCenterY() {
		return position.y + (height / 2.0f);
	}
}
