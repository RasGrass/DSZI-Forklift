package org.dszi.forklift;

/**
 *
 * @author Damian
 */
public class Criteria {

	private int id, weight, rackNumber, shelfNumber;
	private String name, color, type;

	public Criteria() {
		id = weight = rackNumber = shelfNumber = -1;
		name = color = type = null;
	}

	public boolean match(Object o) {
		if (id != -1 && id != o.getID()) {
			return false;
		}

		if (weight != -1 && weight != o.getWeight()) {
			return false;
		}

		if (rackNumber != -1 && rackNumber != o.getRackNumber()) {
			return false;
		}

		if (shelfNumber != -1 && shelfNumber != o.getShelfNumber()) {
			return false;
		}

		if (name != null && !name.toLowerCase().equals(o.getName().toLowerCase())) {
			return false;
		}

		if (color != null && !color.toLowerCase().equals(o.getColor().toLowerCase())) {
			return false;
		}

		if (type != null && !type.toLowerCase().equals(o.getType().toLowerCase())) {
			return false;
		}

		return true;
	}

	public void addCritera(Criteria c) {
		if (c.id != -1) {
			id = c.id;
		}

		if (c.weight != -1) {
			weight = c.weight;
		}

		if (c.rackNumber != -1) {
			rackNumber = c.rackNumber;
		}

		if (c.shelfNumber != -1) {
			shelfNumber = c.shelfNumber;
		}

		if (c.name != null) {
			name = c.name;
		}

		if (c.color != null) {
			color = c.color;
		}

		if (c.type != null) {
			type = c.type;
		}
	}

	public String __toString() {
		return ("name: " + name);
	}

	public boolean isEmpty() {
		return ((weight == -1)
				&& (rackNumber == -1)
				&& (shelfNumber == -1)
				&& (name == null)
				&& (color == null)
				&& (type == null));
	}

	public int getId() {
		return id;
	}

	public int getWeight() {
		return weight;
	}

	public int getRackNumber() {
		return rackNumber;
	}

	public int getShelfNumber() {
		return shelfNumber;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public String getType() {
		return type;
	}

	public void setId(int value) {
		id = value;
	}

	public void setWeight(int value) {
		weight = value;
	}

	public void setRackNumber(int value) {
		rackNumber = value;
	}

	public void setShelfNumber(int value) {
		shelfNumber = value;
	}

	public void setName(String value) {
		name = value;
	}

	public void setColor(String value) {
		color = value;
	}

	public void setType(String value) {
		type = value;
	}
}
