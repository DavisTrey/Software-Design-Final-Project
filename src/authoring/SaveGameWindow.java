package authoring;

import javax.swing.*;

import authoring.features.FeatureManager;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.*;

/**
 * Class that handles the creation of the "Save Game" Window
 * @author Pritam M, Richard Cao, Davis Treybig, Jacob Lettie
 *
 */
public class SaveGameWindow extends JFrame{

    private JTextField saveGameField;
    private JButton saveButton;

    public SaveGameWindow(){
        super("Saving Game:");
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,1));
        buildPanel();
        pack();
        setVisible(true);
    }
    /**
     * Builds the GUI
     */
    private void buildPanel(){
        saveGameField = new JTextField(10);
        saveButton=new JButton("Save");
        saveButton.addActionListener(new SaveButton());
        
        JLabel NameLabel =  new JLabel("What would you like to name your saved game?");

        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        add(panel1);
        add(panel2);
        add(panel3);
        panel1.add(NameLabel);
        panel2.add(saveGameField);
        panel3.add(saveButton);
 }
    /**
     * Validates user input
     * @return
     */
    private boolean validateText(){
    	return !saveGameField.getText().equals("");
    }
    /**
     * Action listener for the save Button that handles that actual storing of the game
     */
    private class SaveButton implements ActionListener{
        public void actionPerformed(ActionEvent e){
        		if(validateText()){
        			FeatureManager.getDataManager().storeWorldData(saveGameField.getText(), FeatureManager.getWorldData());
        			dispose();
        		}
        		else{
        			JOptionPane pane=new JOptionPane();
        			pane.showMessageDialog(new JFrame(), "Please enter a valid file name", "Invalid File Name", JOptionPane.WARNING_MESSAGE);
        		}
        }
    }
}
