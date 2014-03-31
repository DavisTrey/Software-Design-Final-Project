package engine.gridobject;

import java.awt.event.KeyEvent;

import engine.world.Tile;

public class Player extends RuleFollower {

	public Player(String image, double speed) {
		super(image, speed);
		myItems = null;
	
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
			myDY = -mySpeed;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			myDY = mySpeed;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			myDX = mySpeed;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			myDX = -mySpeed;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP
				|| e.getKeyCode() == KeyEvent.VK_DOWN)
			myDY = 0;

		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_LEFT)
			myDX = 0;
	}
	
	
}
