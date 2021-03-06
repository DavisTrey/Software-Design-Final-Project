package authoring;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.*;

import authoring.features.Feature;

/**
 * Class that manages the various panels and frames in the authoring environment
 * @author Pritam M, Richard Cao, Davis Treybig, Jacob Lettie
 *
 */
public class AuthoringView {
	public static final String MENU_BAR = "menu bar";
	private static JFrame myFrame;
	private Map<String, JComponent> myPanels;
	private List<Feature> myFeatures;
	
	public AuthoringView(List<Feature> features, boolean exitOnClose){
		myFeatures = features;
		instantiateObjects();
		if(exitOnClose)
			myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addAllFeatures();
		setUpFrame();
	}
	private void instantiateObjects() {
        myPanels = new HashMap<String, JComponent>();
		addWorkspaceObjects();
	}
	private void addWorkspaceObjects(){
		myFrame = new JFrame("OOGASalad Authoring");
		myPanels=new HashMap<String, JComponent>();
		myFrame.setJMenuBar((JMenuBar)myPanels.get(MENU_BAR));
		instantiatePanels();
	}
	private void instantiatePanels() {
		myPanels.put(BorderLayout.NORTH, new JPanel());
		myPanels.put(BorderLayout.SOUTH, new JPanel());
		myPanels.put(BorderLayout.WEST, new JPanel());
		myPanels.put(BorderLayout.EAST, new JPanel());
		myPanels.put(BorderLayout.CENTER, new JPanel());
		myPanels.put(MENU_BAR, new JMenuBar());
	}
	private void addAllFeatures() {
		for(Feature f: myFeatures){
			addFeature(f);
		}
	}
	private void addFeature(Feature f) {
		for(JComponent j: f.getFeature().keySet()){
			myPanels.get(f.getFeature().get(j)).add(j);
		}
	}
	private void setUpFrame() {
		myFrame.setJMenuBar((JMenuBar)myPanels.get(MENU_BAR));
		for(String s: myPanels.keySet()){
			if(myPanels.get(s) instanceof JPanel)
				myFrame.add(myPanels.get(s), s);
		}
		myFrame.pack();
		myFrame.setVisible(true);
	}
	public void close() {
		myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
	}
	public JFrame getFrame() {
		return myFrame;
	}
}
