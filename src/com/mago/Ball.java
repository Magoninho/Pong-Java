package com.mago;

import java.awt.*;
import java.util.Random;

public class Ball {
	public double x, y;
	public int width, height;

	public double dx, dy;
	public double speed = 2.5;


	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 5;
		this.height = 5;

		generateDirections();
	}

	public void generateDirections() {
		int angle = (int)Math.floor(Math.random()*(135-45+1)+45); // generates a random angle between 45 and 135

		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}

	public void tick(double deltaTime) {

		if (x + (dx * speed) + width >= Game.WIDTH || x + (dx*speed) < 0) {
			dx *=-1;
		} else if (y >= Game.HEIGHT) {
			// enemy scores
			generateDirections();
			Game.player.resetPosition();
			Game.enemy.resetPosition();
			x = Game.WIDTH/2.0;
			y = Game.HEIGHT/2.0;
		} else if (y < 0) {
			// player scores
			generateDirections();
			Game.enemy.resetPosition();
			x = Game.WIDTH/2.0;
			y = Game.HEIGHT/2.0;
		}

		Rectangle bounds = new Rectangle((int)(x+(dx*speed)), (int)(y+(dy*speed)), width, height);

		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width, Game.enemy.height);

		if (bounds.intersects(boundsPlayer)) {
			generateDirections();
			y = boundsPlayer.y - height;
			dy*=-1;
			if (speed < 7.5)
				speed += 0.5;
		} else if (bounds.intersects(boundsEnemy)) {
			generateDirections();
			y = boundsEnemy.y + boundsEnemy.height;
			//dy*=-1;
			if (speed < 7.5)
				speed += 0.5;
		}

		x += dx*speed * deltaTime;
		y += dy*speed * deltaTime;
	}

	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)x, (int)y, width, height);
	}
}
