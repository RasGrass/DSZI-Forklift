package org.dszi.forklift.logic;

import com.google.inject.Inject;
import java.awt.Point;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
        
        @Inject
        private MapBeerSpecies mapBeerSpecies;

	private static final List<Task> _tasks = new CopyOnWriteArrayList<>();

	public void MoveToPoint(Point startPoint, Point destPoint, Boolean empty) {
		for (MoveActionTypes action : _treeState.treesearch(new TreeItem(startPoint, MoveActionTypes.RIGHT), destPoint, _grid)) {
			AddTask(ActionTypes.ACTION_TYPE_MOVE_CART, action, empty);
		}
	}

	public void AddTask(ActionTypes actionType, MoveActionTypes moveAction, Boolean empty) {
		_tasks.add(new Task(actionType, moveAction, empty));
	}

	private void completeTask(Task task) {
                
		switch (task.GetActionType()) {
			case ACTION_TYPE_MOVE_CART:
                                _cart.setEmpty(task.GetEmpty());
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
                                _tasks.remove(task);
			}
		}
	}

    public void NewBeer(String species) 
    {
        Point destPoint = null;
        switch(mapBeerSpecies.Map(species))
        {
            case RACK1:
            {
                destPoint = new Point(7, 2);
                break;
            }
            case RACK2:
            {
                destPoint = new Point(7, 5);
                break;
            }
            case RACK3:
            {
                destPoint = new Point(7, 10);
                break;
            }
            default:
            {
                destPoint = new Point(0,0);
            }
        }
                
        MoveToPoint(new Point(0,0), destPoint, false);
        MoveToPoint(destPoint, new Point(0,0), true);
    }
}
