package org.dszi.forklift.logic;

public class Task {

	private final ActionTypes _action;
	private final MoveActionTypes _moveAction;
        private final Boolean _empty;

	public Task(ActionTypes action, MoveActionTypes moveAction) {
            _action = action;
            _moveAction = moveAction;
            _empty = true;
	}
        
        public Task(ActionTypes action, MoveActionTypes moveAction, Boolean empty){
            _action = action;
            _moveAction = moveAction;
            _empty = empty;
	}

	public ActionTypes GetActionType() {
            return _action;
	}

	public MoveActionTypes GetMoveActionType() {
            return _moveAction;
	}
        
        public Boolean GetEmpty()
        {
            return _empty;
        }
}
