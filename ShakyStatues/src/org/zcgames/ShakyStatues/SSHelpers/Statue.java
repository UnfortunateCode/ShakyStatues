package org.zcgames.ShakyStatues.SSHelpers;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Statue {

	private Statue(Statue.Builder statueBuilder) {
		
	}
	
	public static class Builder {
		private HashMap<String, TextureRegion> spriteMap;
		
		public Builder addSprite(String name, TextureRegion region) {
			spriteMap.put(name, region);
			return this;
		}

		public TextureRegion getSprite(String name) {
			return spriteMap.get(name);
		}
	}
}
