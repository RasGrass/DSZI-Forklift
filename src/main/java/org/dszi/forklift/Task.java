package org.dszi.forklift;

/**
 *
 * @author RasGrass
 */
public class Task {

	public static final int ACTION_TYPE_REPLACE = 5;
	public static final int ACTION_TYPE_ADDANYWHERE = 4;
	public static final int ACTION_TYPE_ADD = 3;
	public static final int ACTION_TYPE_DELETE = 1;
	public static final int ACTION_TYPE_MOVE = 2;
	public static final int ACTION_TYPE_WAIT = 0;
	private Object[] objects = new Object[2];
	private int action;
	private int rack;

	public int getRack() {
		return rack;
	}

	public int getShelf() {
		return shelf;
	}
	private int shelf;

	public Task(int actionType, Object obj1) {
		action = actionType;
		objects[0] = obj1;
		objects[1] = null;
	}

	public Task(int actionType, Object obj1, int rack, int shelf) {
		action = actionType;
		objects[0] = obj1;
		objects[1] = null;
		this.rack = rack;
		this.shelf = shelf;
	}

	public Task(int actionType, Object obj1, Object obj2) {
		action = actionType;
		objects[0] = obj1;
		objects[1] = obj2;
	}

	public int getActionType() {
		return action;
	}

	public void waitNow() {
		action = Task.ACTION_TYPE_WAIT;
	}

	public Object getObject(int objectNumber) {
		if (objectNumber == 1) {
			return objects[0];
		} else {
			return objects[1];
		}
	}
}
