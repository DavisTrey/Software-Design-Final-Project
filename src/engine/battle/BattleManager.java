package engine.battle;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import util.Reflection;
import engine.battle.states.BattleState;
import engine.dialogue.AbstractManager;
import engine.dialogue.InteractionBox;
import engine.dialogue.InteractionMatrix2x2;
import engine.gridobject.person.Enemy;
import engine.gridobject.person.Player;
import engine.item.StatBuffer;

public class BattleManager extends AbstractManager implements InteractionBox{
	BattleCalculator myBattleCalculate;
	private Player myPlayer;
	private Enemy myEnemy;
	private BattleSelectorNode myAttackSelector;
	private BattleSelectorNode myBagSelector;
	private BattleSelectorNode myWeaponSelector;
	private BattleSelectorNode myRunSelector;
	private BattleSelectorNode myCurrentBattleSelector;
	private BattleExecutorNode myCurrentBattleExecutorNode;
	private static String myCurrentState="TopLevel";
	private Image myCurrentPlayerBattleImage;
	private Image myCurrentEnemyBattleImage;
	private String textToBeDisplayed="";
	private boolean ran=false;
	private boolean didDrop=false;
	String[] myLabels;
	private Map<String, Integer> myOldStats = new HashMap<String, Integer>();

	/**
	 * Instantiates a new battle manager.
	 *
	 * @param player the player
	 * @param enemy the enemy
	 * @param labels the labels for the battle. 4 must be included.
	 * 0=attack 1=weapons 2=bag 3=run
	 */
	public BattleManager(Player player, Enemy enemy, String[] labels){
		myPlayer = player;
		myEnemy=enemy;	
		myLabels = labels;
		setOriginalNodes();
		initializeChildrenNodes();
		myCurrentPlayerBattleImage=myPlayer.getBattleImage();
		myCurrentEnemyBattleImage = myEnemy.getBattleImage();
		saveOldStats(player);
	}
	private void saveOldStats(Player player) {
		for(String stat: player.getStatsMap().keySet()){
			myOldStats.put(stat, player.getStatsMap().get(stat).getValue());
		}
	}
	private void initializeChildrenNodes() {
		setAttackChildrenNodes(myAttackSelector);
		setWeaponChildrenNodes(myWeaponSelector);
		setBagChildrenNodes(myBagSelector);
		if(myEnemy.isRandom())setRunChildrenNodes(myRunSelector);
	}
	private void updateAttackList(){
		setAttackChildrenNodes(myAttackSelector);
	}
	private void setOriginalNodes(){
		myAttackSelector = new BattleSelectorNode(myLabels[0]);
		myBagSelector = new BattleSelectorNode(myLabels[2]);
		myWeaponSelector = new BattleSelectorNode(myLabels[1]);
		if(myEnemy.isRandom())myRunSelector = new BattleSelectorNode(myLabels[3]);
		getMatrix().setNode(myAttackSelector, 0, 0);
		getMatrix().setNode(myBagSelector, 1, 0);
		getMatrix().setNode(myWeaponSelector, 0, 1);
		if(myEnemy.isRandom())getMatrix().setNode(myRunSelector, 1, 1);
		myCurrentBattleSelector=(BattleSelectorNode) getMatrix().getCurrentNode();
	}

	private void setAttackChildrenNodes(BattleSelectorNode node){
		for(BattleExecutable attack : myPlayer.getCurrentWeapon().getAttackList()){
			setNextChild(node, attack);
		}
	}
	private void setNextChild(BattleSelectorNode node, BattleExecutable attack) {
		BattleExecutorNode executorNode = new BattleExecutorNode(attack);
		node.setChild(executorNode);
	}

	private void setWeaponChildrenNodes(BattleSelectorNode node){
		for(BattleExecutable weapon : myPlayer.getWeaponList()){
			setNextChild(node, weapon);
		}
	}

	private void setBagChildrenNodes(BattleSelectorNode node){
		for(BattleExecutable item : myPlayer.getItems()){
			if(item instanceof StatBuffer)setNextChild(node, item);
		}
	}

	private void setRunChildrenNodes(BattleSelectorNode node){
		BattleExecutable run = new Run();
		setNextChild(node, run);

	}

	@Override
	public void getNextText() {
		((InteractionMatrix2x2) getMatrix()).resetMatrixPosition();
		((BattleState) Reflection.createInstance("engine.battle.states."+myCurrentState)).doState(this);
	}
	public void setCurrentNode() {
		if(myCurrentState.equals("TopLevel")){
			myCurrentBattleSelector=(BattleSelectorNode) getMatrix().getCurrentNode();
		}
		else if(myCurrentState.equals("BottomLevel")){
			myCurrentBattleExecutorNode=(BattleExecutorNode) getMatrix().getCurrentNode();
		}
	}

	public Player getPlayer() {
		return myPlayer;
	}
	public void setCurrentTextToBeDisplayed(String text) {
		textToBeDisplayed=text;

	}
	public boolean didRun(){
		return ran;
	}
	public String getCurrentState(){
		return myCurrentState;
	}
	public void setCurrentState(String state){
		myCurrentState=state;
	}
	@Override
	public boolean isResponding() {
		return (myCurrentState=="TopLevel" || myCurrentState=="BottomLevel");	
	}
	@Override
	public String getTextToBeDisplayed() {
		return textToBeDisplayed;
	}
	public Image getCurrentPlayerBattleImage(){
		return myCurrentPlayerBattleImage;
	}
	public Image getCurrentEnemyBattleImage(){
		return myCurrentEnemyBattleImage;
	}
	public BattleSelectorNode getCurrentBattleSelector(){
		return myCurrentBattleSelector;
	}
	public void setCurrentBattleExecutorNode(BattleExecutorNode node){
		myCurrentBattleExecutorNode = node;
	}

	public BattleExecutorNode getCurrentBattleExecutorNode(){
		return myCurrentBattleExecutorNode;
	}
	public Enemy getEnemy() {
		return myEnemy;
	}
	public void backToTopOfBattle(){
		setOriginalNodes();
		initializeChildrenNodes();
		myCurrentState="TopLevel";
		myCurrentPlayerBattleImage=myPlayer.getBattleImage();
		myCurrentEnemyBattleImage=myEnemy.getBattleImage();
	}
	public void setPlayerBattleImage(Image image) {
		myCurrentPlayerBattleImage=image;
	}
	public void setEnemyBattleImage(Image image){
		myCurrentEnemyBattleImage=image;
	}
	public void resetStats(){
		for(String stat: myOldStats.keySet()){
			myPlayer.getStatsMap().get(stat).setValue(myOldStats.get(stat));
		}
	}
	public void setToWeaponSelector(){
		myCurrentBattleSelector=myWeaponSelector;
	}
	public boolean didDrop(){
		return didDrop;
	}
	public void setDidDrop(){
		didDrop=true;
	}
	public String[] getLabels(){
		return myLabels;
	}
}
