package engine.gridobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.util.Map;

import javax.swing.ImageIcon;

import engine.Statistic;
import engine.collision.CollisionHandler;

public abstract class GridObject{

	private int myWidth;
	private int myHeight;
	protected int myX;
	protected int myY;
	protected CollisionHandler myCollisionHandler;
	protected String myImageFile;
	protected Image myImage;
	private Map<String,Statistic> myStatsMap;
	private boolean doesHarm = false;
	String facing = "down";
	
	public GridObject(String image) {
		myStatsMap = null;
		myCollisionHandler = null;
		myImageFile=image;
	}
	
	public String getImageFile(){
		return myImageFile;
	}
	public void setSize(int width, int height){
		myWidth=width;
		myHeight=height;
	}
	public void setPosition(int x, int y){
		myX=x;
		myY=y;
		System.out.println("x " + myX);
	}
	
	public int[] getPosition(){
		return new int[] {myX,myY};
	}
	public void setImage(String file) {
		Image img = scaleImage(myWidth,myHeight,file);
		myImage = img;
		
	}
	
	public void paint(Graphics2D g) {
//		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//                0.1f));
//		g.setBackground(C?olor.red);
		
		//g.setBackground(Color.red);
		//g.clearRect(myX, myY, myWidth, myHeight);
       // g.setBackground(Color.WHITE);
		//myImage = TransformGrayToTransparency(myImage);
		
		g.drawImage(myImage, myX, myY, null);
	}
	private Image TransformGrayToTransparency(Image myImage2)
	  {
	    ImageFilter filter = new RGBImageFilter()
	    {
	      public final int filterRGB(int x, int y, int rgb)
	      {
	        return (rgb << 8) & 0xFF000000;
	    	  
	      }
	    };

	    ImageProducer ip = new FilteredImageSource(myImage2.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	  }
	
	public void setCollisionHandler(CollisionHandler handler) {
		myCollisionHandler = handler;
	}
	public void addStatistic(Statistic stat) {
		myStatsMap.put(stat.getName(), stat);
	}
	
	public void addStatistic(String name, int value, int maxValue){
		myStatsMap.put(name,new Statistic(name,value,maxValue));
	}
	
	public Map<String,Statistic> getStatsMap(){
		return myStatsMap;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(myX, myY-myHeight, myWidth, myHeight);	
	}
	public boolean getDoesHarm(){
		return doesHarm;
	}
	public void setDoesHarm(boolean harm){
		doesHarm=harm;
	}
	public Image scaleImage(int WIDTH, int HEIGHT, String filename) {
		BufferedImage bi = null;
		try {
			ImageIcon ii = new ImageIcon(this.getClass().getResource(filename));
			bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints(new RenderingHints(
					RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY));
			g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bi;

	}
	
	public void move() {}; // default is to do nothing
	public void doCollision(GridObject o){};
	
}
