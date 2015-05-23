package org.dszi.forklift.logic;

public class Task {

	private final ActionTypes _action;
	private final MoveActionTypes _moveAction;

	public Task(ActionTypes action, MoveActionTypes moveAction) {
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
