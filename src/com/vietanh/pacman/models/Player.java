package com.vietanh.pacman.models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.vietanh.pacman.ui.PacmanUI;

public class Player extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean up, down, left, right;
	private int speed = 4;

	public Player(int x, int y) {
		setBounds(x, y, 32, 32);
	}

	public boolean isTransparent(int x, int y, BufferedImage img) {
		int pixel = img.getRGB(x, y);
		if ((pixel >> 24) == 0x00) {
			return true;
		}
		return false;
	}

	public boolean isTransparentFull(BufferedImage img) {
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if (!isTransparent(x, y, img)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean canMove(int nextX, int nextY) {
		Rectangle bounds = new Rectangle(nextX, nextY, width, height);
		Level level = PacmanUI.level;
		for (int xx = 0; xx < level.titles.length; xx++) {
			for (int yy = 0; yy < level.titles[0].length; yy++) {
				if (!isTransparentFull(level.titles[xx][yy])) {

					if (bounds.intersects(new Rectangle(xx * 32, yy * 32, 32, 32))) {

						return false;
					}
				}
			}
		}
		return true;
	}

	public void tick() {
		if (right && canMove(x + speed, y)) 
			x += speed;
		if (left && canMove(x - speed, y))
			x -= speed;
		if (down && canMove(x, y + speed))
			y += speed;
		if (up && canMove(x, y - speed))
			y -= speed;
		if (x < -32) {
			x = 640;
			return;
		}
		
		if(x > 672) {
			x = 0;
			return;
		}
			
	}

	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
	}

}
