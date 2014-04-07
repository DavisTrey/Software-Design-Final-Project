package authoring;

import java.io.File;

public class GridObjectData {

	private String myImageName;
	private Boolean isSteppable;
	private Boolean isTalkable;
	private int x;
	private int y;
	
	public GridObjectData(){
		x = -1;
		y = -1;
	}
//	public GridObjectData(TileData td, boolean step, boolean talk, String s) {
//		myTile = td;
//		td.addGridObjectData(this);
//		isSteppable = step;
//		isTalkable = talk;
//		myImageName = s;
//	}
	
	public Boolean isSteppable(){
		return isSteppable;
	}
	public Boolean isTalkable(){
		return isTalkable;
	}
	public String getImageName(){
		return myImageName;
	}
	public void setX(int xx){
		x=xx;
	}
	public void setY(int yy){
		y=yy;
	}
	
	public void setSteppable(boolean b){
		isSteppable = b;
	}
	public void setTalkable(boolean b){
		isTalkable = b;
	}
	public void setImageName(String s){
		myImageName = s;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public boolean isDefined(){
		if(x==-1||y==-1){
			return false;
		}
		return true;
	}

}
