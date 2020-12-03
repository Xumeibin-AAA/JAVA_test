package StudentManage.run;
/*
主函数
 */

import StudentManage.utils.SqliteDb;
import StudentManage.view.MainView;

public class Main {
    public static void main(String[] args) {
        //1.创建数据库，初始化表
        SqliteDb.initTable();
        //2.创建主页面
        new MainView();

    }
}
