package GUI;
import GUI.CHART.ChartDrawer;
import GUI.TABLE.BookManagerTable;
import GUI.TABLE.BorrowManagerTable;
import GUI.TABLE.UserManagerTable;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
public class MainWindow {
    JFrame jFrame = new JFrame("图书借阅管理系统");
    final int WIDTH = 1000;
    final int HEIGHT = 750;
    JSplitPane jSplitPane;
    JTree tree;
    DefaultMutableTreeNode root;
    DefaultMutableTreeNode userManage;
    DefaultMutableTreeNode bookManege;
    DefaultMutableTreeNode borrowManage;
    DefaultMutableTreeNode statisticAnalysis;
    DefaultMutableTreeNode bookInventoryBarChart;
    DefaultMutableTreeNode bookPriceStatisticsChart;
    DefaultMutableTreeNode userBorrowingStatisticsChart;
    private static UserManagerTable userManagerTable = null;
    private static BookManagerTable bookManagerTable = null;
    private static BorrowManagerTable BORROW_MANAGER_TABLE = null;
    MainWindow() {
        userManagerTable = new UserManagerTable(new String[]{"编号", "姓名", "密码", "电话", "性别","注册时间"});
        bookManagerTable = new BookManagerTable(new String[]{"编号", "书名", "类别", "作者", "价格", "库存"});
        BORROW_MANAGER_TABLE = new BorrowManagerTable(new String[]{"编号", "用户名", "书名", "借出日期", "归还日期"});
    }

    public static void UserManagerTableRefresh()
    {
        userManagerTable.Refresh();
    }
    public static void BookManagerTableRefresh()
    {
        bookManagerTable.Refresh();
    }
    public static void BorrowManageTableRefresh()
    {
        BORROW_MANAGER_TABLE.Refresh();
    }
    // 创建工具栏
    JToolBar toolBar = new JToolBar();
    JButton addToolButton = new JButton("添加");
    JButton delToolButton = new JButton("删除");
    JButton revToolButton = new JButton("修改");
    JButton borrowToolButton = new JButton("借阅");
    JButton RefreshToolButton = new JButton("刷新");
    final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addToolButton) { // 添加
                if (tree.isRowSelected(1)) userManagerTable.addData();
                else if (tree.isRowSelected(2)) bookManagerTable.addData();
            } else if (e.getSource() == delToolButton) { // 删除
                if (tree.isRowSelected(1)) userManagerTable.delTableData();
                else if (tree.isRowSelected(2)) bookManagerTable.delTableData();
                else if (tree.isRowSelected(3)) BORROW_MANAGER_TABLE.delTableData();

            } else if (e.getSource() == revToolButton) { // 修改
                if (tree.isRowSelected(1)) userManagerTable.dataModification();
                else if (tree.isRowSelected(2)) bookManagerTable.dataModification();
                else if (tree.isRowSelected(3)) BORROW_MANAGER_TABLE.dataModification();

            } else if (e.getSource() == RefreshToolButton) {
                MainWindow.Refresh();
            } else if (e.getSource() == borrowToolButton)
            {
                if (tree.isRowSelected(2)) bookManagerTable.addBorrow();
            }
        }
    };
    public static JLabel LeftStatusLabel = new JLabel("");
    public static JLabel RightStatusLabel = new JLabel("") {
        private static final Timer timer;
        static {
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RightStatusLabel.setText("");
                    timer.stop();
                }
            });
            timer.setRepeats(false);
        }
        @Override
        public void setText(String text) {
            super.setText(text);
            timer.restart();
        }
    };
    private static void Refresh() {
        UserManagerTableRefresh();
        BookManagerTableRefresh();
        BorrowManageTableRefresh();
        RightStatusLabel.setText("刷新成功");
    }
    private void setMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenuEdit = new JMenu("设置");
        JMenuItem m1 = new JMenuItem("切换账号");
        JMenuItem m2 = new JMenuItem("退出程序");
        JMenu jMenuIllustrate = new JMenu("说明");
        JMenu MenuView = new JMenu("视图");

        final ActionListener menuactionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == m1)
                {
                    new LoginInterface().Init();
                    jFrame.dispose();
                } else if (e.getSource() == m2)
                {
                    jFrame.dispose();
                } else if (e.getSource() == jMenuIllustrate)
                {

                }
            }
        };

        m1.addActionListener(menuactionListener);
        m2.addActionListener(menuactionListener);
        jMenuIllustrate.addActionListener(menuactionListener);

        jMenuBar.add(jMenuEdit);
        jMenuBar.add(jMenuIllustrate);
        jMenuBar.add(MenuView);

        jMenuEdit.add(m1);
        jMenuEdit.add(m2);

        jFrame.setJMenuBar(jMenuBar);
    }
    private void setJSplitPaneLeftComponent() {
        jSplitPane = new JSplitPane();
        //  支持连续布局
        jSplitPane.setContinuousLayout(true);
//        jSplitPane.setDividerLocation(100);
        jSplitPane.setDividerSize(2);

        //  设置左侧内容
        root = new DefaultMutableTreeNode("系统管理");
        userManage = new DefaultMutableTreeNode("用户管理");
        bookManege = new DefaultMutableTreeNode("图书管理");
        borrowManage = new DefaultMutableTreeNode("借阅管理");
        statisticAnalysis = new DefaultMutableTreeNode("统计分析");
        bookInventoryBarChart = new DefaultMutableTreeNode("书籍价格");
        bookPriceStatisticsChart = new DefaultMutableTreeNode("书籍库存");
        userBorrowingStatisticsChart = new DefaultMutableTreeNode("用户借阅");

        statisticAnalysis.add(bookInventoryBarChart);
        statisticAnalysis.add(bookPriceStatisticsChart);
        statisticAnalysis.add(userBorrowingStatisticsChart);

        root.add(userManage);
        root.add(bookManege);
        root.add(borrowManage);
        root.add(statisticAnalysis);

        tree = new JTree(root);
        MyRenderer myRenderer = new MyRenderer();
        //  设置结点绘制器
        tree.setCellRenderer(myRenderer);
        tree.setSelectionRow(2);
        jSplitPane.setLeftComponent(new JScrollPane(tree));
    }
    private void setJSplitPaneRightComponent() {
        tree.addTreeSelectionListener(e -> {
            TreePath path = e.getNewLeadSelectionPath();
            if (path != null) {
                Object lastPathComponent = path.getLastPathComponent();
                if (userManage.equals(lastPathComponent)) {
                    jSplitPane.setRightComponent(userManagerTable);
                } else if (bookManege.equals(lastPathComponent)) {
                    jSplitPane.setRightComponent(bookManagerTable);
                } else if (borrowManage.equals(lastPathComponent)) {
                    jSplitPane.setRightComponent(BORROW_MANAGER_TABLE);
                } else if (bookInventoryBarChart.equals(lastPathComponent)) {
                    jSplitPane.setRightComponent(new JScrollPane
                            (ChartDrawer.getXBookPriceStatisticsChartPanel()));
                } else if (bookPriceStatisticsChart.equals(lastPathComponent))
                {
                    jSplitPane.setRightComponent
                            (new JScrollPane(ChartDrawer.getXBookInventoryBarChartPanel()));
                } else if (userBorrowingStatisticsChart.equals(lastPathComponent))
                {
                    jSplitPane.setRightComponent
                            (new JScrollPane(ChartDrawer.getXBorrowingStatisticsChartPanel()));
                }
            }
        });

        jSplitPane.setRightComponent(bookManagerTable);
    }
    private void setToolBar() {
        final int ICON_WIDTH = 13; // 设置图标宽度
        final int ICON_HEIGHT = 13; // 设置图标高度

        ImageIcon addIcon;
        ImageIcon delIcon;
        ImageIcon revIcon;
        ImageIcon RefreshIcon;
        ImageIcon borrowIcon;

        try {
            addIcon = GUIUtils.getImageIcon("/res/添加.png");
            delIcon = GUIUtils.getImageIcon("/res/删除.png");
            revIcon = GUIUtils.getImageIcon("/res/修改.png");
            RefreshIcon = GUIUtils.getImageIcon("/res/刷新.png");
            borrowIcon = GUIUtils.getImageIcon("/res/借阅.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (addIcon != null) {
            addToolButton.setIcon(new ImageIcon(addIcon.getImage()
                    .getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH)));
        } if (delIcon != null) {
            delToolButton.setIcon(new ImageIcon(delIcon.getImage()
                    .getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH)));
        } if (revIcon != null) {
            revToolButton.setIcon(new ImageIcon(revIcon.getImage()
                    .getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH)));
        } if (RefreshIcon != null) {
            RefreshToolButton.setIcon(new ImageIcon(RefreshIcon.getImage()
                    .getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH)));
        } if (borrowIcon != null) {
            borrowToolButton.setIcon(new ImageIcon(borrowIcon.getImage()
                    .getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH)));
        }


        toolBar.add(addToolButton);
        toolBar.add(delToolButton);
        toolBar.add(revToolButton);
        toolBar.add(RefreshToolButton);
        toolBar.add(borrowToolButton);

        addToolButton.addActionListener(actionListener);
        delToolButton.addActionListener(actionListener);
        revToolButton.addActionListener(actionListener);
        RefreshToolButton.addActionListener(actionListener);
        borrowToolButton.addActionListener(actionListener);

        // 将工具栏添加到 JFrame
        jFrame.add(toolBar, BorderLayout.PAGE_START);

    }
    private void setStatusBar() {
        // 创建状态栏面板
        JPanel statusBarPanel = new JPanel();
        statusBarPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusBarPanel.setPreferredSize(new Dimension(jFrame.getWidth(), 20));
        statusBarPanel.setLayout(new BorderLayout());

        // 创建状态标签
        if (LoginInterface.getAdministratorPrivileges())
        {
            LeftStatusLabel.setText("管理员: " + LoginInterface.getUserName());
        } else {
            LeftStatusLabel.setText("用户: " + LoginInterface.getUserName());
        }

        RightStatusLabel.setText("登录成功");
        LeftStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        RightStatusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // 将标签添加到状态栏面板中
        statusBarPanel.add(LeftStatusLabel, BorderLayout.WEST);
        statusBarPanel.add(RightStatusLabel, BorderLayout.EAST);

        // 将状态栏面板添加到 JFrame 底部
        jFrame.add(statusBarPanel, BorderLayout.SOUTH);
    }
    public void Init() {
        jFrame.setBounds((GUIUtils.getScreenWidth() - WIDTH) / 2,
                (GUIUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
//        jFrame.setResizable(false);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        GUIUtils.setWindowIcon(jFrame, "/res/Book.png");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setMenuBar();
        this.setJSplitPaneLeftComponent();
        this.setJSplitPaneRightComponent();
        this.setToolBar();
        this.setStatusBar();

        jFrame.add(jSplitPane);
        jFrame.setVisible(true);
    }
    //  结点绘制器
    public static class MyRenderer extends DefaultTreeCellRenderer {
        private final ImageIcon rootIcon;
        private final ImageIcon userManageIcon;
        private final ImageIcon bookManegeIcon;
        private final ImageIcon borrowManageIcon;
        private final ImageIcon statisticAnalysisIcon;

        private static final int ICON_WIDTH = 13; // 设置图标宽度
        private static final int ICON_HEIGHT = 13; // 设置图标高度

        MyRenderer() {
            rootIcon = getScaledIcon("/res/系统管理.png");
            userManageIcon = getScaledIcon("/res/用户管理.png");
            bookManegeIcon = getScaledIcon("/res/图书管理.png");
            borrowManageIcon = getScaledIcon("/res/借阅管理.png");
            statisticAnalysisIcon = getScaledIcon("/res/统计分析.png");
        }

        private ImageIcon getScaledIcon(String imagePath) {
            ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
            return new ImageIcon(originalIcon.getImage()
                    .getScaledInstance(ICON_WIDTH, ICON_HEIGHT, java.awt.Image.SCALE_AREA_AVERAGING));
        }

        @Override
        public Component getTreeCellRendererComponent
                (JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            // 使用默认绘制
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            ImageIcon image = switch (row) {
                case 0 -> rootIcon;
                case 1 -> userManageIcon;
                case 2 -> bookManegeIcon;
                case 3 -> borrowManageIcon;
                case 4 -> statisticAnalysisIcon;
                default -> null;
            };
            this.setIcon(image);
            return this;
        }
    }
}
