package engine.world;

import engine.gridobject.GridObject;

public class Tile {


	private double myWidth;
	private double myHeight;
	private GridObject myObject;


	/**
	 * Instantiates a new tile.
	 *
	 * @param x the width of the tile.
	 * @param y the height of the tile
	 */
	public Tile(double x, double y) {
		myWidth = x;
		myHeight = y;
	}
	
	
	/**
	 * Sets the tile object.
	 *
	 * @param sprite the new tile object
	 */
	public void setTileObject(GridObject obj){
		myObject = obj;
		
	}
	
	
	

}