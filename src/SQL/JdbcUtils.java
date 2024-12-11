package SQL;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class JdbcUtils {
    static Connection connection = null;
    static   {
        try {
            //解析配置文件db.properties
            InputStream asStream = JdbcUtils.class.getResourceAsStream("/res/db.properties");

            Properties properties = new Properties();
            properties.load(asStream);

            //解析配置文件各个参数
            final String driver = properties.getProperty("jdbc.driver");
            final String url = properties.getProperty("jdbc.url");
            final String username = properties.getProperty("jdbc.username");
            final String password = properties.getProperty("jdbc.password");
            //注册驱动
            Class.forName(driver);
            //获取连接对象
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // 获取连接对象
    public static Connection getConnection(){
        return connection;
    }
    public static Vector<Vector> tableResultList(String sql) throws SQLException {
        Statement state = null;
        ResultSet resultSet = null;
        Vector<Vector> resultList = new Vector<>();
        try {
            // 获取语句执行对象
            state = connection.createStatement();
            resultSet = state.executeQuery(sql);

            // 获取结果集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 处理结果集
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    row.add(value);
                }
                resultList.add(row);
            }
        } finally {
            // 关闭资源
            if (state != null) {
                state.close();
            }
            if (resultSet != null)
            {
                resultSet.close();
            }
        }
        return resultList;
    }
    public static void insertData(String sql, Vector<Object> params) throws SQLException {
        try (PreparedStatement pStmt = connection.prepareStatement(sql)) {

            // 设置参数
            for (int i = 0; i < params.size(); i++) {
                pStmt.setObject(i + 1, params.get(i));
            }
            // 执行插入操作
            pStmt.executeUpdate();
        }
    }
    public static Boolean validateLogin(String username, String password) {
        // 查询数据库以验证用户名和密码
        boolean validate;
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            validate = rs.next();
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return validate;
    }
    public static boolean isAdministrators(String username, String password) {
        // 查询数据库以验证用户名和密码
        boolean isValid;
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password_hash = ? AND authority = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setBoolean(3, true); // 设置为 true，表示管理员权限
            ResultSet rs = pstmt.executeQuery();

            isValid = rs.next(); // 如果查询结果包含至少一行，则认为是管理员

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            isValid = false; // 查询出错，返回 false
        }
        return isValid;
    }
    public static boolean delData(String tableName, int ID) {
        PreparedStatement stmt = null;
        try {
            // 创建 SQL 删除语句
            String sql = "DELETE FROM " + tableName + " WHERE id = ?";

            // 准备语句
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ID);

            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(null, "删除数据失败，存在外键约束", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {}
        }
        return true;
    }

}