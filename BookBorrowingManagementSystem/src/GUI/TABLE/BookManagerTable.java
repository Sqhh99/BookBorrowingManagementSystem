package GUI.TABLE;

import GUI.DIALOG.BookCustomInputDialog;
import GUI.DIALOG.BorrowCustomInputDialog;
import GUI.LoginInterface;
import GUI.MainWindow;
import SQL.JdbcUtils;
import SQL.DialogUpdateTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import SQL.DialogAddTable;

public class BookManagerTable extends TableBase {
    JMenuItem borrowMenuItem;
    JMenuItem addMenuItem;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addMenuItem)
            {
                addData();
            } else if (e.getSource() == borrowMenuItem)
            {
                addBorrow();
            }
        }
    };
    public BookManagerTable(String[] ListItem) {
        super(ListItem);

        searchTextField.setPrompt("请输入需要搜索的书名");

        borrowMenuItem = new JMenuItem("借阅");
        addMenuItem = new JMenuItem("添加");
        addMenuItem.addActionListener(actionListener);
        borrowMenuItem.addActionListener(actionListener);

        this.popupMenu.add(addMenuItem);
        this.popupMenu.add(borrowMenuItem);


    }
    @Override
    public void addData() {
        if (!LoginInterface.getAdministratorPrivileges()) return ;
        // 弹出界面填写信息
        BookCustomInputDialog booksCustomInputDialog = new BookCustomInputDialog(null,"图书添加");
        booksCustomInputDialog.setVisible(true);

        if (booksCustomInputDialog.isConfirmed())
        {
            // 获取用户输入的数据
            String bookName = booksCustomInputDialog.getBookName();
            String category = booksCustomInputDialog.getCategory();
            String author = booksCustomInputDialog.getAuthor();

            int price = 0;
            int stock = 0;
            if (isValidInput(booksCustomInputDialog.getPrice()) && isValidInput(booksCustomInputDialog.getStock()))
            {
                price = Integer.parseInt(booksCustomInputDialog.getPrice());
                stock = Integer.parseInt(booksCustomInputDialog.getStock());
            }
            DialogAddTable dialogAddTable = new DialogAddTable();

            if (isValidInput(bookName) && isValidInput(category) && isValidInput(author)
                    && dialogAddTable.addBook(bookName, category, author, price, stock)) {

                Refresh();
                MainWindow.RightStatusLabel.setText("添加成功");
            } else {
                JOptionPane.showMessageDialog(null, "添加失败，请检查数据", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void addBorrow() {
        BorrowCustomInputDialog borrowCustomInputDialog = new BorrowCustomInputDialog(null, "归还时间");
        borrowCustomInputDialog.setVisible(true);

        int userID = UserManagerTable.getUserID();
        System.out.println(userID);
        int bookID = getBookID();
        System.out.println(bookID);
        if (bookID != -1)
        {
            Timestamp timestamp = borrowCustomInputDialog.getTimestamp();
            if (userID != -1 && bookID != -1 && borrowCustomInputDialog.isConfirmed())
            {
                DialogAddTable dialogAddTable = new DialogAddTable();
                if (dialogAddTable.addBorrowRecord(userID,bookID,timestamp))
                {
                    System.out.println("借阅成功");
                    MainWindow.BorrowManageTableRefresh();
                    MainWindow.BookManagerTableRefresh();
                    MainWindow.RightStatusLabel.setText("借阅成功");
                }
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
        String sql = "select * from books";
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
            BookCustomInputDialog booksCustomInputDialog = new BookCustomInputDialog(null,"图书信息输入");
            booksCustomInputDialog.bookTextField.setText(rowData.get(1));
            booksCustomInputDialog.categoryTextField.setText(rowData.get(2));
            booksCustomInputDialog.authorTextField.setText(rowData.get(3));
            booksCustomInputDialog.priceTextField.setText(rowData.get(4));
            booksCustomInputDialog.stockTextField.setText(rowData.get(5));
            booksCustomInputDialog.setVisible(true);

            if (booksCustomInputDialog.isConfirmed())
            {
                // 获取用户输入的数据
                String bookName = booksCustomInputDialog.getBookName();
                String category = booksCustomInputDialog.getCategory();
                String author = booksCustomInputDialog.getAuthor();

                double price = 0;
                int stock = 0;
                if (isValidInput(booksCustomInputDialog.getPrice()) && isValidInput(booksCustomInputDialog.getStock()))
                {
                    price = Double.parseDouble(booksCustomInputDialog.getPrice());
                    stock = Integer.parseInt(booksCustomInputDialog.getStock());
                }
                String BookID = (String) tableModel.getValueAt(selectedRow, 0);

                DialogUpdateTable dialogUpdateTable = new DialogUpdateTable();

                if (isValidInput(bookName) && isValidInput(category) && isValidInput(author)
                        && dialogUpdateTable.updateBooksTable(Integer.parseInt(BookID),
                        bookName, category, author, price, stock)) {
                    // 用户确认后更新表格中对应行的数据
                    tableModel.setValueAt(bookName, selectedRow, 1);
                    tableModel.setValueAt(category, selectedRow, 2);
                    tableModel.setValueAt(author, selectedRow, 3);
                    tableModel.setValueAt(price, selectedRow, 4);
                    tableModel.setValueAt(stock, selectedRow, 5);
                    MainWindow.RightStatusLabel.setText("修改成功");
                } else {
                    JOptionPane.showMessageDialog(null, "修改失败，请检查数据", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
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

                if (JdbcUtils.delData("books",Integer.parseInt(BorrowID)))
                {
                    ((DefaultTableModel) tableModel).removeRow(selectedRow);
                    MainWindow.RightStatusLabel.setText("删除成功");
                }
            }
        }
    }
    public int getBookID() {
        int selectedRow = table.getSelectedRow();
        int bookID = -1;
        if (selectedRow != -1) {
            String temp = (String) tableData.get(selectedRow).get(0);
            bookID = Integer.parseInt(temp);
        }
        return bookID;
    }
}

