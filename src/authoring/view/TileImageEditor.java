package authoring.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import Data.ImageFile;

/**
 * Class that displays all usable TileImages so that the user can place them on Tiles in the authoring view
 * @author Pritam M, Richard Cao, Davis Treybig, Jacob Lettie
 * 
 */
public class TileImageEditor extends ImageEditor {
	
	public static final String DEFAULT_IMAGE_SAVE_EXTENSION=".jpg";
	public static final String IMAGE_TYPE="TileImage";

	public TileImageEditor() {
		super();
		myWindow = new JFrame("Tile Image Editor");
		myWindow.setLayout(new BorderLayout());
		myWindow.setBounds(900, 0, 360, 360);
		myWindow.getContentPane().add(scroll, BorderLayout.CENTER);

		myWindow.setVisible(true);
	}

	/**
	 * Adds all currently existing images to the TileImageEditor
	 */
	@Override
	public void addExistingImages() {
		List<ImageFile> imageList=m.getSavedImageMap();
		for(ImageFile image: imageList){
			if(image.getType().equalsIgnoreCase(IMAGE_TYPE)){
				addImage(image.getImage(), image.getName());
			}
		}
		
	}

}
