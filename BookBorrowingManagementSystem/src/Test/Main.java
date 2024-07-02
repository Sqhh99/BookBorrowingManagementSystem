package Test;
import GUI.LoginInterface;
import SQL.JdbcUtils;
import java.sql.SQLException;

public class Main {
    static void Login()
    {
        LoginInterface loginInterface = new LoginInterface();
        loginInterface.Init();
    }
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (JdbcUtils.getConnection() != null) {
                    JdbcUtils.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        Login();
    }
}

