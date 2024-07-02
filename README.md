# BookBorrowingManagementSystem
This is my Java course design
## Declaration
- The compiler software used is IntelliJ IDEA 2023.1
- The database software is DataGrip 2023.1.1
- Java version is jdk21
- MySQL 8

## Project Structure

### src

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

#### 使用说明
- 登录和注册就不介绍了，管理员用户无法通过数据库创建
主界面：
![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/a6f6da4b-c78a-4777-8243-a1e879548ace)
主界面分为菜单栏、状态栏、工具栏、树型控件和表。

1）可以通过工具栏对表进行增删改查，下面是对图书表进行演示：

增加、删除、修改数据有两种途径，一种是选中数据之后点击工具栏进行中的按钮进行操作，还有一种是选中数据右键弹出右键菜单进行操作
 ![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/8207131b-35cc-4a80-b345-ef4afe0cbdd3)

删除：选中删除之后会弹出对话框，点击确认就会删除数据
![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/d5b7e2bc-1fce-4de8-9d2b-46c68c188a9b)
 
若出现以下提示则说明该书籍存在借阅记录，需要用户将书籍归还之后才可以进行删除
![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/f54b87a5-c3aa-49ba-8d97-8eb5094767d9)

在这里可以看到用户借阅了算法导论，因此无法删除
![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/2e65b59a-e674-4ea0-9cc3-63a655828991)

 
若用户归还算法导论后则可以安全删除算法导论的书籍
 ![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/ab9fc213-15dc-4bf4-91a1-5ce977f99090)


添加：点击添加按钮即可填写信息添加书籍，
![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/c7cefc67-9a0e-4f94-8446-10a5ea13863d)
![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/5fb2648e-d5b0-4e19-a24e-4cd1cb2e4db3)

修改：快捷方式是双击需要修改的书籍即可进行修改
![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/edc34fdc-ccbf-46c5-9c08-806bdf0f017b)

这里选中了编号10的数据将它修改为上面的信息
 ![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/ac9ed527-92ca-413a-99d2-057e889f480f)


查找：在表格最上面有一个搜索框，输入书籍的名称即可查找书籍的信息
 ![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/132aef91-7c14-4821-bfde-dfc6a35b5621)

以上是演示了书籍表的增删改查的操作，用户表与书籍表的操作一致
借阅书籍：用户可以选中书籍右键借阅，若书籍库存足够则填写归还时间之后可以借阅，若库存不足则会输出错误提示框。
![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/1c288c2f-5213-459a-b084-2288e686cfbe)

 成功借阅后借阅信息就会出现在借阅表里面：
 ![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/a91c5c96-1d92-427a-a017-4d5941cab254)

还书：用户在借阅表里面可以对自己借阅的书籍进行归还
 ![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/6344f4ee-c85f-40f8-a4ee-af309de1e5e5)

若改借阅记录不是对应登录的用户则会显示无权归还：
 ![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/5acccf19-964f-4062-9de9-cf683e04dd53)


2）数据分析：系统分别提供了三个数据统计图，书籍价格统计、书籍库存统计、用户借阅统计，以下选取书籍借阅图进行展示，统计图的信息会根据用户操作而更新。
 ![image](https://github.com/Sqhh99/BookBorrowingManagementSystem/assets/127586242/ffbaad6f-f38f-4708-8384-39ed500a8e5a)




