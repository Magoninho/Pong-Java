package com.mago;

import com.mago.input.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

	public static int SCALE = 3;
	public static int WIDTH = 120*SCALE;
	public static int HEIGHT = 160*SCALE;

	private Keyboard keyboard;
	private Thread thread;
	public boolean running = false;

//	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	public static Player player;
	public static Enemy enemy;
	public static Ball ball;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		player = new Player((Game.WIDTH/2) - 60/2, HEIGHT-20);
		enemy = new Enemy((WIDTH/2), 10);
		ball = new Ball((WIDTH/2), HEIGHT/2);

		keyboard = new Keyboard();

		addKeyListener(keyboard);
		this.setFocusable(true); // very important
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("Pong");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

		game.start();
	}

	public synchronized void start() {

		running = true;
		thread = new Thread(this);
		thread.start();

	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tick(double deltaTime) {
		keyboard.update();
		processInput();
		player.tick(deltaTime);
		enemy.tick(deltaTime);
		ball.tick(deltaTime);

	}

	public void render() {
		// idk why but this makes the input do not lag on linux
		Toolkit.getDefaultToolkit().sync();

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
//		Graphics g = layer.getGraphics();

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		player.render(g);
		enemy.render(g);
		ball.render(g);

//		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);

		g.dispose();
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {

				tick(delta);
				delta--;
			}
			render();
		}
		// TODO: stop();
	}

	public synchronized void processInput() {
		player.velX = 0;
		if (keyboard.right) {
			player.velX = 4;
		} else if (keyboard.left) {
			player.velX = -4;
		}
	}
}
