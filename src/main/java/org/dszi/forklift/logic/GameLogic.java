package org.dszi.forklift.logic;

import com.google.inject.Inject;
import java.awt.Point;
import java.util.ArrayList;
import org.dszi.forklift.models.ActionTypes;
import org.dszi.forklift.models.Cart;
import org.dszi.forklift.models.Grid;
import org.dszi.forklift.models.GridItem;
import org.dszi.forklift.models.MoveActionTypes;
import org.dszi.forklift.models.TreeItem;

public class GameLogic {

	@Inject
	private Cart _cart;
        @Inject
        private Grid _grid;
        @Inject
        private TreeState _treeState;
        
	private static final ArrayList<Task> _tasks = new ArrayList();

        public GameLogic()
        {
            
        }
        
	/*public static void addTask(ActionTypes actionType, Item obj1) {
		tasks.add(new Task(actionType, obj1));
	}

	public static void addTask(ActionTypes actionType, Item obj1, int rack, int shelf) {
		tasks.add(new Task(actionType, obj1, rack, shelf));
	}

	public static void addTask(ActionTypes actionType, Item obj1, Item obj2) {
		tasks.add(new Task(actionType, obj1, obj2));
	}
*/
        public void MoveToPoint(Point startPoint, Point destPoint)
        {
           /* grid.SetObject(new GridItem(), 3, 3);
             grid.SetObject(new GridItem(), 2, 3);
              grid.SetObject(new GridItem(), 1, 3);*/
            
           /* _grid.SetObject(new GridItem(), 1, 0);
              _grid.SetObject(new GridItem(), 8, 7);
              _grid.SetObject(new GridItem(), 9, 7);
              _grid.SetObject(new GridItem(), 10, 7);
              _grid.SetObject(new GridItem(), 11, 7);*/
                   
            for(MoveActionTypes action : _treeState.treesearch(new TreeItem(startPoint, MoveActionTypes.RIGHT), destPoint, _grid))
            {
                AddTask(ActionTypes.ACTION_TYPE_MOVE_CARD, action);
            }
        }
        
        public  void AddTask(ActionTypes actionType, MoveActionTypes moveAction) {
		_tasks.add(new Task(actionType, moveAction));
	}
        
	private void completeTask(Task task) throws InterruptedException {
	
			switch (task.GetActionType()) {
				/*case ACTION_TYPE_ADD:
					cart.add(task.getObject(1), 2, 0);
					break;
				case ACTION_TYPE_ADDANYWHERE:
					cart.add(task.getObject(1));
					break;
				case ACTION_TYPE_DELETE:
					cart.delete(task.getObject(1));
					break;
				case ACTION_TYPE_MOVE:
					cart.replace(task.getObject(1), task.getRack(), task.getShelf());
					break;
				case ACTION_TYPE_REPLACE:
					cart.replace(task.getObject(1), task.getObject(2));
					break;*/
                                case ACTION_TYPE_MOVE_CARD:                                        
                                        _cart.Move(task.GetMoveActionType());
                                        break;
				default:
					System.out.println( "Gamelogic: action " + task.GetActionType() + "didin't recognized");
					break;
			}
		}
	

	public void processLogic() throws InterruptedException {
		if (!_tasks.isEmpty())
                {
                    System.out.println("Ilość zadań: " + _tasks.size());
                    for (Task task : _tasks) {
                        completeTask(task);
                    }
                    _tasks.clear();
		}
	}
}
