package engine.state;

import java.awt.event.KeyEvent;

import engine.Control;
import engine.dialogue.TransparentDisplayer;
import engine.gridobject.GridObject;
import engine.gridobject.person.Player;
import engine.menu.MenuManager;

public class MenuState extends AbstractState {
	
	private MenuManager myMenu;
	private Player myPlayer;
	
	public MenuState(Player p) {
		super();
		myPlayer = p;
	
	}
	


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == Control.UP){
			myMenu.moveCursorUp();
		}
		if (e.getKeyCode() == Control.DOWN){
			myMenu.moveCursorDown();
		}
		if (e.getKeyCode() == Control.RIGHT){
			myMenu.moveCursorRight();
		}
		if (e.getKeyCode() == Control.LEFT){
			myMenu.moveCursorLeft();
		}		
		if (e.getKeyCode() == Control.A) {
			myMenu.select();
			}
		if (e.getKeyCode() == Control.SPACE) {
			myPlayer.setState(new WalkAroundState(myPlayer));
			myPlayer.setInteractionBox(new TransparentDisplayer());
			//myMenu.toggleMenu();
		}
	}

}
