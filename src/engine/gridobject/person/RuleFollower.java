package engine.gridobject.person;

import java.awt.Rectangle;
import java.util.List;

import engine.gridobject.GridObject;
import engine.gridobject.item.Item;
import engine.gridobject.item.Weapon;
import engine.images.SpriteSheet;

public class RuleFollower extends GridObject {

	private List<Item> myItems;
	private double mySpeed;
	private double myDX=0;
	private double myDY=0;
	private int myMaxX;
	private int myMinX;
	private int myMaxY;
	private int myMinY;
	protected Weapon myWeapon;
	//up=0, right=1, down=2, left=3
	private int myFacing=2;
	private int count = 0;
	private String currentImageFile;
	
	public RuleFollower(String[] animImages, double speed, int numTilesWidth, int numTilesHeight) {
		super(animImages, numTilesWidth, numTilesHeight);
		mySpeed = speed;
		resetMax();
		myWeapon = null;
		currentImageFile=getAnimImages()[2];
	}
	
	private boolean isAnim(String[] animImages) {
		return animImages.length == 12;
	}
	
	public void setMaxX(int maxX){
		myMaxX=maxX;
	}
	public void setMinX(int minX){
		myMinX=minX;
	}
	public void setMaxY(int maxY){
		myMaxY=maxY;
	}
	public void setMinY(int minY){
		myMinY=minY;
	}
	public void resetMax(){
		myMaxX = myMaxY = Integer.MAX_VALUE;
		myMinX = myMinY = Integer.MIN_VALUE;
	}
	@Override
	public void move() {
		updateFacing();
		if(!(getX()+getDX()>myMaxX) && !(getX()+getDX()-getWidth()<myMinX))
			incrementX(getDX());
		if(!(getY()+getDY()+getHeight()>myMaxY) && !(getY()+getDY()<myMinY))
			incrementY(getDY());
		resetMax();
		uniqueMove();
		
	}

	private void updateFacing() {
		String imageName=currentImageFile;
		count++;
		if(count == 50)
			count = 0;
		if(getDX()>0){
			myFacing=1;
			imageName = switchAnim(3, 4, 5, getAnimImages());
		}
		else if(getDX()<0){
			myFacing=3;
			imageName = switchAnim(9, 10, 11, getAnimImages());
		}
		else if(getDY()>0){
			myFacing=2;
			imageName = switchAnim(6, 7, 8, getAnimImages());

		}
		else if(getDY()<0){
			myFacing=0;
			imageName = switchAnim(0, 1, 2, getAnimImages());
		}
		setImage(imageName);
		currentImageFile=imageName;
	}
	
	private String switchAnim(int still, int start, int end, String[] anim){
//		System.out.println(anim.length);
		if(isAnim(anim)) {
			if(count < 25)
				return anim[start];
			else
				return anim[end];
		}
		else {
			return anim[still];
		}
	}
	
	public void addWeapon(Weapon weapon){
	}

	public void addItem(Item it) {
		getItems().add(it);
	}
	
	public List<Item> getItems() {
		return myItems;
	}

	public void setMyItems(List<Item> items) {
		myItems = items;
	}

	public void removeItem(Item it) {
		for (Item current : getItems()) {
			if (current.getName().equals(it.getName())) {
				getItems().remove(current);
			}
		}
	}

	public double getDY() {
		return myDY;
	}

	public void setDY(double myDY) {
		this.myDY = myDY;
	}

	public double getSpeed() {
		return mySpeed;
	}

	public void setSpeed(double mySpeed) {
		this.mySpeed = mySpeed;
	}

	public double getDX() {
		return myDX;
	}

	public void setDX(double myDX) {
		this.myDX = myDX;
	}
	public int getFacing(){
		return myFacing;
	}
	


}
