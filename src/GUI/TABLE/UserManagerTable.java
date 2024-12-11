package GUI.TABLE;

import GUI.DIALOG.UserCustomInputDialog;
import GUI.LoginInterface;
import GUI.MainWindow;
import SQL.DialogAddTable;
import SQL.DialogUpdateTable;
import SQL.JdbcUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;

public class UserManagerTable extends TableBase {
    private static int userID = -1;
    private int UserRow = -1;
    JMenuItem addMenuItem;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addMenuItem)
            {
                addData();
            }
        }
    };

    public UserManagerTable(String[] ListItem) {
        super(ListItem);

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String temp = (String) tableModel.getValueAt(i, 1);
            if (temp.equals(LoginInterface.getUserName())) {
                UserRow = i;
                try {
                    userID = Integer.parseInt((String) tableModel.getValueAt(UserRow, 0));
                } catch (NumberFormatException ee) {
                    ee.printStackTrace();
                }
            }
        }

        searchTextField.setPrompt("请输入需要搜索的用户名");

        addMenuItem = new JMenuItem("添加");
        this.popupMenu.add(addMenuItem);
        addMenuItem.addActionListener(actionListener);
    }
    @Override
    public void addData() {
        if (!LoginInterface.getAdministratorPrivileges()) return ;
        // 弹出界面填写信息
        UserCustomInputDialog userCustomInputDialog = new UserCustomInputDialog(null,"用户添加");
        userCustomInputDialog.setVisible(true);

        if (userCustomInputDialog.isConfirmed())
        {
            String UserName = userCustomInputDialog.getUsername();
            String PassWord = userCustomInputDialog.getPassword();
            String Phone = userCustomInputDialog.getPhone();
            String gender = userCustomInputDialog.getSex() ? "M" : "F";

            DialogAddTable dialogAddTable = new DialogAddTable();

            if (isValidInput(UserName) && isValidInput(PassWord) && isValidInput(Phone) &&
                    dialogAddTable.addUser(UserName, PassWord, Phone, gender)) {
                Refresh();
                MainWindow.RightStatusLabel.setText("添加成功");
            } else {
                JOptionPane.showMessageDialog(null, "修改失败，请检查数据", "错误", JOptionPane.ERROR_MESSAGE);
                MainWindow.RightStatusLabel.setText("添加失败");
            }
        }
    }
    @Override
    public void Refresh() {
        tableData.clear();
        exeAddExampleData();
    }
    @Override
    protected void addExampleData(Vector<Vector> lists) {
        super.addExampleData(lists);
    }
    @Override
    protected void exeAddExampleData() {
        String sql = "select * from users";
        try {
            addExampleData(JdbcUtils.tableResultList(sql));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void dataModification() {
        if (!LoginInterface.getAdministratorPrivileges()) return ;
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // 获取选中行的数据
            Vector<String> rowData = new Vector<>();
            for (int i = 0; i < table.getColumnCount(); i++) {
                rowData.add((String) tableModel.getValueAt(selectedRow, i));
            }

            // 弹出界面填写信息
            UserCustomInputDialog userCustomInputDialog = new UserCustomInputDialog(null,"用户修改");
            userCustomInputDialog.userTextField.setText(rowData.get(1));
            userCustomInputDialog.passwordTextField.setText(rowData.get(2));
            userCustomInputDialog.phoneTextField.setText(rowData.get(3));
            if (rowData.get(4).equals("M")) userCustomInputDialog.maleBtn.setSelected(true);
            else userCustomInputDialog.femaleBtn.setSelected(true);
            userCustomInputDialog.setVisible(true);

            if (userCustomInputDialog.isConfirmed())
            {
                String UserName = userCustomInputDialog.getUsername();
                String PassWord = userCustomInputDialog.getPassword();
                String Phone = userCustomInputDialog.getPhone();
                String gender = userCustomInputDialog.getSex() ? "M" : "F";

                DialogUpdateTable dialogUpdateTable = new DialogUpdateTable();

                String UserID = (String) tableModel.getValueAt(selectedRow, 0);

                if (isValidInput(UserName) && isValidInput(PassWord) && isValidInput(Phone) &&
                        dialogUpdateTable.updateUsersTable(Integer.parseInt(UserID),
                                UserName, PassWord, Phone, gender)) {
                    // 用户确认后更新表格中对应行的数据
                    tableModel.setValueAt(UserName, selectedRow, 1);
                    tableModel.setValueAt(PassWord, selectedRow, 2);
                    tableModel.setValueAt(Phone, selectedRow, 3);
                    tableModel.setValueAt(gender, selectedRow, 4);
                    MainWindow.RightStatusLabel.setText("修改成功");
                } else {
                    JOptionPane.showMessageDialog(null, "修改失败，请检查数据", "错误", JOptionPane.ERROR_MESSAGE);
                    MainWindow.RightStatusLabel.setText("修改失败");
                }
            }
        }
    }
    @Override
    protected void setColorMarking(int row, Component c) {
        if (row == UserRow) {
            c.setBackground(new Color(173, 255, 173));
            return;
        }
        c.setBackground(Color.WHITE);
    }
    @Override
    public void setMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // 双击事件
            this.dataModification();
        }
    }
    @Override
    public void delTableData() {
        if (!LoginInterface.getAdministratorPrivileges()) return ;
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(null, "确定要删除选中的行吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                String BorrowID = (String) tableModel.getValueAt(selectedRow, 0);

                if (JdbcUtils.delData("users",Integer.parseInt(BorrowID)))
                {
                    ((DefaultTableModel) tableModel).removeRow(selectedRow);
                    MainWindow.RightStatusLabel.setText("删除成功");
                } else {
                    MainWindow.RightStatusLabel.setText("删除失败");
                }
            }
        }
    }
    public static int getUserID() {
        System.out.println(userID);
        return userID;
    }
}
