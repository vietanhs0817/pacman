package com.vietanh.pacman.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.vietanh.pacman.commons.CommonConst;
import com.vietanh.pacman.models.Level;
import com.vietanh.pacman.models.Player;

public class PacmanUI extends Canvas implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread thread;
	public static Player player;
	private boolean isRunning = false;
	public static Level level;
	
	public PacmanUI() {
		Dimension dimension = new Dimension(CommonConst.FRAME_WIDTH, CommonConst.FRAME_HEIGHT);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setMinimumSize(dimension);
		addKeyListener(this);
		player = new Player(32*9, 7*32);
		level = new Level("res/map.png");
	}

	public synchronized void start() {
		if (isRunning)
			return;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!isRunning)
			return;
		isRunning = false;
		try {
			thread.join();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void tick() {
		player.tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, CommonConst.FRAME_WIDTH, CommonConst.FRAME_HEIGHT);
		player.render(g);
		level.render(g);
		g.dispose();
		bs.show();
	}

	@Override
	public void run() {
		requestFocus();
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 60.0;
		double delta = 0;
		double ns = 1000000000 / targetTick;

		while (isRunning) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				render();
				fps++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println(fps);
				fps = 0;
				timer += 1000;
			}
		}
	}

	public static void main(String[] args) {
		PacmanUI game = new PacmanUI();
		JFrame frame = new JFrame(CommonConst.STR_PACMAN);
		frame.add(game);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		game.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
			player.down = false;
			player.left = false;
			player.right = false;
		}
			
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
			player.up = false;
			player.left = false;
			player.right = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.up = false;
			player.down = false;
			player.left = true;
			player.right = false;
		}
			
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			player.up = false;
			player.down = false;
			player.left = false;
			player.right = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
			player.up = false;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			player.down = false;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			player.left = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			player.right = false;

	}

}
