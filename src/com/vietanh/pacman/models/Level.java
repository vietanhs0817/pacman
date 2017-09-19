package com.vietanh.pacman.models;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Level {
	public int width;
	public int height;

	public BufferedImage[][] titles;
	public Food[][] foods;

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

	public Level(String path) {
		try {
			BufferedImage map = ImageIO.read(new File(path));

			BufferedImage foodImage = ImageIO.read(new File("res/food.png"));
			width = map.getWidth();
			height = map.getHeight();
			int cols = 20;
			int rows = 15;
			titles = new BufferedImage[cols][rows];
			foods = new Food[cols][rows];
			for (int xx = 0; xx < cols; xx++) {
				for (int yy = 0; yy < rows; yy++) {
					titles[xx][yy] = map.getSubimage(xx * 32, yy * 32, 32, 32);
					if (isTransparentFull(titles[xx][yy])) {
						if ((xx == 9 && yy == 9) || (xx >= 7 && xx <= 11 && yy >= 0 && yy <= 3) ) {
							
						}else {
							foods[xx][yy] = new Food(xx * 32, yy * 32, foodImage);
						}
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void render(Graphics g) {
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 15; y++) {
				g.drawImage(titles[x][y], x * 32, y * 32, null);
				if (foods[x][y] != null) {
					foods[x][y].render(g);
				}
			}
		}
	}
}
