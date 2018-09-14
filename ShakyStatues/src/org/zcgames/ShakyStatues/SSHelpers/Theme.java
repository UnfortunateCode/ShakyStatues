package org.zcgames.ShakyStatues.SSHelpers;

// Theme handles all the appropriate loaded data
// for each theme.
public class Theme implements Comparable<Theme> {
	private String title;
	private int howSoon;
	
	private Theme(String title, int howSoon) {
		this.title = title;
		this.howSoon = howSoon;
	}
	
	public static class Builder {
		private String title = "";
		private int howSoon = 0;
		private Theme theme = new Theme(title, howSoon);
		
		public Theme build() {
			return theme;
		}
	}

	@Override
	public int compareTo(Theme t) {
		if (howSoon != t.howSoon) {
			return howSoon - t.howSoon;
		} else {
			return title.compareTo(t.title);
		}
	}

}
