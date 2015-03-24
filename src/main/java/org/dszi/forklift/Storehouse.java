package org.dszi.forklift;

import java.awt.FlowLayout;
import java.util.ArrayList;

public class Storehouse extends Cart {

	private static ArrayList<Object> objects = new ArrayList();
	public static Rack[] racks = new Rack[7];

	/*public static Storehouse getStorehouse() {
	 return this;
	 }*/
	Storehouse() {
		for (int i = 0; i < 6; i++) {
			racks[i] = new Rack();
			FlowLayout flow = new FlowLayout();
			flow.setVgap(0);
			racks[i].setLayout(flow);
		}

	}

	public Rack[] getRacks() {
		return racks;
	}

	public static ArrayList<Object> getObjects() {
		return objects;
	}

	public static String[][] _getObjects() {
		String[][] arr = new String[objects.size()][5];
		for (int i = 0; i < objects.size(); i++) {
			arr[i][0] = new Integer(objects.get(i).getID()).toString();
			arr[i][1] = objects.get(i).getName();
			arr[i][2] = new Double(objects.get(i).getWeight()).toString();
			arr[i][3] = objects.get(i).getColor();
			arr[i][4] = objects.get(i).getType();
		}
		return arr;

	}

	public void addObjectSpecifically(Object obj, int rack, int shelf, int place) {
		obj.setRackNumber(rack);
		obj.setShelfNumber(shelf);
		Forklift.getStorehouse().getRacks()[rack].getShelf(shelf).getBoxes()[place].add(obj);
		racks[rack].getShelf(shelf).assingObject(obj);

	}

	public void addObjectSpecifically(Object obj, int rack, int shelf) {
		obj.setRackNumber(rack);
		obj.setShelfNumber(shelf);
		for (int boxes = 0; boxes < 5; boxes++) {
			if (racks[rack].getShelf(shelf).getBoxes()[boxes].isEmpty()) {
				//Forklift.getStorehouse().getRacks()[rack].getShelf(shelf).getBoxes()[boxes].add(obj);
				obj.setPlace(boxes);
				racks[rack].getShelf(shelf).assingObject(obj);
				break;
			}
		}
		//    System.out.println(racks[rack].getShelf(shelf).getBoxes()[obj.getPlace()].getBoxXY().x);

	}

	public void addObjectAnywhere(Object obj) {
		boolean isItCreate = false;
		for (int rack = 0; rack < 5; rack++) {
			for (int shelf = 0; shelf < 5; shelf++) {
				for (int box = 0; box < 5; box++) {
					if ((racks[rack].getShelf(shelf).getObjectCount() < 5) && (isItCreate == false) && (racks[rack].getShelf(shelf).getBoxes()[box].isEmpty())) {
						obj.setRackNumber(rack);
						obj.setShelfNumber(shelf);
						obj.setPlace(box);
						racks[rack].getShelf(shelf).assingObject(obj);
						isItCreate = true;
					}
				}
			}
		}
	}

	public static void deleteObject(Object obj) {
		racks[obj.getRackNumber()].getShelf(obj.getShelfNumber()).removeObject(obj);
		racks[obj.getRackNumber()].getShelf(obj.getShelfNumber()).getBoxes()[obj.getPlace()].remove(obj);
//        racks[obj.getRackNumber()].getShelf(obj.getShelfNumber()).getBoxes()[obj.getPlace()].setObject(null);
		obj = null;
	}

	public void replaceObject(int toRack, int toShelf, Object obj) {
		if (racks[toRack].getShelf(toShelf).getObjectCount() < 5) {
			racks[toRack].getShelf(toShelf).assingObject(obj);
			racks[obj.getRackNumber()].getShelf(obj.getShelfNumber()).removeObject(obj);
			obj.setRackNumber(toRack);
			obj.setShelfNumber(toShelf);
		} else {
			System.out.println("Nie można przenieść - półka jest pełna");
		}
	}

	public static void replaceObjects(Object obj1, Object obj2) {
		//obj1 jest przenoszony w inne wolne miejsce

		Object tempObj = obj2;
		int tempRackNumber2 = obj2.getRackNumber();
		int tempShelfNumber2 = obj2.getShelfNumber();

		int tempRackNumber1 = obj1.getRackNumber();
		int tempShelfNumber1 = obj1.getShelfNumber();

		//obj2 jest usuwany
		racks[tempRackNumber2].getShelf(tempShelfNumber2).removeObject(obj2);

		//pod miejsce obj2 przypisany jest obj1
		racks[tempRackNumber2].getShelf(tempShelfNumber2).assingObject(obj1);

		//ustawienie półek dla obj1
		obj1.setRackNumber(tempRackNumber2);
		obj1.setShelfNumber(tempShelfNumber2);

		//usunięcie obj1
		racks[tempRackNumber1].getShelf(tempShelfNumber1).removeObject(obj1);

		//przypisanie tymczasowego na stare miejsce obj1 
		racks[tempRackNumber1].getShelf(tempShelfNumber1).assingObject(tempObj);
		obj2.setRackNumber(tempRackNumber1);
		obj2.setShelfNumber(tempShelfNumber1);
		tempObj = null;
	}

	public static Object find(int id) {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).getID() == id) {
				System.out.println("Znaleziono obiekt o podanym id");
				return objects.get(i);
			}
		}
		System.out.println("Nie znaleziono obiektu o podanym id");
		return null;
	}

	public static ArrayList<Object> findByCriteria(Criteria c) {
		ArrayList<Object> found = new ArrayList<Object>();

		for (int i = 0; i < objects.size(); i++) {
			if (c.match(objects.get(i))) {
				found.add(objects.get(i));
			}
		}

		return found;
	}

	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < objects.size(); i++) {
			ret += _getObjects()[i][0] + System.lineSeparator();
			ret += _getObjects()[i][1] + System.lineSeparator();
			ret += _getObjects()[i][2] + System.lineSeparator();
			ret += _getObjects()[i][3] + System.lineSeparator();
			ret += _getObjects()[i][4] + System.lineSeparator();
		}
		return ret;
	}
}
