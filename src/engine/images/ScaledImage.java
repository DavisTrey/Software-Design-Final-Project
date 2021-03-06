package engine.images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

import util.Constants;

public class ScaledImage {
	private int myWidth;
	private int myHeight;
	private String myFile;

	public ScaledImage(int width, int height, String filename) {
		myWidth = width;
		myHeight = height;
		myFile = filename;
	}

	/**
	 * Scale image to a specified size.
	 * 
	 * @return the image
	 */
	public Image scaleImage() {
		BufferedImage bi = null;
		ImageIcon ii = null;
		try {

			ii = new ImageIcon(this.getClass().getClassLoader()
					.getResource(myFile));
		} catch (Exception e) {
			int i = myFile.lastIndexOf('/');
			if ((i != -1) && (i + 1 < myFile.length())) {
				String f = myFile.substring(i + 1);
				ii = new ImageIcon(this.getClass().getClassLoader()
						.getResource(Constants.IMAGEPATH + f));
			} else {
				File f = new File("./src/" + Constants.IMAGEPATH + myFile);
				if (f.exists()) {
					ii = new ImageIcon("./src/" + Constants.IMAGEPATH + myFile);
				} else {
					ii = new ImageIcon(this.getClass().getClassLoader()
					.getResource(Constants.GRIDOBJECTPATH + myFile));
				}
			}

		}
		bi = new BufferedImage(myWidth, myHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) bi.createGraphics();

		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY));

		g2d.drawImage(ii.getImage(), 0, 0, myWidth, myHeight, null);

		return bi;

	}
}
