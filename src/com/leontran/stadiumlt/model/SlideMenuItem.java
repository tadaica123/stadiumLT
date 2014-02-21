package com.leontran.stadiumlt.model;

import android.graphics.drawable.Drawable;

public class SlideMenuItem {
	public int id;
	public Drawable icon;
	public String label;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the icon
	 */
	public Drawable getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public SlideMenuItem(int id, Drawable icon, String label) {
		super();
		this.id = id;
		this.icon = icon;
		this.label = label;
	}

	public SlideMenuItem() {
		super();
	}
}
