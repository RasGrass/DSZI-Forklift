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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
		this.rowNames = new String[]{"Chmiele", "Słody", "Drożdże", "IBU", "Alkohol", "Ekstrakt"};
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
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();

		cellRenderer.setHorizontalTextPosition(DefaultTableCellRenderer.CENTER);
		cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
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
			objectTable.setRowHeight(150);
			objectTable.setColumnSelectionAllowed(false);
			objectTable.setDragEnabled(false);
			objectTable.setRowMargin(30);

			model.setRowCount(storehouse.getBeers().size());
			TableColumn col1 = objectTable.getColumnModel().getColumn(0);
			TableColumn col2 = objectTable.getColumnModel().getColumn(1);
			TableColumn col3 = objectTable.getColumnModel().getColumn(2);
			TableColumn col4 = objectTable.getColumnModel().getColumn(3);
			TableColumn col5 = objectTable.getColumnModel().getColumn(4);
			TableColumn col6 = objectTable.getColumnModel().getColumn(5);

			col1.setCellRenderer(cellRenderer);
			col2.setCellRenderer(cellRenderer);
			col3.setCellRenderer(cellRenderer);
			col4.setCellRenderer(cellRenderer);
			col5.setCellRenderer(cellRenderer);
			col6.setCellRenderer(cellRenderer);
		}

		objectTable.setOpaque(true);
		objectTable.setShowVerticalLines(false);
		objectTable.setShowHorizontalLines(true);
		table = new JScrollPane(objectTable);

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
