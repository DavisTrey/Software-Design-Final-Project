package engine.gridobject.person;

import engine.state.WalkAroundState;

public class Movement1 extends Movement{

	/**
	 * Instantiates a new back and forth movement.
	 *
	 * @param npc the npc
	 * @param player the player
	 */
	public Movement1(NPC npc, Player player) {
		super(npc, player);


	}

	@Override
	public void move() {
		moveBackAndForth();
	}

	public void moveBackAndForth(){
		if(getNPC().getPosition()[0]>=getNPC().getStartX()+75)
			getNPC().setSpeed(getNPC().getSpeed() * -1);
		if(getNPC().getPosition()[0]<=getNPC().getStartX()-75)
			getNPC().setSpeed(getNPC().getSpeed() * -1);
		getNPC().setDX(getNPC().getSpeed());
	}
}
