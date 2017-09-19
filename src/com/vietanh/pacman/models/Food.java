package com.vietanh.pacman.models;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Food extends Rectangle{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	public Food(int x,int y,BufferedImage img) {
		setBounds(x, y, 32, 32);
		this.img = img;
	}
	
	public void render(Graphics g) {
		g.drawImage(img, x, y, null);
		
	}
	
}
