package com.mago;

import java.awt.*;

public class Enemy {

	public double x, y;
	public int width, height;
	public int score = 0;

	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 60;
		this.height = 10;
	}

	public void tick(double deltaTime) {

		switch (Game.difficulty) {
			case EASY:
				if (Game.ball.y < Game.HEIGHT / 2.0) {
					if (Game.ball.x - width / 2.0 > x) {
						x += 2.5 * deltaTime;
					}
					if (Game.ball.x - width / 2.0 < x) {
						x -= 2.5 * deltaTime;
					}
				}
				break;
			case MEDIUM:
				if (Game.ball.y < Game.HEIGHT / 2.0) {
					if (Game.ball.x - width / 2.0 > x) {
						x += 4 * deltaTime;
					}
					if (Game.ball.x - width / 2.0 < x) {
						x -= 4 * deltaTime;
					}
				}
				break;
			case IMPOSSIBLE:
				x = (Game.ball.x - this.width / 2.0);
				break;
		}

		// screen bounds checking
		if (x+width > Game.WIDTH) {
			x = Game.WIDTH - width;
		}
		if (x < 0) {
			x = 0;
		}
	}

	public void resetPosition() {
		x = (Game.WIDTH/2) - 30;
		y = 10;
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
	}
}
