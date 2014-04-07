package authoring;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SaveGridObjectFeature extends Feature{
	private JButton createButton;
	private GridObjectCreation mySuperFeature;
	public SaveGridObjectFeature(GridObjectCreation gridObjectCreation){
		mySuperFeature = gridObjectCreation;
		createButton = new JButton("Create GridObject!");
		createButton.addActionListener(new CreateGridObjectListener());
		myComponents.put(createButton, BorderLayout.EAST);
	}
	private class CreateGridObjectListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			mySuperFeature.getData().setX(((GridObjectCoordinateFeature)(mySuperFeature.getFeature("GridObjectCoordinateFeature"))).getX());
			mySuperFeature.getData().setY(((GridObjectCoordinateFeature)(mySuperFeature.getFeature("GridObjectCoordinateFeature"))).getY());
			mySuperFeature.getData().setSteppable(((SteppableFeature)(mySuperFeature.getFeature("SteppableFeature"))).isSteppable());
			if(mySuperFeature.getData().isDefined()){
				mySuperFeature.getView().close();
			}
		}
	}
}
