package org.dszi.forklift;

import java.util.ArrayList;

/**
 *
 * @author RasGrass
 */
public class GameLogic {

	private static ArrayList<Task> task = new ArrayList();

	public static void addTask(int actionType, Object obj1) {
		task.add(new Task(actionType, obj1));
	}

	public static void addTask(int actionType, Object obj1, int rack, int shelf) {
		task.add(new Task(actionType, obj1, rack, shelf));
	}

	public static void addTask(int actionType, Object obj1, Object obj2) {
		task.add(new Task(actionType, obj1, obj2));
	}

	public Task getTask(int index) {
		return task.get(index);
	}

	private void completeTask(int index) throws InterruptedException {
		if (task != null) {
			switch (task.get(index).getActionType()) {
				case Task.ACTION_TYPE_ADD:
					Forklift.getCart().add(getTask(index).getObject(1), 2, 0);
					//task = null;
					break;
				case Task.ACTION_TYPE_ADDANYWHERE:
					Forklift.getCart().add(getTask(index).getObject(1));
					//task = null;
					break;
				case Task.ACTION_TYPE_DELETE:
					Forklift.getCart().delete(getTask(index).getObject(1));
					//task = null;
					break;
				case Task.ACTION_TYPE_MOVE:
					Forklift.getCart().replace(getTask(index).getObject(1), getTask(index).getRack(), getTask(index).getShelf());
					//task = null;
					break;
				case Task.ACTION_TYPE_REPLACE:
					Forklift.getCart().replace(getTask(index).getObject(1), getTask(index).getObject(2));
					//task = null;
					break;
				default:
					System.out.println(task == null ? "" : "Gamelogic: action " + task.get(index).getActionType() + "didin't recognized");
					break;
			}
		}
	}

	public void processLogic() throws InterruptedException {
		if (task.isEmpty()) {
		} else {
			System.out.println("dgdsgdsgsdgsgs" + task.size());
			for (int i = 0; i < task.size(); i++) {
				completeTask(i);
			}
			task.clear();

		}
	}
}
