package com.mago;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import static com.mago.Game.setDificulty;
import static com.sun.java.accessibility.util.AWTEventMonitor.removeKeyListener;

public class Menu implements KeyListener {

	public String gameTitle = "Pong";
	public String menu = "Press enter to start";
	public String[] menu2 = {
			"Easy",
			"Medium",
			"Impossible"
	};
	private int selectedOption = 0;
	public Menu() {
	}

	private void centerString(Graphics g, Rectangle r, String s,
							 Font font) {
		FontRenderContext frc =
				new FontRenderContext(null, true, true);

		Rectangle2D r2D = font.getStringBounds(s, frc);
		int rWidth = (int) Math.round(r2D.getWidth());
		int rHeight = (int) Math.round(r2D.getHeight());
		int rX = (int) Math.round(r2D.getX());
		int rY = (int) Math.round(r2D.getY());

		int a = (r.width / 2) - (rWidth / 2) - rX;
		int b = (r.height / 2) - (rHeight / 2) - rY;

		g.setFont(font);
		g.drawString(s, r.x + a, r.y + b);
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		centerString(g, new Rectangle((Game.WIDTH/2), 100, 0, 0), gameTitle, g.getFont().deriveFont(40f));
		centerString(g, new Rectangle((Game.WIDTH/2), 200, 0, 0), "Select Dificulty:", g.getFont().deriveFont(16f));

		g.fillRect((Game.WIDTH/3) - 10, ((Game.HEIGHT/2) + selectedOption * 16) - 2, 10, 4);
		g.fillRect((Game.WIDTH) - (Game.WIDTH/3) - 5, ((Game.HEIGHT/2) + selectedOption * 16) - 2, 10, 4);
		centerString(g, new Rectangle((Game.WIDTH/2), Game.HEIGHT/2, 0, 0), menu2[0], g.getFont().deriveFont(16f));
		centerString(g, new Rectangle((Game.WIDTH/2), (Game.HEIGHT/2) + 16, 0, 0), menu2[1], g.getFont().deriveFont(16f));
		centerString(g, new Rectangle((Game.WIDTH/2), (Game.HEIGHT/2) + 32, 0, 0), menu2[2], g.getFont().deriveFont(16f));
	}

	public void nextOption() {
		if (this.selectedOption < menu2.length-1)
			this.selectedOption++;
		else
			this.selectedOption = 0;
	}
	public void previousOption() {
		if (this.selectedOption > 0)
			this.selectedOption--;
		else
			this.selectedOption = menu2.length-1;
	}

	public int getSelectedDificulty() {
		return this.selectedOption;
	}

	public void tick() {
		// todo: animation stuff
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.previousOption();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.nextOption();
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// if the game state is the menu state
			if (Game.currentGameState == Game.MENU_STATE) {
				// set the dificulty of the game to be the selected one from this menu
				Game.setDificulty(Dificulties.values()[getSelectedDificulty()]);
				// change the game state to the game to begin
				Game.currentGameState = Game.GAME_STATE;
				// remove this key listener
				removeKeyListener(this);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
