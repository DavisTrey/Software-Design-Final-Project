package authoring.features;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import authoring.gameObjects.MapData;
import authoring.gameObjects.WorldData;
import authoring.view.Grid;

import java.util.*;
import java.awt.*;

/**
 * Class the handles the GUI grid window which contains map tiles. 
 * @author Pritam M, Richard Cao, Davis Treybig, Jacob Lettie
 *
 */
public class GridViewerFeature extends Feature{

	private WorldData wd;
	private JScrollPane myViewer;
	private JTabbedPane tabs;
	private Map<String, Grid> myGrids;
	private String mapName;
	private int row;
	private int col;

	public GridViewerFeature() {
		wd = FeatureManager.getWorldData();
		myGrids = new HashMap<String, Grid>();
		tabs = new JTabbedPane();
		tabs.addChangeListener(new TabbedPaneListener());
		myComponents.put(tabs, BorderLayout.CENTER);
		this.addMap(this.mapNamer());
	}

	public void tileRepaint(){
		for(Grid g : myGrids.values())
			g.tileRepaint();
	}
	public Grid getCurrentGrid(){
		return myGrids.get(mapName);
		
	}
	public void addMap(String s){
		if(myGrids.isEmpty()){
			mapName = s;		
			wd.setCurrentMap(mapName);
		}
		mapSize(s);		
		tabs.addTab(s, myViewer);	
		if(tabs.getTabCount() > 1 && tabs.getSelectedIndex() >= 0){
			tabs.setSelectedIndex(tabs.getSelectedIndex() + 1);
		}
			
	}
	public Grid getGrid(String s){
		return myGrids.get(s);
	}
	
	public void gridMaker(int height, int width, String name){
		Grid g= new Grid(height, width);
		myGrids.put(name, g);
		myViewer = new JScrollPane(myGrids.get(name));
		myViewer.setPreferredSize(new Dimension(592, 590));
	}
	
	public String mapNamer(){
		JPanel mn = new JPanel();
		JTextField nameEntry = new JTextField(20);
		mn.add(new JLabel("Name: "));
		mn.add(nameEntry);
		String name = "";
		int result = JOptionPane.showConfirmDialog(null, mn, "Name your map:", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			name = nameEntry.getText();
			return name;
		}
		else{
			JOptionPane.showMessageDialog(null, "Must name map. Please try again.", "Error Message",
                    JOptionPane.ERROR_MESSAGE);
			return mapNamer();
		}
	}
	
	public void mapSize(String s){
		JPanel mapSizer = new JPanel();
		JTextField rowEntry = new JTextField(5);
		JTextField colEntry = new JTextField(5);
		mapSizer.add(new JLabel("Rows: (>15)"));
		mapSizer.add(rowEntry);
		mapSizer.add(new JLabel("Columns: (>15)"));
		mapSizer.add(colEntry);

		int result = JOptionPane.showConfirmDialog(null, mapSizer, "Please enter the size of your map",
                JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			row = Integer.parseInt(rowEntry.getText());
			col = Integer.parseInt(colEntry.getText());

			if(row < 15 || col < 15){
				JOptionPane.showMessageDialog(null, "Number of rows or columns was not at least 15", "Map Sizing Error",
                        JOptionPane.ERROR_MESSAGE);
				mapSize(s);
				return;
			}
		}
		MapData md = new MapData(row, col);
		wd.addLevel(s, md);
		wd.setCurrentMap(s);
		gridMaker(row, col, s);
	}

	public class TabbedPaneListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			wd.setCurrentMap(tabs.getTitleAt(tabs.getSelectedIndex()));
			mapName=tabs.getTitleAt(tabs.getSelectedIndex());
		}
	}

}
