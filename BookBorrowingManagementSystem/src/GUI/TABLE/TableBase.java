package GUI.TABLE;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import GUI.DIALOG.TableDialog;
import org.jdesktop.swingx.JXTextField;

public class TableBase extends Box {
    protected JXTextField searchTextField;
    protected JButton searchBtn;
    protected JTable table;
    protected Vector<String> titles;
    protected Vector<Vector> tableData;
    protected TableModel tableModel;
    protected String[] ListItem;
    protected JPopupMenu popupMenu = null;
    public TableBase(String[] ListItem) {
        // 垂直布局
        super(BoxLayout.Y_AXIS);

        // 组装视图
        this.ListItem = ListItem;
        titles = new Vector<>();
        //  添加属性标题
        for (String title : this.ListItem) { titles.add(title); }

        tableData = new Vector<>();
        // 创建表格模型
        tableModel = new DefaultTableModel(tableData, titles);
        table = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
//                return super.isCellEditable(row, column);
                return false;
            }
        };

        // 只能选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        exeAddExampleData();

        // 添加双击监听器
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setMouseClicked(e);
            }
        });

        // 设置自定义渲染器
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setColorMarking(row, c);
                return c;
            }
        });

        //  设置右键菜单
        this.popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("删除行");
        deleteMenuItem.addActionListener(e -> { delTableData(); });

        popupMenu.add(deleteMenuItem);
        table.setComponentPopupMenu(popupMenu);

        searchTextField = new JXTextField();
        searchTextField.setMaximumSize(new Dimension(Short.MAX_VALUE, 20));
        searchTextField.setPrompt("请输入搜索内容..."); // 设置提示文本
        searchTextField.setPromptForeground(Color.GRAY);
        searchBtn = new JButton("搜索");
        searchBtn.setMaximumSize(new Dimension(40, 20));
        Box HBox = Box.createHorizontalBox();
        HBox.add(searchTextField);
        HBox.add(searchBtn);

        //  搜索
        searchBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchUserName = searchTextField.getText();
                System.out.println(searchUserName);
                Vector<Integer> dataIndex = new Vector<>();
                for (int r = 0; r < tableModel.getRowCount(); r++) {
                    if (searchUserName.equals(tableModel.getValueAt(r, 1))) {
                        dataIndex.add(r);
                    }
                }
                if (!dataIndex.isEmpty())
                {
                    TableDialog tableDialog = new TableDialog(null, "查询书籍信息", ListItem);
                    tableDialog.addData(tableData, dataIndex);
                    tableDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "未找到！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.add(HBox);
        this.add(new JScrollPane(table));
    }
    protected void setColorMarking(int row, Component c) {}
    protected void setMouseClicked(MouseEvent e) { this.dataModification(); };
    protected void exeAddExampleData() {};
    // 添加示例数据的方法
    protected void addExampleData(Vector<Vector> lists){
        lists.forEach(temp -> {
            Vector<String> row = new Vector<>();
            for (Object item : temp) {
                row.add(item != null ? item.toString() : ""); // 将对象转换为字符串，并避免空指针异常
            }
            tableData.add(row); // 将行数据添加到表格数据中
        });
        ((DefaultTableModel) tableModel).fireTableDataChanged(); // 触发表格数据变更事件
    };
    public void dataModification() {};
    protected boolean isValidInput(String input) {
        return input != null && !input.isBlank();
    }
    public void delTableData() {};
    public void Refresh() {}
    public void addData() {}
}
