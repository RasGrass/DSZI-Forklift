package org.dszi.forklift;

import com.google.inject.AbstractModule;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.dszi.forklift.logic.DecisionTree;
import org.dszi.forklift.logic.GameLogic;
import org.dszi.forklift.logic.MapBeerSpecies;
import org.dszi.forklift.logic.TreeState;
import org.dszi.forklift.models.Cart;
import org.dszi.forklift.models.Grid;
import org.dszi.forklift.repository.Storehouse;
import org.dszi.forklift.repository.ImageRepository;

/**
 *
 * @author RasGrass
 */
public class RepositoryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ImageRepository.class).asEagerSingleton();
		bind(Storehouse.class).asEagerSingleton();                
		bind(Grid.class).asEagerSingleton();
		bind(Cart.class).asEagerSingleton();
		bind(JPanel.class).asEagerSingleton();
		bind(JFrame.class).asEagerSingleton();
		bind(GameLogic.class).asEagerSingleton();
		bind(TreeState.class).asEagerSingleton();
		bind(DecisionTree.class).asEagerSingleton();
                bind(MapBeerSpecies.class).asEagerSingleton();
	}
}
