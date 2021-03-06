package engine.dialogue;

import engine.menu.nodes.MenuNode;
import engine.world.TitleWorld;

/**
 * This is a class that takes MatrixNodes and organizes them in such a way that
 * a user can "select" different options. The idea is that this can be used for any sort of
 * backend system for a display that allows a user to choose from a set of options.
 * 
 */

public abstract class InteractionMatrix {
	
	protected int selectedNodeX;
	protected int selectedNodeY;
	protected int myXDimension;
	protected int myYDimension;
	
	protected MatrixNode[][] myNodes;
	protected TitleWorld[][] myTitleWorldNodes;
	protected String myGame;
	
	public void setNode(MatrixNode mNode, int x, int y) {
		myNodes[x][y] = mNode;
	}
	
	
	
	/**
	 * Allows access to the currently highlighted/selected option in the menu
	 * @return currently selected node
	 */
	public MatrixNode getCurrentNode() {
		return myNodes[selectedNodeX][selectedNodeY];
	}
	
	public MatrixNode getNode(int i, int j) {
		return myNodes[i][j];
	}
	
	public int[] getSelectedNodeLocation() {
		int[] ret = {selectedNodeX, selectedNodeY};
		return ret;
	}
	
	/**
	 * Allows user to toggle up in the select box.
	 */
	public abstract void moveUp();

	/**
	 * Allows user to toggle down in the select box.
	 */
	public abstract void moveDown();
	
	/**
	 * Allows user to toggle left in the select box.
	 */
	public abstract void moveLeft();
	
	/**
	 * Allows user to toggle right in the select box.
	 */
	public abstract void moveRight();
	
	public int[] getDimension() {
		int[] dimensions = {myXDimension,myYDimension};
		return dimensions;
	}
	
	
}
