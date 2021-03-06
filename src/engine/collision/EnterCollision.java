package engine.collision;

import engine.gridobject.Door;
import engine.gridobject.GridObject;
import engine.gridobject.person.Player;

public class EnterCollision extends CollisionHandler {

	/**
	 * Instantiates a new enter collision.
	 *
	 * @param obj1 the obj1
	 * @param obj2 the obj2
	 */
	public EnterCollision(GridObject obj1, GridObject obj2) {
		super(obj1, obj2);
	}

	@Override
	public void doCollision() {
		if(myObj1 instanceof Player)
			((Player) myObj1).enterDoor((Door) myObj2);
	}

}
