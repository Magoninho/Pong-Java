package com.mago;

import java.awt.*;

public class Player {

	public int x, y;
	public float velX = 0;
	public int width, height;
	public boolean right, left;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 60;
		this.height = 20;
	}
	public void tick(double deltaTime) {
		x += velX * deltaTime;
		x += velX * deltaTime;
		if (x+width > Game.WIDTH) {
			x = Game.WIDTH - width;
		}
		if (x < 0) {
			x = 0;
		}
	}

	public void resetPosition() {
		x = (Game.WIDTH/2) - 30;
		y = Game.HEIGHT-20;
	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
}
