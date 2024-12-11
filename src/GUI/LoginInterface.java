package GUI;

import SQL.JdbcUtils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

//  登录界面
public class LoginInterface {
    JFrame jFrame = new JFrame("登录");
    JPanel jPanel = new JPanel();
    final int WIDTH = 350;
    final int HEIGHT = 300;
    private static Boolean administratorPrivileges = false;
    private static String LoginInterfaceUserName = null;
    Box vBox = Box.createVerticalBox();  //  垂直布局
    Box userBox = Box.createHorizontalBox();  //  用户名水平布局
    Box passwordBox = Box.createHorizontalBox();  //  密码水平布局
    Box btnBox = Box.createHorizontalBox();  //  按钮水平布局
    Box radioBox = Box.createHorizontalBox();
    JLabel userLabel = new JLabel("用户名: ");
    JTextField userTextField = new JTextField(15);
    JLabel passwordLabel = new JLabel("密    码: ");
    JPasswordField passwordTextField = new JPasswordField(15);
    JButton loginBtn = new JButton("登录");
    JButton registerBtn = new JButton("注册");
    JRadioButton radioAutomaticLogon = new JRadioButton("管理员");
    JRadioButton radioRememberPassword = new JRadioButton("记住密码");
    private static final String FILENAME = "/res/credentials.txt"; // 保存用户名和密码的文件名
    ActionListener actionListener = e -> {
        if (e.getSource() == loginBtn) {
            // 处理登录按钮点击事件
            String username = userTextField.getText();
            LoginInterfaceUserName = username;
            String password = new String(passwordTextField.getPassword());

            if (radioAutomaticLogon.isSelected() && JdbcUtils.isAdministrators(username,password))
            {
                if (radioRememberPassword.isSelected()) saveCredentials(username,password,"1");
                else saveCredentials(username,password,"0");

                JOptionPane.showMessageDialog(null, "管理员登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                setAdministratorPrivileges(true);
                new MainWindow().Init();
                jFrame.dispose();
            }else if (!radioAutomaticLogon.isSelected() && !JdbcUtils.isAdministrators(username,password)
                && JdbcUtils.validateLogin(username,password))
            {
                if (radioRememberPassword.isSelected()) saveCredentials(username,password,"1");
                else saveCredentials(username,password,"0");

                JOptionPane.showMessageDialog(null, "用户登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                setAdministratorPrivileges(false);
                new MainWindow().Init();
                jFrame.dispose();
            }else {
                JOptionPane.showMessageDialog(null, "登录失败，请检查账号和密码", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == registerBtn) {
            // 处理注册按钮点击事件
            new RegisterInterface().Init();
            jFrame.dispose();
        }
    };
    public void Init() {
        jFrame.setBounds((GUIUtils.getScreenWidth() - WIDTH) / 2, (GUIUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jFrame.setResizable(false);  //  不允许窗口改变大小
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUIUtils.setWindowIcon(jFrame, "/res/Login.png");

        userTextField.setToolTipText("请输入用户名");
        GUIUtils.addComponents(userBox, userLabel, userTextField);
//        userTextField.setOpaque(false);// 透明背景

        passwordTextField.setToolTipText("请输入密码");
        GUIUtils.addComponents(passwordBox, passwordLabel, passwordTextField);
//        passwordTextField.setOpaque(false);// 透明背景

        GUIUtils.addComponents(btnBox, loginBtn, Box.createHorizontalStrut(60), registerBtn);

        loadCredentials(userTextField,passwordTextField,radioRememberPassword);

        GUIUtils.addComponents(radioBox, radioAutomaticLogon, Box.createHorizontalStrut(60), radioRememberPassword);

        //  添加登录和注册的事件监听
        loginBtn.addActionListener(actionListener);
        registerBtn.addActionListener(actionListener);

        GUIUtils.addComponents(vBox, Box.createVerticalStrut(60), userBox, Box.createVerticalStrut(20), passwordBox,
                Box.createVerticalStrut(20), radioBox, Box.createVerticalStrut(20), btnBox);

        jPanel.add(vBox);
        jFrame.add(jPanel);

        //  设置窗口可见
        jFrame.setVisible(true);
    }
    //  保存用户和密码
    private static void saveCredentials(String username, String password, String t) {
        System.out.println(getResourcePath());
        try (PrintWriter writer = new PrintWriter(new FileWriter(getResourcePath()),true)) {
            writer.println(username);
            writer.println(password);
            writer.println(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 读取用户和密码
    private static void loadCredentials(JTextField usernameField, JPasswordField passwordField, JRadioButton radioRememberPassword) {
        File file = new File(getResourcePath());
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String username = reader.readLine();
                String password = reader.readLine();
                String t = reader.readLine();
                if (t.equals("1"))
                {
                    usernameField.setText(username);
                    passwordField.setText(password);
                    radioRememberPassword.setSelected(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //  获取项目文件路径
    private static String getResourcePath() {
        // 获取资源路径
        return Objects.requireNonNull(LoginInterface.class.getResource(LoginInterface.FILENAME)).getPath();
    }
    //  获取用户名
    public static String getUserName()
    {
        return LoginInterfaceUserName;
    }
    //  设置用户权限
    public static void setAdministratorPrivileges(Boolean administratorPrivileges) {
        LoginInterface.administratorPrivileges = administratorPrivileges;
    }
    //  获取用户权限
    public static Boolean getAdministratorPrivileges() {
        return administratorPrivileges;
    }
}