# BookBorrowingManagementSystem
This is my Java course design
# Declaration
- The compiler software used is IntelliJ IDEA 2023.1
- The database software is DataGrip 2023.1.1
- Java version is jdk21
- MySQL 8

# Project Structure

## src

#### FCLASS
- **Book** ----> 图书类
- **Borrowing** ----> 借阅记录
- **User** ----> 用户类

#### CHART
- **CharDrawer** ----> 绘图类

#### DIALOG
- **BookCustomInputDialog** ----> 图书自定义输入框
- **BorrowCustomInputDialog** ----> 借阅记录输入框
- **TableDialog** ----> 用户搜索结果显示框
- **UserCustomInputDialog** ----> 用户信息输入框

#### GUI
- **BookManagerTable** ----> 图书表
- **BorrowManagerTable** ----> 借阅记录表
- **TableBase** ----> 表的基类, 用于继承
- **UserManagerTable** ----> 用户表
- **DateSelector** ----> 时间选择器组件
- **GroupBox** ----> 组件容器
- **GUtils** ----> GUI工具类
- **LoginInterface** ----> 登录界面
- **Mainwindow** ----> 主界面
- **RegisterInterface** ----> 注册界面

#### lib
- **[mysql]** ----> 数据库驱动
- **[swing]** ----> 图形用户界面
- **xchart** ----> 绘图库

#### res
- **\*.png** ----> 组件图片
- **db.properties** ----> 数据库配置文件

#### SQL
- **DialogAddTable** ----> 数据表
- **DialogUpdateTable** ----> 数据更新

#### Test
- **Main** ----> 主类用于执行

