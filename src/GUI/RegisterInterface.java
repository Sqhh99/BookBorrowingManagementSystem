package GUI;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Vector;
import static SQL.JdbcUtils.insertData;

public class RegisterInterface {
    JFrame jFrame = new JFrame("注册");
    JPanel jPanel = new JPanel();
    final int WIDTH = 400;
    final int HEIGHT = 350;
    Box vBox = Box.createVerticalBox();  //  垂直布局
    Box userBox = Box.createHorizontalBox();  //  用户名水平布局
    Box passwordBox = Box.createHorizontalBox();  //  密码水平布局
    Box phoneBox = Box.createHorizontalBox();
    Box gBox = Box.createHorizontalBox();
    Box btnBox = Box .createHorizontalBox();
    JLabel userLabel = new JLabel("用户名: ");
    JTextField userTextField = new JTextField(15);
    JLabel passwordLabel = new JLabel("密    码: ");
    JPasswordField passwordTextField = new JPasswordField(15);
    JLabel phoneLabel = new JLabel("手机号: ");
    JTextField phoneTextField = new JTextField(15);
    JLabel gLable = new JLabel("性    别: ");
    JRadioButton maleBtn = new JRadioButton("男" , true);
    JRadioButton femaleBtn = new JRadioButton("女", false);
    ButtonGroup bg = new ButtonGroup();
    JButton registerBtn = new JButton("注册");
    JButton backBtn = new JButton("返回");
    //  设置事件监听
    ActionListener actionListener = e -> {
        //  注册事件监听
        if (e.getSource() == registerBtn) {
            // 处理注册按钮点击事件
            String username = userTextField.getText();
            String password = new String(passwordTextField.getPassword());
            String phone = phoneTextField.getText();

//            System.out.println(phone);
            if (username.isEmpty() || password.isEmpty() || phone.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "注册用户数据时发生错误","错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String insertUserSQL = "INSERT INTO users (username, password_hash, phone, gender, created_at) VALUES (?, ?, ?, ?, ?)";

            // 创建参数向量并设置参数值
            Vector<Object> params = new Vector<>();
            params.add(username); // 用户名
            params.add(password); // 密码哈希值
            params.add(phone); // 电话号码
            if (maleBtn.isSelected()) {
                params.add("M"); // 性别为男性
            } else {
                params.add("F"); // 性别为女性
            }

            params.add(Timestamp.valueOf(LocalDateTime.now())); // 创建时间

            try {
                insertData(insertUserSQL, params);
                System.out.println("用户数据插入成功！");
                JOptionPane.showMessageDialog(null, "用户注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                new LoginInterface().Init();
                jFrame.dispose();
            } catch (SQLException et) {
                System.err.println("注册用户数据时发生错误：" + et.getMessage());
                JOptionPane.showMessageDialog(null, "注册用户数据时发生错误：" + et.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        //  返回按钮监听
        } else if (e.getSource() == backBtn) {
            new LoginInterface().Init();
            jFrame.dispose();
        }
    };
    //  初始化
    public void Init() {
        jFrame.setBounds((GUIUtils.getScreenWidth() - WIDTH) / 2,(GUIUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        jFrame.setResizable(false);

        GUIUtils.setWindowIcon(jFrame, "/res/Register.png");

        userTextField.setToolTipText("请输入用户名");
        GUIUtils.addComponents(userBox, userLabel, userTextField);

        passwordTextField.setToolTipText("请输入密码");
        GUIUtils.addComponents(passwordBox, passwordLabel, passwordTextField);

        passwordTextField.setToolTipText("请输入手机号");
        GUIUtils.addComponents(phoneBox, phoneLabel, phoneTextField);

        bg.add(maleBtn);
        bg.add(femaleBtn);
        GUIUtils.addComponents(gBox, gLable, maleBtn, femaleBtn, Box.createHorizontalStrut(120));

        GUIUtils.addComponents(btnBox, registerBtn, Box.createHorizontalStrut(60), backBtn);

        GUIUtils.addComponents(vBox, Box.createVerticalStrut(55), userBox, Box.createVerticalStrut(20),
                passwordBox, Box.createVerticalStrut(20), phoneBox, Box.createVerticalStrut(20), gBox,
                Box.createVerticalStrut(20), btnBox);

        registerBtn.addActionListener(actionListener);
        backBtn.addActionListener(actionListener);

        jPanel.add(vBox);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }
}
