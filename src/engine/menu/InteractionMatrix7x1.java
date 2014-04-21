package engine.menu;

import engine.dialogue.InteractionBox;
import engine.dialogue.InteractionMatrix;
import engine.dialogue.MatrixNode;

/**
 * This is a class that takes MatrixNodes and organizes them in such a way that
 * a user can "select" different options. The idea is that this can be used for
 * any sort of backend system for a display that allows a user to choose from a
 * set of options.
 * 
 */

public class InteractionMatrix7x1 extends InteractionMatrix {
	
	private InteractionBox[] myInteractionBoxes;

	public InteractionMatrix7x1() {
		selectedNodeX = 0;
		selectedNodeY = 0;
		myNodes = new MatrixNode[1][7];
		myXDimension = 1;
		myYDimension = 7;
		myInteractionBoxes = new InteractionBox[7];
	}

	/**
	 * Allows user to toggle up in the select box, selecting the option above
	 * the current option
	 */
	public void moveUp() {
		if (selectedNodeY != 0) {
			selectedNodeY--;
		} else {
			selectedNodeY = 6;
		}
	}

	/**
	 * Allows user to toggle down in the select box, selecting the option below
	 * the current option
	 */
	public void moveDown() {
		if (selectedNodeY != 6) {
			selectedNodeY++;
		} else {
			selectedNodeY = 0;
		}
	}

	/**
	 * Allows user to toggle left in the select box, selecting the option to the
	 * left of the current option
	 */
	public void moveLeft() {
	}

	/**
	 * Allows user to toggle right in the select box, selecting the option to
	 * the right of the current option
	 */
	public void moveRight() {
	}
	
	public void setManager(InteractionBox ib, int index){
		myInteractionBoxes[index] = ib;
	}
}
