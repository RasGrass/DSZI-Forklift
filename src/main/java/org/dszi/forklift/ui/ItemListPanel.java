package org.dszi.forklift.ui;

import com.google.inject.Inject;
import org.dszi.forklift.repository.Storehouse;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;
import org.dszi.forklift.Forklift;

/**
 *
 * @author RasGrass
 */
public class ItemListPanel extends JPanel {

	private final Dimension panelSize;
	private JTable objectTable;
	private DefaultTableModel model;
	private final String[] rowNames;
	private JScrollPane table;

	@Inject
	private Storehouse storehouse;

	public ItemListPanel() {
		super();
		this.panelSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.rowNames = new String[]{"Nazwa", "Gatunek"};
		int temp;
		temp = (int) (panelSize.width / 1.5);
		panelSize.width = panelSize.width - temp;
		setPreferredSize(panelSize);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new BorderLayout());
		setVisible(true);
		initTable();
		initUI();
		setBackground(Color.LIGHT_GRAY);
		table.getViewport().setBackground(Color.LIGHT_GRAY);
	}

	private void initTable() {

		storehouse = Forklift.getInjector().getInstance(Storehouse.class);
		if (storehouse == null) {
			model = new DefaultTableModel();
		} else {
			model = new DefaultTableModel(storehouse._getBeers(), rowNames) {
				@Override
				public boolean isCellEditable(int cell, int column) {
					return false;
				}
			};

			objectTable = new JTable(model);
			objectTable.setBackground(Color.LIGHT_GRAY);
			objectTable.setIntercellSpacing(new Dimension(3, 5));
			objectTable.setRowHeight(50);
			objectTable.setColumnSelectionAllowed(false);
			objectTable.setDragEnabled(false);
			objectTable.setRowMargin(30);

			model.setRowCount(storehouse.getBeers().size());

		}

		objectTable.setOpaque(true);
		objectTable.setShowVerticalLines(false);
		objectTable.setShowHorizontalLines(true);
		table = new JScrollPane(objectTable);
		DefaultTableCellRenderer cellRenderer = new BeerListRenderer(storehouse.getBeers(), objectTable);

		cellRenderer.setHorizontalTextPosition(DefaultTableCellRenderer.CENTER);
		cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		TableColumn col1 = objectTable.getColumnModel().getColumn(0);
		TableColumn col2 = objectTable.getColumnModel().getColumn(1);

		col1.setCellRenderer(cellRenderer);
		col2.setCellRenderer(cellRenderer);
	}

	private void initUI() {
		JLabel tableTitle = new JLabel("Zawartość magazynu");
		tableTitle.setFont(new Font("Serif", Font.PLAIN, 24));
		tableTitle.setBackground(Color.LIGHT_GRAY);
		tableTitle.setHorizontalAlignment(JLabel.CENTER);
		add(tableTitle, BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
		JPanel southButtonPanel = new JPanel();
		JButton selectAllButton = new JButton("Zaznacz wszystko");
		JButton removeSelectedButton = new JButton("Usuń zaznaczone");
		southButtonPanel.add(selectAllButton);
		southButtonPanel.add(removeSelectedButton);

		selectAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				objectTable.selectAll();
			}
		});

		removeSelectedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				objectTable.invalidate();
				while (objectTable.getSelectedRows().length > 0) {
					int[] selectedRows = objectTable.getSelectedRows();
					model.removeRow(selectedRows[0]);
				}
			}
		});

		add(southButtonPanel, BorderLayout.SOUTH);
		setVisible(true);
		southButtonPanel.setVisible(true);
		southButtonPanel.setLayout(new GridLayout(1, 2, southButtonPanel.getSize().width / 3, 30));
	}

}
