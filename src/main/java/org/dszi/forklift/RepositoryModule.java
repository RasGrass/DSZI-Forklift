package org.dszi.forklift;

import com.google.inject.AbstractModule;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.dszi.forklift.logic.GameLogic;
import org.dszi.forklift.models.Cart;
import org.dszi.forklift.models.Rack;
import org.dszi.forklift.models.Shelf;
import org.dszi.forklift.models.Storehouse;
import org.dszi.forklift.repository.ImageRepository;
import org.dszi.forklift.ui.ItemListPanel;

/**
 *
 * @author RasGrass
 */
public class RepositoryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ImageRepository.class).asEagerSingleton();//toInstance(new ImageRepository());
		bind(Storehouse.class).asEagerSingleton();//toInstance(new Storehouse());
		bind(ItemListPanel.class).asEagerSingleton();

		bind(Cart.class).asEagerSingleton();//toInstance(new Cart());
		bind(JPanel.class).asEagerSingleton();//toInstance(new JPanel());
		bind(JFrame.class).asEagerSingleton();//toInstance(new JFrame());
		bind(Shelf.class).toInstance(new Shelf());
		bind(Rack.class).toInstance(new Rack());
                bind(GameLogic.class).asEagerSingleton();

	}
}
