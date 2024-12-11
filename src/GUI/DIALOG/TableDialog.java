package GUI.DIALOG;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.*;

public class TableDialog extends JDialog {
    private JTable table;
    private Vector<String> titles;
    private Vector<Vector<Object>> tableData;
    private DefaultTableModel tableModel;
    private String[] listItem;
    public TableDialog(JFrame parent, String dialogTitle, String[] listItem) {
        super(parent, dialogTitle, true);
        this.listItem = listItem;

        titles = new Vector<>();
        for (var title : listItem) {
            titles.add(title);
        }

        tableData = new Vector<>();
        tableModel = new DefaultTableModel(tableData, titles);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 只能选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(500, 300);
        this.setMinimumSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }
    public void addData(Vector<Vector> data, Vector<Integer> dataIndex) {
        for (int index : dataIndex)
        {
            tableModel.addRow(data.get(index));
        }
        tableModel.fireTableDataChanged(); // 触发表格数据变更事件
    }
}

