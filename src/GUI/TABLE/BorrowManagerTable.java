package GUI.TABLE;
import GUI.DIALOG.BorrowCustomInputDialog;
import GUI.LoginInterface;
import GUI.MainWindow;
import SQL.DialogUpdateTable;
import SQL.JdbcUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class BorrowManagerTable extends TableBase {
    JMenuItem returningBooksItem = new JMenuItem("还书");
    static Vector<Integer> UserBorrowArr = new Vector<>();
    static Vector<Integer> beOverdueARR = new Vector<>();

    public void setBeOverdueARR() {
        beOverdueARR.clear();
        for (int i = 0; i < table.getRowCount(); i++) {
            String returningTemp = (String) tableModel.getValueAt(i, 4);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date returningDate;
            try {
                returningDate = sdf.parse(returningTemp.substring(0, 19));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Timestamp returningTimestamp = new Timestamp(returningDate.getTime());
            Timestamp currentTimestamp = new Timestamp(new Date().getTime());

            if (currentTimestamp.getTime() - returningTimestamp.getTime() > 0)
            {
                beOverdueARR.add(i);
            }
        }
    }
    public BorrowManagerTable(String[] ListItem) {
        super(ListItem);
        setBeOverdueARR();
        searchTextField.setPrompt("请输入需要搜索的用户名");

        for (int r = 0; r < tableModel.getRowCount(); r++) {
            if (LoginInterface.getUserName().equals(tableModel.getValueAt(r, 1))) {
                UserBorrowArr.add(r);
            }
        }

        returningBooksItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserBorrowArr.clear();
                for (int r = 0; r < tableModel.getRowCount(); r++) {
                    if (LoginInterface.getUserName().equals(tableModel.getValueAt(r, 1))) {
                        UserBorrowArr.add(r);
                    }
                }
                int selectedRow = table.getSelectedRow();
                System.out.println(UserBorrowArr);
                System.out.println(selectedRow);
                if (selectedRow != -1)
                {
                    if (UserBorrowArr.contains(selectedRow))
                    {
                        int option = JOptionPane.showConfirmDialog(null, "确定要归还吗？", "确认归还", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            String BorrowID = (String) tableModel.getValueAt(selectedRow, 0);
                            System.out.println();

                            if (JdbcUtils.delData("borrow_records",Integer.parseInt(BorrowID)))
                            {
                                ((DefaultTableModel) tableModel).removeRow(selectedRow);
                                MainWindow.RightStatusLabel.setText("归还成功");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "归还失败，无权归还", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        popupMenu.add(returningBooksItem);
    }
    @Override
    protected void addExampleData(Vector<Vector> lists) {
        super.addExampleData(lists);
    }
    @Override
    protected void setColorMarking(int row, Component c) {
        if (beOverdueARR.contains(row)) {
            c.setBackground(new Color(255, 100, 100));
            return;
        }
        c.setBackground(Color.WHITE);
    }
    //  执行
    @Override
    protected void exeAddExampleData() {
        String sql = "select * from borrowuserbook";
        try {
            addExampleData(JdbcUtils.tableResultList(sql));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void dataModification() {
        int selectedRow = table.getSelectedRow();
        if (!UserBorrowArr.contains(selectedRow) && !LoginInterface.getAdministratorPrivileges()) return;
        if (selectedRow != -1) {
            // 弹出界面填写信息
            BorrowCustomInputDialog borrowCustomInputDialog = new BorrowCustomInputDialog(null,"借阅修改");
            borrowCustomInputDialog.setVisible(true);

            if (borrowCustomInputDialog.isConfirmed())
            {
                Timestamp timestamp = borrowCustomInputDialog.getTimestamp();

                String BorrowID = (String) tableModel.getValueAt(selectedRow, 0);
//
                DialogUpdateTable dialogUpdateTable = new DialogUpdateTable();

                if (dialogUpdateTable.updateBorrowTable(Integer.parseInt(BorrowID), timestamp)) {
                    // 用户确认后更新表格中对应行的数据
                    tableModel.setValueAt(timestamp.toString(), selectedRow, 4);
                    MainWindow.RightStatusLabel.setText("修改成功");
                    setBeOverdueARR();
                    Refresh();
                } else {
                    JOptionPane.showMessageDialog(null, "修改失败，请检查数据", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }
    //  双击
    @Override
    public void setMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // 双击事件
            this.dataModification();
        }
    }
    @Override
    public void Refresh() {
        tableData.clear();
        exeAddExampleData();
    }
    @Override
    public void delTableData() {
        if (!LoginInterface.getAdministratorPrivileges()) return ;
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(null, "确定要删除选中的行吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                String BorrowID = (String) tableModel.getValueAt(selectedRow, 0);

                if (JdbcUtils.delData("borrow_records",Integer.parseInt(BorrowID)))
                {
                    ((DefaultTableModel) tableModel).removeRow(selectedRow);
                    MainWindow.RightStatusLabel.setText("删除成功");
                }
            }
        }
    }
}
