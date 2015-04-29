package org.dszi.forklift.ui;

import com.google.inject.Inject;
import org.dszi.forklift.models.Storehouse;
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

	private final Storehouse storehouse;

	@Inject
	public ItemListPanel(Storehouse storehouse) {
		super();
		this.storehouse = storehouse;
		this.panelSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.rowNames = new String[]{"ID", "Nazwa", "Ciężar", "Kolor", "Typ"};
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
		model = new DefaultTableModel(storehouse._getObjects(), rowNames) {
			@Override
			public boolean isCellEditable(int cell, int column) {
				return false;
			}
		};
		model.setRowCount(storehouse.getObjects().size());
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalTextPosition(DefaultTableCellRenderer.CENTER);
		cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

		objectTable = new JTable(model);
		objectTable.setBackground(Color.LIGHT_GRAY);
		objectTable.setIntercellSpacing(new Dimension(3, 5));
		objectTable.setRowHeight(50);
		objectTable.setColumnSelectionAllowed(false);
		objectTable.setDragEnabled(false);
		objectTable.setRowMargin(30);

		//System.out.println(Forklift.getStroehouse().toString());
		TableColumn col1 = objectTable.getColumnModel().getColumn(0);
		TableColumn col2 = objectTable.getColumnModel().getColumn(1);
		TableColumn col3 = objectTable.getColumnModel().getColumn(2);
		TableColumn col4 = objectTable.getColumnModel().getColumn(3);
		TableColumn col5 = objectTable.getColumnModel().getColumn(4);

		col1.setCellRenderer(cellRenderer);
		col2.setCellRenderer(cellRenderer);
		col3.setCellRenderer(cellRenderer);
		col4.setCellRenderer(cellRenderer);
		col5.setCellRenderer(cellRenderer);

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
					int selectedRowsCount = objectTable.getSelectedRows().length;
					int[] selectedRows = objectTable.getSelectedRows();
					int id = Integer.parseInt((String) model.getValueAt(selectedRows[0], 0));
					System.out.println("usuwam" + storehouse.find(id).toString());
					storehouse.deleteObject(storehouse.find(id));
					model.removeRow(selectedRows[0]);
				}
			}
		});

		add(southButtonPanel, BorderLayout.SOUTH);
		setVisible(true);
		southButtonPanel.setVisible(true);
		//System.out.println(southButtonPanel.getSize().width);
		southButtonPanel.setLayout(new GridLayout(1, 2, southButtonPanel.getSize().width / 3, 30));
	}
}
