package org.dszi.forklift.logic;

import com.google.inject.Inject;
import java.awt.Point;
import java.util.ArrayList;
import org.dszi.forklift.models.Cart;
import org.dszi.forklift.models.Grid;
import org.dszi.forklift.models.TreeItem;

public class GameLogic {

	@Inject
	private Cart _cart;
	
	@Inject
	private Grid _grid;
	
	@Inject
	private TreeState _treeState;

	private static final ArrayList<Task> _tasks = new ArrayList();

	public void MoveToPoint(Point startPoint, Point destPoint) {
		for (MoveActionTypes action : _treeState.treesearch(new TreeItem(startPoint, MoveActionTypes.RIGHT), destPoint, _grid)) {
			AddTask(ActionTypes.ACTION_TYPE_MOVE_CART, action);
		}
	}

	public void AddTask(ActionTypes actionType, MoveActionTypes moveAction) {
		_tasks.add(new Task(actionType, moveAction));
	}

	private void completeTask(Task task) {

		switch (task.GetActionType()) {
			case ACTION_TYPE_MOVE_CART:
				_cart.Move(task.GetMoveActionType());
				break;
			default:
				System.out.println("Gamelogic: action " + task.GetActionType() + "didin't recognized");
				break;
		}
	}

	public void processLogic() {
		if (!_tasks.isEmpty()) {
			System.out.println("Ilość zadań: " + _tasks.size());
			for (Task task : _tasks) {
				completeTask(task);
			}
			_tasks.clear();
		}
	}
}
