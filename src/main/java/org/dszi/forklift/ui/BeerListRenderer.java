package org.dszi.forklift.ui;

import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.dszi.forklift.models.BeerModel;

/**
 *
 * @author RasGrass
 */
public class BeerListRenderer extends DefaultTableCellRenderer {

	private final List<BeerModel> beers;
	private final JTable table;

	public BeerListRenderer(List<BeerModel> beers, JTable table) {
		this.beers = beers;
		this.table = table;
	}

	@Override
	public String getToolTipText(MouseEvent me) {
		int row = table.rowAtPoint(me.getPoint());
		return beers.get(row).toString();
	}

}
