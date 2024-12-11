package GUI.DIALOG;

import GUI.GUIUtils;

import javax.swing.*;
import java.awt.*;

public class UserCustomInputDialog extends JDialog {
    JPanel jPanel = new JPanel();
    Box vBox = Box.createVerticalBox();  //  垂直布局
    Box userBox = Box.createHorizontalBox();  //  用户名水平布局
    Box passwordBox = Box.createHorizontalBox();  //  密码水平布局
    Box phoneBox = Box.createHorizontalBox();
    Box gBox = Box.createHorizontalBox();
    Box btnBox = Box .createHorizontalBox();
    JLabel userLabel = new JLabel("用户名: ");
    public JTextField userTextField = new JTextField(15);
    JLabel passwordLabel = new JLabel("密    码: ");
    public JPasswordField passwordTextField = new JPasswordField(15);
    JLabel phoneLabel = new JLabel("手机号: ");
    public JTextField phoneTextField = new JTextField(15);
    JLabel sexLabel = new JLabel("性    别: ");
    public JRadioButton maleBtn = new JRadioButton("男" , true);
    public JRadioButton femaleBtn = new JRadioButton("女", false);
    ButtonGroup bg = new ButtonGroup();
    private boolean confirmed;
    public UserCustomInputDialog(JFrame parent, String dialogTitle) {
        super(parent, dialogTitle, true);
        setLayout(new BorderLayout());
        this.setResizable(false);

        userTextField.setToolTipText("请输入用户名");
        GUIUtils.addComponents(userBox, userLabel, userTextField);

        passwordTextField.setToolTipText("请输入密码");
        GUIUtils.addComponents(passwordBox, passwordLabel, passwordTextField);

        passwordTextField.setToolTipText("请输入手机号");
        GUIUtils.addComponents(phoneBox, phoneLabel, phoneTextField);

        bg.add(maleBtn);
        bg.add(femaleBtn);
        GUIUtils.addComponents(gBox, sexLabel, maleBtn, femaleBtn, Box.createHorizontalStrut(120));

        GUIUtils.addComponents(vBox, userBox, Box.createVerticalStrut(10),
                passwordBox, Box.createVerticalStrut(10), phoneBox, Box.createVerticalStrut(10), gBox,
                Box.createVerticalStrut(10),btnBox);


        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        JButton cancelButton = new JButton("关闭");
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        GUIUtils.addComponents(btnBox, okButton, Box.createHorizontalStrut(60), cancelButton);
        jPanel.add(vBox);

        add(jPanel);

        pack();
        setLocationRelativeTo(parent);
    }
    public String getUsername() {
        return userTextField.getText();
    }
    public String getPassword() {
        return new String(passwordTextField.getPassword());
    }
    public String getPhone() {
        return phoneTextField.getText();
    }
    public boolean getSex() {
        return maleBtn.isSelected();
    }
    public boolean isConfirmed() {
        return confirmed;
    }
}
