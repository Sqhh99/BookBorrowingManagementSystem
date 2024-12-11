package SQL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DialogAddTable {
    static Connection connection = null;

    public DialogAddTable() {
        connection = JdbcUtils.getConnection();
    }

    public Boolean addBook(String bookName, String category, String author, int price, int stock) {
        String sql = "INSERT INTO books (name, category, author, price, stock) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bookName);
            stmt.setString(2, category);
            stmt.setString(3, author);
            stmt.setInt(4, price);
            stmt.setInt(5, stock);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "添加图书失败。", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Boolean addUser(String username, String passwordHash, String phone, String gender) {
        String sql = "INSERT INTO users (username, password_hash, phone, gender) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.setString(3, phone);
            stmt.setString(4, gender);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "添加用户失败。", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    // 添加借阅记录
    public Boolean addBorrowRecord(int userId, int bookId,Timestamp returnTime) {
        String sql = "INSERT INTO borrow_records (user_id, book_id, return_time) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setTimestamp(3, returnTime);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "添加借阅记录失败。", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}

