package GUI.DIALOG;

import GUI.GUIUtils;

import javax.swing.*;
import java.awt.*;

public class BookCustomInputDialog extends JDialog {
    JPanel jPanel = new JPanel();
    Box vBox = Box.createVerticalBox();
    Box bookNameBox = Box.createHorizontalBox();
    Box categoryBox = Box.createHorizontalBox();
    Box authorBox = Box.createHorizontalBox();
    Box priceBox = Box.createHorizontalBox();
    Box stockBox = Box.createHorizontalBox();
    Box btnBox = Box .createHorizontalBox();
    JLabel bookName = new JLabel("书    名: ");
    public JTextField bookTextField = new JTextField(15);
    JLabel categoryLabel = new JLabel("类    别: ");
    public JTextField categoryTextField = new JTextField(15);
    JLabel authorLabel = new JLabel("作    者: ");
    public JTextField authorTextField = new JTextField(15);
    JLabel priceLabel = new JLabel("价    格: ");
    public JTextField priceTextField = new JTextField(15);
    JLabel stockLabel = new JLabel("库    存: ");
    public JTextField stockTextField = new JTextField(15);
    private boolean confirmed;
    private final JButton okButton;
    private final JButton cancelButton;
    public BookCustomInputDialog(JFrame parent, String dialogTitle) {
        super(parent, dialogTitle, true);
        setLayout(new BorderLayout());
        this.setResizable(false);

        bookTextField.setToolTipText("请输入书名");
        GUIUtils.addComponents(bookNameBox, bookName, bookTextField);

        categoryTextField.setToolTipText("请输入类别");
        GUIUtils.addComponents(categoryBox, categoryLabel, categoryTextField);

        authorTextField.setToolTipText("请输入作者");
        GUIUtils.addComponents(authorBox, authorLabel, authorTextField);

        priceTextField.setToolTipText("请输入价格");
        GUIUtils.addComponents(priceBox, priceLabel, priceTextField);

        stockTextField.setToolTipText("请输入库存");
        GUIUtils.addComponents(stockBox, stockLabel, stockTextField);

        GUIUtils.addComponents(vBox, bookNameBox,
                Box.createVerticalStrut(5), categoryBox,
                Box.createVerticalStrut(5), authorBox,
                Box.createVerticalStrut(5), priceBox,
                Box.createVerticalStrut(5), stockBox,
                Box.createVerticalStrut(5), btnBox);


        okButton = new JButton("确定");
        okButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        cancelButton = new JButton("关闭");
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        GUIUtils.addComponents(btnBox,okButton, Box.createHorizontalStrut(60), cancelButton);
        jPanel.add(vBox);

        add(jPanel);

        pack();
        setLocationRelativeTo(parent);
    }
    public String getBookName() { return bookTextField.getText(); }
    public String getCategory() { return categoryTextField.getText(); }
    public String getAuthor() { return authorTextField.getText(); }
    public String getPrice() { return priceTextField.getText(); }
    public String getStock() { return stockTextField.getText(); }
    public boolean isConfirmed() { return confirmed; }
}
