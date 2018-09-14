package org.zcgames.ShakyStatues.SSHelpers;

import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zcgames.Assets.ThreadHead;
import org.zcgames.ShakyStatues.ShakyStatues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AssetLoader {
	private static boolean isLoaded = false;
	private static String error = "";
	
	public static TreeSet<Theme> THEME_HEAP = new TreeSet<Theme>();
	

	/**
	 * load handles all the logic in finding assets and storing
	 * them in an accessible location. The threadHead is provided
	 * so that AssetLoader can check to see if loading should be
	 * paused/resumed.
	 * 
	 * @param threadHead head of the pausing thread
	 */
	public static void load(ThreadHead threadHead) {
		// TODO Auto-generated method stub
		FileHandle baseDir;
		FileHandle[] children;
		DocumentBuilder dBuilder;
		try {
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			error = "The Museum has collapsed.\nError 02: Document Builder failed in AssetLoader";
			return;
		}
		
		
		/*
		 * Loading Themes
		 * Theme > ThemeName >
		 * * colorScheme.xml - includes colors for menus, backgrounds, etc.
		 * * themeDescription.xml - includes data that can be viewed about the
		 *                          theme in question. Also, this file holds the
		 *                          priority of the theme which will be used to
		 *                          generate how soon the theme appears.
		 * * Artifacts > 
		 * * * background.png - background image
		 * * * 001.png - image file for first artifact
		 * * * 001.map - data file containing mappings of stills and
		 *               animation found in the bmp
		 * * * 001.act - data file containing actions for artifact
		 *               including standby/breaking/descriptions/etc.
		 * * Statues >
		 * * * 001.png - Each standard statue gets a bmp, map, and act file
		 * * * 001.map   that determines their actions during play and in 
		 * * * 001.act   the hall of statues.
		 * * MiniBoss >
		 * * * 001.png - MiniBosses only occur on 2x3 rooms, and are un-related
		 * * * 001.map   to the boss of the floor set.
		 * * * 001.act
		 * * Boss >
		 * * * 001.png - Bosses only occur on boss floors. Bosses are ordered
		 * * * 001.map   based on file number, with the lower number appearing
		 * * * 001.act   more often than with higher numbers. Should not exceed
		 *               ten bosses per theme.
		 */
		try {
			baseDir = Gdx.files.internal("data/Themes");
		} catch (GdxRuntimeException gre) {
			// This error cannot be recovered from, as the folder holding all
			// the graphical/level data is missing.
			System.err.println("Error: " + gre);
			error = "All the exhibits are gone.\nError 01: Themes Folder Missing";
			return;
		}
		
		children = baseDir.list();
		Theme.Builder themeBuilder;
		Document doc;
		FileHandle map, act;
		
		// For each theme in the Theme/ folder, attempt to build the theme using
		// the Theme.Builder, and if successful, add it to the THEME_HEAP
		for (FileHandle themeHandle : children) {
			themeBuilder = new Theme.Builder();
			try {
				doc = dBuilder.parse(themeHandle.child("themeDescription.xml").file());
				parseThemeDescription(doc, themeBuilder);
				
				doc = dBuilder.parse(themeHandle.child("colorScheme.xml").file());
				parseColorScheme(doc, themeBuilder);
				
				// Collect Artifacts
				for (FileHandle fh : themeHandle.child("Artifacts").list()) {
					threadHead.waiting(); // wait until thread is not paused
					if (fh.extension().equals("png")) {
						try {
							map = themeHandle.child("Artifacts").child(fh.nameWithoutExtension() + ".map");
							act = themeHandle.child("Artifacts").child(fh.nameWithoutExtension() + ".act");

							addArtifact(new Texture(fh), dBuilder.parse(map.file()), dBuilder.parse(act.file()), themeBuilder);
						} catch (Exception e) {
							// Not all the files associated with the artifact are valid, so
							// the artifact is ignored
							continue;
						}
					}
				}
				
				// Collect Statues
				for (FileHandle fh : themeHandle.child("Statues").list()) {
					threadHead.waiting(); // wait until thread is not paused
					if (fh.extension().equals("png")) {
						try {
							map = themeHandle.child("Statues").child(fh.nameWithoutExtension() + ".map");
							act = themeHandle.child("Statues").child(fh.nameWithoutExtension() + ".act");

							addStatue(new Texture(fh), dBuilder.parse(map.file()), dBuilder.parse(act.file()), themeBuilder);
						} catch (Exception e) {
							// Not all the files associated with the statue are valid, so
							// the statue is ignored
							continue;
						}
					}
				}
				
				// Collect MiniBosses
				for (FileHandle fh : themeHandle.child("MiniBoss").list()) {
					threadHead.waiting(); // wait until thread is not paused
					if (fh.extension().equals("png")) {
						try {
							map = themeHandle.child("MiniBoss").child(fh.nameWithoutExtension() + ".map");
							act = themeHandle.child("MiniBoss").child(fh.nameWithoutExtension() + ".act");

							addMiniBoss(new Texture(fh), dBuilder.parse(map.file()), dBuilder.parse(act.file()), themeBuilder);
						} catch (Exception e) {
							// Not all the files associated with the miniBoss are valid, so
							// the miniBoss is ignored
							continue;
						}
					}
				}
				
				// Collect Bosses
				for (FileHandle fh : themeHandle.child("Boss").list()) {
					threadHead.waiting(); // wait until thread is not paused
					if (fh.extension().equals("png")) {
						try {
							map = themeHandle.child("Boss").child(fh.nameWithoutExtension() + ".map");
							act = themeHandle.child("Boss").child(fh.nameWithoutExtension() + ".act");

							addBoss(new Texture(fh), dBuilder.parse(map.file()), dBuilder.parse(act.file()), themeBuilder);
						} catch (Exception e) {
							// Not all the files associated with the boss are valid, so
							// the boss is ignored
							continue;
						}
					}
				}
				
			} catch (Exception e) {
				// Files in the theme were missing, or data was incorrect
				if (error == "") {
					error = "The exhibits have been vandalized.\nError 02: Corruption in:";
				}
				error += themeHandle.name() + "\n";
					
				continue;
			}
			
			THEME_HEAP.add(themeBuilder.build());
		}
		
		if (THEME_HEAP.size() == 0) {
			if (children.length == 0) {
				error = "All the exhibits are gone.\nError 01: No themes found in Themes/";
			}
			return;
		}
		
		isLoaded = true;
	}
	
	private static void parseThemeDescription(Document doc, Theme.Builder themeBuilder) {
		
	}
	
	private static void parseColorScheme(Document doc, Theme.Builder themeBuilder) {
		
	}
	
	private static void addArtifact(Texture image, Document mapping, Document actions, Theme.Builder themeBuilder) {
		
	}
	
	/*
	 * addStatue accepts in the formated form of the three files associated with the statue, and
	 * reads them to assemble a Statue Object using the Statue.Builder class.
	 */
	private static void addStatue(Texture image, Document mapping, Document actions, Theme.Builder themeBuilder) 
		throws Exception
	{
		Statue.Builder statueBuilder = new Statue.Builder();
		
		// TODO Read why to normalize at 
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		mapping.getDocumentElement().normalize();
		actions.getDocumentElement().normalize();
		
		// Create a Sprite Map for the Statue.  Each image will be placed in the map with a 
		// String Key, the string coming from the mapping elements for Name.
		NodeList frames = mapping.getElementsByTagName("frame");
		Element element;
		for (int i = 0; i < frames.getLength(); ++i) {
			element = (Element) frames.item(i);
			statueBuilder.addSprite(element.getElementsByTagName("name").toString(),
					new TextureRegion(image,
							Integer.valueOf(element.getElementsByTagName("x").toString()),
							Integer.valueOf(element.getElementsByTagName("y").toString()),
							Integer.valueOf(element.getElementsByTagName("width").toString()),
							Integer.valueOf(element.getElementsByTagName("height").toString())));
		}
		
		if (statueBuilder.getSprite("base") == null) {
			throw new Exception("Missing base sprite");
		}
		
		AnimationBuilder animationBuilder = new AnimationBuilder();
		
	}
	
	private static void addMiniBoss(Texture image, Document mapping, Document actions, Theme.Builder themeBuilder) {
		
	}
	
	private static void addBoss(Texture image, Document mapping, Document actions, Theme.Builder themeBuilder) {
		
	}

	public static void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public static boolean isLoaded() {
		return isLoaded;
	}

	public static String getError() {
		return error;
	}
}
