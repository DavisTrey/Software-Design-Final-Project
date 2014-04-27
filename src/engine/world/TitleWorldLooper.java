package engine.world;

import GameView.TitleManager;
import engine.dialogue.DialogueDisplayControl;
import engine.state.TitleState;

public class TitleWorldLooper extends GameLooper {

	private TitleWorld myWorld;
	private TitleManager myTM;
	private boolean isGameLoaded;
	

	public TitleWorldLooper(TitleWorld currentWorld) {
		super(currentWorld);
		myWorld = (TitleWorld) getWorld();
		myTM = new TitleManager(myWorld.getPlayer());
		myWorld.getPlayer().setDialogueDisplayControl(
				new DialogueDisplayControl(myWorld));
		myWorld.getPlayer().setInteractionBox(myTM);
		myWorld.getPlayer()
				.setState(new TitleState(myWorld.getPlayer(), myTM));
		
		isGameLoaded = myTM.getIsGameLoaded();
		
	}

	@Override
	public World doLoop() {
		isGameLoaded =myTM.getIsGameLoaded();
		if(isGameLoaded){
			// Load new world
		}
		return null;
	}

}
