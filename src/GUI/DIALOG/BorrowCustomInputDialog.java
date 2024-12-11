package GUI.DIALOG;

import GUI.DateSelector;
import GUI.GUIUtils;
import GUI.GroupBox;
import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

public class BorrowCustomInputDialog extends JDialog {
    JPanel jPanel = new JPanel();
    Box vBox = Box.createVerticalBox();  //  垂直布局
    Box btnBox = Box .createHorizontalBox();
    private boolean confirmed;
    private final DateSelector dateSelector = new DateSelector();
    public BorrowCustomInputDialog(JFrame parent,String dialogTitle) {
        super(parent, dialogTitle, true);
        setLayout(new BorderLayout());
        this.setResizable(false);

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

        GroupBox groupBox = new GroupBox("归还日期");
        groupBox.addComponent(dateSelector);
        GUIUtils.addComponents(btnBox, okButton, Box.createHorizontalStrut(60), cancelButton);

        GUIUtils.addComponents(vBox,
                Box.createVerticalStrut(10), groupBox,
                Box.createVerticalStrut(10), btnBox);

        jPanel.add(vBox);

        add(jPanel);

        pack();
        setLocationRelativeTo(parent);
    }
    public Timestamp getTimestamp()
    {
        return dateSelector.getSelectedTimestamp();
    }
    public boolean isConfirmed()
    {
        return confirmed;
    }

}