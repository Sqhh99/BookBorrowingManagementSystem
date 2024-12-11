package SQL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.*;

public class DialogUpdateTable {
    static Connection connection = null;

    public DialogUpdateTable() {
        connection = JdbcUtils.getConnection();
    }

    public Boolean updateBooksTable(int bookId, String bookName, String category, String author, Double price, int stock) {
        String sql = "UPDATE books SET name = ?, category = ?, author = ?, price = ?, stock = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, bookName);
            stmt.setString(2, category);
            stmt.setString(3, author);
            stmt.setDouble(4, price);
            stmt.setInt(5, stock);
            stmt.setInt(6, bookId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "数据库更新失败。", "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Boolean updateUsersTable(int userId, String username, String passwordHash, String phone, String gender) {
        String sql = "UPDATE users SET username = ?, password_hash = ?, phone = ?, gender = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.setString(3, phone);
            stmt.setString(4, gender);
            stmt.setInt(5, userId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "数据库更新失败。", "错误", JOptionPane.ERROR_MESSAGE);

            return false;
        }
        return true;
    }

    public Boolean updateBorrowTable(int Id, Timestamp returnTime) {
        String sql = "UPDATE borrow_records SET return_time = ? WHERE Id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, returnTime);
            stmt.setInt(2, Id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "借阅记录已更新。", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "未找到匹配的借阅记录。", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "数据库更新失败。", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
