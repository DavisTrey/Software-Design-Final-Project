package engine.gridobject;

import java.util.List;

import util.Constants;
import engine.dialogue.ConversationManager;
import engine.dialogue.NPCResponseNode;
import engine.gridobject.person.Player;
import engine.item.Item;
import engine.item.Pickupable;
import engine.state.DialogueState;

public class Barrier extends GridObject {
	
	public Barrier(String image, int numTilesWidth, int numTilesHeight) {
		super(image, numTilesWidth, numTilesHeight);
	}

	public Barrier(List<Object> list) {
		super((String) list.get(Constants.IMAGE_CONST), (int) ((Double) list.get(Constants.WIDTH_CONST)).intValue(), (int) ((Double) list.get(Constants.HEIGHT_CONST)).intValue());
	}
	
	public void displayAlertBox(Player p, Pickupable it) {
		NPCResponseNode n = new NPCResponseNode(this, "You found: " + it.toString() + "!");
		System.out.println("Item found alert");
		ConversationManager conversation = new ConversationManager(p, this, n);
		p.setState(new DialogueState(conversation));
		super.setInteractionBox(conversation);
	}

}
