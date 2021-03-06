package authoring.features;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import authoring.gameObjects.MapData;
import Data.FileLister;
import Data.MusicManager;

/**
 * Class that allows the user to upload music and choose which maps
 *	will play what song when the player is on that map
 * @author Pritam M, Richard Cao, Davis Treybig, Jacob Lettie
 *	
 */
public class AddMusicFeature extends Feature implements ActionListener, ListSelectionListener{

	private JFrame myWindow;
	private JButton musicButton;
	private JList availableMusic;
	private JButton uploadButton;
	protected DefaultListModel<String> model;
	private JScrollPane listContainer;
	private MusicManager m;

	public AddMusicFeature() {		
		musicButton = new JButton("Add Music");
		musicButton.setActionCommand("addmusic");
		musicButton.addActionListener(this);
		myComponents.put(musicButton, BorderLayout.SOUTH);
		uploadButton = new JButton("Upload New Song");
		uploadButton.addActionListener(this);
		model = new DefaultListModel<String>();
		addExisting();
		availableMusic = new JList<String>(model);
		availableMusic.addListSelectionListener(this);
		availableMusic.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		availableMusic.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		availableMusic.setVisibleRowCount(-1);
		listContainer = new JScrollPane(availableMusic);
		m = new MusicManager();

		myWindow = new JFrame("Music Editor");
		myWindow.setLayout(new BorderLayout());
		myWindow.getContentPane().add(listContainer, BorderLayout.CENTER);
		myWindow.getContentPane().add(uploadButton, BorderLayout.SOUTH);
		myWindow.pack();

	}
	private void addExisting(){
		FileLister f=new FileLister();
		List<String> files=f.getFileList(m.DEFAULT_MUSIC_FOLDER);
		for(String s: files){
			model.addElement(s);
			FeatureManager.getWorldData().saveSong(s, new File(m.DEFAULT_MUSIC_FOLDER+s));
		}
		
	}
	private void musicUploader(){
		JTextField name = new JTextField(10);
		JPanel panel = new JPanel();
		panel.add(name);
		int result = JOptionPane.showOptionDialog(null, panel, "Name Your Song",
				JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

		if(result == JOptionPane.OK_OPTION){
			if(name.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Complete required fields.", "Error Message",
						JOptionPane.ERROR_MESSAGE);
				musicUploader();
			}
			else
				chooseMusic(name.getText());
		}
	}

	private void chooseMusic(String fileName) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"wav", "WAV");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		File songFile;
		if(returnVal == JFileChooser.APPROVE_OPTION){
			songFile = chooser.getSelectedFile();

			try {
				File savedFile = m.storeMusicFile(fileName+m.DEFAULT_MUSIC_EXTENSION, songFile);
				FeatureManager.getWorldData().saveSong(fileName+m.DEFAULT_MUSIC_EXTENSION, savedFile);
				model.addElement(fileName+m.DEFAULT_MUSIC_EXTENSION);
				myWindow.pack();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "File failed to load, try again", "Error!",
						JOptionPane.ERROR_MESSAGE);
				chooseMusic(fileName);
			}
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("addmusic"))
			myWindow.setVisible(true);
		else
			this.musicUploader();
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		String name = model.get(availableMusic.getSelectedIndex());
		JOptionPane.showConfirmDialog(null, "Would you like to make " + name + " the song for this map?",
				"Confirm", JOptionPane.OK_OPTION);
		MapData cm = FeatureManager.getWorldData().getCurrentMap();
		cm.addSong(name);
	}

}
