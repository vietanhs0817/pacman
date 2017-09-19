package com.vietanh.pacman.models;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.vietanh.pacman.ui.PacmanUI;

public class Player extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean up=true, down, left, right;
	private int speed = 2;
	private BufferedImage img[];
	private BufferedImage imgLeft[];
	private BufferedImage imgDown[];
	private BufferedImage imgUp[];
	
	public Player(int x, int y) {
		setBounds(x, y, 32, 32);
		playerImageLoader();
	
	}

	public void playerImageLoader() {
		try {
			if (img == null) {
				img = new BufferedImage[30];
				imgLeft = new BufferedImage[30];
				imgDown = new BufferedImage[30];
				imgUp = new BufferedImage[30];
			}
			int temp = 0;
			BufferedImage playerImg = ImageIO.read(new File("res/player.png"));
			BufferedImage playerLeftImg = ImageIO.read(new File("res/player_left.png"));
			BufferedImage playerDownImg = ImageIO.read(new File("res/player_down.png"));
			BufferedImage playerUpImg = ImageIO.read(new File("res/player_up.png"));
			
			for (int i = 0; i < img.length; i++) {
				if(i%10 == 0&& i != 0) {
					temp += 1;
				}
				img[i] = playerImg.getSubimage(temp * 32, 0, 32, 32);
				imgLeft[i] = playerLeftImg.getSubimage(temp * 32, 0, 32, 32);
				imgDown[i] = playerDownImg.getSubimage(temp * 32, 0, 32, 32);
				imgUp[i] = playerUpImg.getSubimage(temp * 32, 0, 32, 32);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

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

	private boolean canEatFood() {
		Rectangle bounds = new Rectangle(x, y, width, height);
		Level level = PacmanUI.level;
		for (int xx = 0; xx < level.titles.length; xx++) {
			for (int yy = 0; yy < level.titles[0].length; yy++) {
				if (level.foods[xx][yy] != null) {
					if (bounds.intersects(level.foods[xx][yy])) {
						level.foods[xx][yy] = null;
						return true;
					}
				}
			}
		}
		return false;
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
		canEatFood();
	}

	public void render(Graphics g,int i) {
		if(right) {
			g.drawImage(img[i], x, y, null);	
		}
		if(left) {
			g.drawImage(imgLeft[i], x, y, null);	
		}
		if(down) {
			g.drawImage(imgDown[i], x, y, null);	
		}
		if(up) {
			g.drawImage(imgUp[i], x, y, null);	
		}
		
	}

}
