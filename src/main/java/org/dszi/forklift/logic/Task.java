package org.dszi.forklift.logic;

import org.dszi.forklift.models.ActionTypes;
import org.dszi.forklift.models.MoveActionTypes;

public class Task
{
	//private Item[] objects = new Item[2];
	private final ActionTypes _action;
        private final MoveActionTypes _moveAction;
	/*private int rack;

        
	public int getRack() {
		return rack;
	}

	public int getShelf() {
		return shelf;
	}
	private int shelf;

	public Task(ActionTypes actionType, Item obj1) {
		action = actionType;
		objects[0] = obj1;
		objects[1] = null;
	}

	public Task(ActionTypes actionType, Item obj1, int rack, int shelf) {
		action = actionType;
		objects[0] = obj1;
		objects[1] = null;
		this.rack = rack;
		this.shelf = shelf;
	}

	public Task(ActionTypes actionType, Item obj1, Item obj2) {
		action = actionType;
		objects[0] = obj1;
		objects[1] = obj2;
	}	

	public void waitNow() {
		action = ActionTypes.ACTION_TYPE_WAIT;
	}

	public Item getObject(int objectNumber) {
		if (objectNumber == 1) {
			return objects[0];
		} else {
			return objects[1];
		}
	}*/
        
        public Task(ActionTypes action, MoveActionTypes moveAction){
            _action = action;
            _moveAction = moveAction;
        }
        
        public ActionTypes GetActionType() {
		return _action;
	}
        
         public MoveActionTypes GetMoveActionType() {
		return _moveAction;
	}
}
