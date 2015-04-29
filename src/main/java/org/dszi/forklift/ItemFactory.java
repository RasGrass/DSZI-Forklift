package org.dszi.forklift;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.dszi.forklift.models.Item;
import org.dszi.forklift.models.Storehouse;

/**
 *
 * @author RasGrass
 */
public class ItemFactory {

	@Inject
	public ItemFactory(Storehouse storehouse, @Assisted String name, @Assisted double weight, @Assisted String color, @Assisted String type) {
	}

	@Inject
	public ItemFactory(Storehouse storehouse, @Assisted String name, @Assisted double weight, @Assisted String color, @Assisted String type, @Assisted int place) {
	}

	@Inject
	public ItemFactory(Storehouse storehouse, @Assisted Item obj) {
	}
}
