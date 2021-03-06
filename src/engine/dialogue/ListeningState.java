package engine.dialogue;

import engine.state.WalkAroundState;


public class ListeningState implements ConversationState{

	public ListeningState() {}

	@Override
	public void doState(ConversationManager cm) {
		
		boolean newNodes = true;
		boolean itemNode = false;
		
		if (hasItemNode(cm)) {
			for (UserQueryNode node : cm.getCurrentNPCResponseNode().getUserQueryNodes()) {
				if (node != null && node.hasItem()) {
					cm.setCurrentNPCResponseNode(node.getMyNPCResponseNode());
					cm.setTextToBeDisplayed(cm.getCurrentNPCResponseNode().getDialogue());
					itemNode = true;
					break;
				}
			}
		} else {
			newNodes = createAvailableResponses(cm);
		}
		
		
		if (!newNodes) {
			cm.getPlayer().setState(new WalkAroundState(cm.getPlayer()));
			cm.getPlayer().getDialogueDisplayControl().setInteractionBox(new TransparentDisplayer());
		} else if (itemNode) {
			cm.setCurrentState(new ListeningState());
		} else {
			cm.setCurrentState(new RespondingState());
			cm.setResponding(true);
		}
		
		
	}
	
	private boolean createAvailableResponses(ConversationManager cm ) {
		int count = 0;
		if (cm.getCurrentNPCResponseNode().getUserQueryNodes().isEmpty()) return false;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if(cm.getCurrentNPCResponseNode().getUserQueryNodes().size()>count)
					cm.getMatrix().setNode(cm.getCurrentNPCResponseNode().getUserQueryNodes().get(count), j, i);
				else{
					cm.getMatrix().setNode(null, j, i);
				}
				count++;
			}
		}	
		cm.setCurrentUserQueryNode((UserQueryNode) cm.getMatrix().getCurrentNode());
		return true;
	}

	private boolean hasItemNode(ConversationManager cm) {
		for (UserQueryNode node : cm.getCurrentNPCResponseNode().getUserQueryNodes()) {
			if (node != null && node.hasItem()) {
				return true;
			}
		}
		return false;
	}

}
