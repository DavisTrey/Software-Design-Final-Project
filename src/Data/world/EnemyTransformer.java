package Data.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Constants;
import authoring.gameObjects.EnemyData;
import authoring.gameObjects.WeaponData;
import authoring.gameObjects.WorldData;
import engine.gridobject.GridObject;
import engine.gridobject.person.Enemy;
import engine.item.Weapon;

/**
 * @author Sanmay Jain
 */
public class EnemyTransformer implements Transformer {
	Enemy myEnemy;
	EnemyData myEnemyData;
	WorldUtil myWorldUtil;
	List<WeaponData> myWeaponDataList;

	public EnemyTransformer(GridObject g) {
		myEnemy = (Enemy) g;
		myWorldUtil = new WorldUtil();
		myWeaponDataList = new ArrayList<WeaponData>();
	}

	@Override
	public void transform() {
		String[] eAnimImages = myEnemy.getAnimImages();	
		Map<String,Integer> attributeValues1 = new HashMap<String,Integer>();
		for (String key : myEnemy.getStatsMap().keySet()) {
			attributeValues1.put(key,  myEnemy.getStatsMap().get(key).getValue());
		}
		
		List<Weapon> eWeapons = myEnemy.getWeaponList();
		String[] eWeps = myWorldUtil.getWeaponNames(eWeapons);
		
		for (Weapon w : myEnemy.getWeaponList()) {
			WeaponTransformer wt = new WeaponTransformer(w);
			wt.transform();
			WeaponData weaponData = wt.getTransformedData();
			if (weaponData != null) {
				myWeaponDataList.add(weaponData);
			}
		}
		
		String spriteName = myWorldUtil.getSpriteName(myEnemy.getImageFile());
		
	    myEnemyData = new EnemyData(myEnemy.getX()/Constants.TILE_SIZE,
			    myEnemy.getY()/Constants.TILE_SIZE,
				spriteName,
				myEnemy.toString(),
				attributeValues1,
				eWeps,
				3, 
	            myEnemy.getMoney(),
	            myEnemy.getExperience());
		
		myEnemyData.setImages(eAnimImages);
		myEnemyData.setMyMoney(myEnemy.getMoney());
		myEnemyData.setMyExperience(myEnemy.getExperience());

	}
	
	public EnemyData getTransformedData() {
		return myEnemyData;
	}
	
	public List<WeaponData> getWeaponDataList() {
		return myWeaponDataList;
	}
}
