package authoring.features;

/**
 * @ Pritam M.
 * @ Jacob L.
 * */

import javax.swing.*;
import authoring.gameObjects.BarrierCreation;
import authoring.gameObjects.DoorCreation;
import authoring.gameObjects.EncounterCreation;
import authoring.gameObjects.HealerCreation;
import authoring.gameObjects.ItemWeaponCreation;
import authoring.gameObjects.LabelsCreation;
import authoring.gameObjects.NPCCreation;
import authoring.gameObjects.PlayerEnemyCreation;
import authoring.gameObjects.ShopkeeperCreation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGameComponents extends Feature implements ActionListener {

    private String[] buttonNames = {"Item/Weapon","Player/Enemy", "Door", "Barrier", "NPC", "EncounterTile",
            "Arena Labels", "Healer", "Shopkeeper"};
    private ItemWeaponCreation itemWeaponCreation;
    private PlayerEnemyCreation playerEnemyCreation;
    private DoorCreation doorCreation;
    private BarrierCreation barrierCreation;
    private NPCCreation npcCreation;
    private EncounterCreation encounterCreation;
    private LabelsCreation labelsCreation;
    private HealerCreation healerCreation;
    private ShopkeeperCreation shopkeeperCreation;
    private JFrame frame;


    public AddGameComponents(){
        JButton add = new JButton("+");
        add.setActionCommand("add");
        add.addActionListener(this);
        myComponents.put(add, BorderLayout.SOUTH);
        itemWeaponCreation = new ItemWeaponCreation();
        playerEnemyCreation = new PlayerEnemyCreation();
        barrierCreation=new BarrierCreation();
        doorCreation=new DoorCreation();
        npcCreation=new NPCCreation();
        encounterCreation = new EncounterCreation();
        labelsCreation = new LabelsCreation();
        healerCreation = new HealerCreation();
        shopkeeperCreation = new ShopkeeperCreation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("add".equals(e.getActionCommand())){
            showCreationOptions();
        } else if("item/weapon".equals(e.getActionCommand())){
            itemWeaponCreation.creationPanel();
            frame.dispose();
        } else if("player/enemy".equals(e.getActionCommand())){
            playerEnemyCreation.creationPanel();
            frame.dispose();
        } else if("barrier".equals(e.getActionCommand())){
        	barrierCreation.creationPanel();
        	frame.dispose();
        } else if("door".equals(e.getActionCommand())){
        	doorCreation.creationPanel();
        	frame.dispose();
        } else if("npc".equals(e.getActionCommand())){
        	npcCreation.creationPanel();
        	frame.dispose();
        } else if("encountertile".equals(e.getActionCommand())){
        	encounterCreation.creationPanel();
        	frame.dispose();
        } else if("arenalabels".equals(e.getActionCommand())){
            labelsCreation.creationPanel();
            frame.dispose();
        } else if("healer".equals(e.getActionCommand())){
            healerCreation.creationPanel();
            frame.dispose();
        } else if("shopkeeper".equals(e.getActionCommand())){
            shopkeeperCreation.creationPanel();
            frame.dispose();
        }
        
    }

    private void showCreationOptions() {

        FeatureManager.getWeaponItemViewer().iterateWeaponsAndItems();
        frame=new JFrame();
        frame.setLocationRelativeTo(null);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.PAGE_AXIS));
        for(int i = 0; i<buttonNames.length; i++){
            JButton b = new JButton(buttonNames[i]);
            String command = buttonNames[i].replaceAll("\\s+","").toLowerCase();
            b.setActionCommand(command);
            b.addActionListener(this);
            buttonPanel.add(b);
        }
        frame.add(buttonPanel);
        frame.pack();
        frame.setVisible(true);

    }
}
