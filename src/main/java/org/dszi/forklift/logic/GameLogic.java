package org.dszi.forklift.logic;

import com.google.inject.Inject;
import java.util.ArrayList;
import org.dszi.forklift.models.ActionTypes;
import org.dszi.forklift.models.Cart;
import org.dszi.forklift.models.MoveActionTypes;

public class GameLogic {

	@Inject
	private Cart _cart;

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
