package com.vietanh.pacman.ui;

import javax.swing.JFrame;

import com.vietanh.pacman.commons.CommonConst;

public class PacmanUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public PacmanUI() {
		this.setTitle(CommonConst.STR_PACMAN);
		this.setSize(CommonConst.FRAME_WIDTH,CommonConst.FRAME_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public static void main(String[] args) {
		PacmanUI ui = new PacmanUI();
		ui.setVisible(true);
	}
	
}
