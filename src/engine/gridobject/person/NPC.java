package engine.gridobject.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.Constants;
import engine.Statistic;
import engine.dialogue.ConversationManager;
import engine.dialogue.DialogueDisplayControl;
import engine.dialogue.NPCResponseNode;
import engine.dialogue.TransparentDisplayer;
import engine.dialogue.UserQueryNode;
import engine.item.Item;
import engine.item.KeyItem;
import engine.item.StatBuffer;
import engine.state.DialogueState;
import authoring.UserQueryNodeData;
import authoring.gameObjects.ItemData;
import authoring.gameObjects.NPCResponseNodeData;

public class NPC extends Person {

	protected List<String> myDialogue;
	private Movement myMovement;
	private Player myPlayer;
	private NPCResponseNode myResponseNode;

	/**
	 * Instantiates a new npc.
	 * 
	 * @param image
	 *            the image
	 * @param speed
	 *            the speed
	 * @param numTilesWidth
	 *            the width in tiles
	 * @param numTilesHeight
	 *            the height in tiles
	 * 
	 * @param movementType
	 *            the movement type. 1=move back and forth 2=follow player if it
	 *            gets close 3=stand still
	 * @param player
	 *            the player
	 */
	public NPC(String[] animImages, String name, double speed,
			int numTilesWidth, int numTilesHeight, int movementType,
			Player player) {
		super(animImages, name, speed, numTilesWidth, numTilesHeight);
		myDialogue = new ArrayList<String>();
		myPlayer = player;
		myMovement = (Movement) Reflection.createInstance(
				"engine.gridobject.person.Movement" + movementType, this,
				player);

		myResponseNode = null;
	}

	public NPC(List<Object> list) {
		super((String[]) ((List<String>) list.get(Constants.IMAGE_CONST))
				.toArray(new String[12]), (String) list
				.get(Constants.NAME_CONST), 1, (int) ((Double) list
				.get(Constants.WIDTH_CONST)).intValue(), (int) ((Double) list
				.get(Constants.HEIGHT_CONST)).intValue());

		myDialogue = new ArrayList<String>();
		myPlayer = (Player) list.get(Constants.NPC_PLAYER_CONST);
		myMovement = (Movement) Reflection.createInstance(
				"engine.gridobject.person.Movement"
						+ (int) ((Double) list
								.get(Constants.NPC_MOVEMENT_CONST)).intValue(),
				this, myPlayer);
		myResponseNode = buildResponseTree(
				(NPCResponseNodeData) list.get(Constants.RESPONSE_ROOT_CONST),
				(Map<String, ItemData>) list.get(Constants.NPC_ITEMS_CONST));
	}

	public void setResponseNode(NPCResponseNode n) {
		myResponseNode = n;
	}

	/**
	 * Builds the response tree.
	 * 
	 * @param n
	 *            head NPCResponseNodeData
	 * @param items
	 *            map of item names to itemdata
	 * @return the root of the created NPC response node
	 */
	public NPCResponseNode buildResponseTree(NPCResponseNodeData n,
			Map<String, ItemData> items) {
		Item myItem = null;
		String i = n.getItem();
		if (i != null) {

			ItemData id = items.get(i);
			if (id.getMyIdentity().equals("KeyItem")) {
				myItem = new KeyItem(id.getItemImage(), id.getItemName());
			} else if (id.getMyIdentity().equals("StatBuffer")) {
				Map<String, Integer> valuesMap = id.getMyItemValues();
				String key = "health";
				Integer value = 10;
				Statistic stats = null;
				if ((valuesMap != null) && (valuesMap.size() > 0)) {
					for (String k : valuesMap.keySet()) {
						stats = new Statistic(k, valuesMap.get(k), 100);
						break;
					}
				} else {
					stats = new Statistic(key, value, 100);
				}
				myItem = new StatBuffer(id.getItemImage(), id.getItemName(),
						stats, 10);
			}
		}
		NPCResponseNode head = new NPCResponseNode(n.getString(), myItem);
		if (n.getChildren().size() != 0) {
			for (UserQueryNodeData u : n.getChildren()) {
				NPCResponseNode child = buildResponseTree(u.getChild(), items);
				head.addResponseNode(new UserQueryNode(myPlayer, u.getItem(), u
						.getString(), child));
			}
		}
		return head;
	}

	public Player getPlayer() {
		return myPlayer;
	}

	@Override
	public void uniqueMove() {
		myMovement.move();
	}

	/**
	 * How far from player.
	 * 
	 * @return how far the enemy is from the player
	 */
	public int howFarFromPlayer() {
		return getDistance(getX(), getY(), getPlayer().getX(), getPlayer()
				.getY());
	}

	/**
	 * Gets the distance.
	 * 
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param x2
	 *            the x2
	 * @param y2
	 *            the y2
	 * @return the distance
	 */
	private int getDistance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	@Override
	public void doAction() {
		doDialogue();
	}

	@Override
	public void doDialogue() {
		ConversationManager conversation = new ConversationManager(myPlayer,
				this, myResponseNode);

		myPlayer.setState(new DialogueState(conversation));
		super.setInteractionBox(conversation);
	}

	public NPCResponseNode getResponseNode() {
		return myResponseNode;
	}
}
