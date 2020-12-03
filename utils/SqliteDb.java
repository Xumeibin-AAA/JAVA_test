package StudentManage.utils;
import StudentManage.model.Student;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
sqlite 相关的操作
1.连接数据库
2.执行sql语句
3.断开连接
 */
public class SqliteDb {
    public static Connection conn;
    /*
    连接数据库：通过驱动连接，每种类型的数据库有对应的驱动，驱动的版本与数据库版本有对应关系
    驱动是一个jar 下载地址：https://mvnrepository.com/
     */
    public static void main(String[] args) {
//        initTable();
//        Student stu = new  Student("1003","ccc","女","20","十年级","15202990560");
//        addStudent(stu);
//        boolean b =  deletStudent("aa1");
//        System.out.println(b);
//        SqliteDb db = new SqliteDb();
//        db.connect();

//        List<Student> content = queryStudent("aa");
//        for(Student s:content){
//            System.out.print("姓名:" +s.getName() + "  ");
//            System.out.print("学号:" + s.getId() + "  ");
//            System.out.print("年龄:" + s.getAge() + "  ");
//            System.out.print("班级:" +s.getClasses() + "  ");
//            System.out.println("电话:" + s.getPhone() + "  ");
//        }
    }
    private void connect(){
        String JDBC_DERIVER = "org.sqlite.JDBC";//常量： 驱动名字
        String JDBC_URL = "jdbc:sqlite:student.db";//常量： 数据库的访问路径

        try{
            //加载驱动
            Class.forName(JDBC_DERIVER);
            //连接数据库
            conn = DriverManager.getConnection(JDBC_URL);
            System.out.println("数据库连接成功");

        } catch (Exception e){
            System.out.println("数据库连接失败" + e.getMessage());
        }

    }
    /*
    断开数据库
     */
    private void close(){
        try {
            conn.close();
            System.out.println("关闭成功");
        } catch (Exception e) {
            System.out.println("数据库断开失败" + e.getMessage());;
        }

    }
    /*
    执行增删改
     */
    private static boolean execute(String sql){
        try{
            Statement statement = conn.createStatement();
            statement.execute(sql);
            int count = statement.getUpdateCount();//获取影响了多少条数据
            statement.close();
            System.out.println("执行sql语句：" + sql + count + "条记录被更新。");
            return count>=1;//判断执行成功还是失败 执行成功时至少有一条数据被修改

        } catch (Exception e){
            System.out.println("执行sql语句异常" + e.getMessage());
        }

        return false;


    }
    /*
    执行查询类的sql,返回表信息
     */
    private List<Student> executeQuery(String sql){
        List<Student> students = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();
            boolean b =  statement.execute(sql);
            if(b) {
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String gender = rs.getString("gender");
                    String age = rs.getString("age");
                    String phone = rs.getString("phone");
                    String classes = rs.getString("classes");
//                    System.out.println(id);
                    Student stu = new Student(id, name, gender, age, classes, phone);
                    students.add(stu);

                }
            }
            System.out.println("执行sql语句：" + sql + "查询数据" + students.size());
            statement.close();

        } catch (Exception e){
            System.out.println("执行sql语句异常" + e.getMessage());
        }


        return students;

    }
    //初始化数据库
    public  static void initTable(){
        SqliteDb db = new SqliteDb();
        db.connect();
        db.execute("create table if not exists student(id varchar(32) primary key, name varchar(10),classes varchar(10), age varchar(8)\n" +
                ",gender varchar(2),phone varchar(16));");
        db.close();


    }
    /*
    添加学生
    好处：1.调用简单 2.屏蔽了sql/数据库 如果更改数据库，只需要修改这个文件
     */
    public  static boolean addStudent(Student student){
        String id = student.getId();
        String name = student.getName();
        String gender = student.getGender();
        String phone = student.getPhone();
        String age = student.getAge();
        String classes = student.getClasses();
        SqliteDb db = new SqliteDb();
        db.connect();
        boolean b = db.execute("insert into student" +
                "(id,name,classes,age,gender,phone) " +
                "values ('" + id + "','" +name + "','" +
                classes + "','" + age + "'," +
                "'" +gender + "','" +  phone + "');");
        db.close();
        return b;

    }
    public static boolean deletStudent(String name){
        SqliteDb db = new SqliteDb();
        db.connect();
        boolean b = db.execute("delete from student where name ='" + name + "';");
        db.close();
        return b;
    }
    public static List<Student> queryStudent(){
        SqliteDb db = new SqliteDb();
        db.connect();
        List<Student> students =  db.executeQuery("select * from student;");
        db.close();
        return students;
    }
    public static List<Student> queryStudent(String name){
        SqliteDb db = new SqliteDb();
        db.connect();
        List<Student> students = db.executeQuery("select * from student where name like '%" + name + "%';");
        db.close();
        return students;
    }
    /*
    list 转二维数据
     */
    public static  String[][] list2Arrays(List<Student> students){
        String[][] res =null;
        if(students.size()>0){
            //数据初始化长度
            res = new String[students.size()][6];
            for(int j = 0; j< students.size();j++){
                Student stu = students.get(j);
                res[j][0] = stu.getId();
                res[j][1] = stu.getName();
                res[j][2] = stu.getGender();
                res[j][3] = stu.getAge();
                res[j][4] = stu.getClasses();
                res[j][5] = stu.getPhone();
            }

//            for(Student s:students){
//
//            }

        }
        return res;


    }

    public static  boolean updateStudent(String[] arr){
        System.out.println(Arrays.toString(arr));
        String id =arr[0];
        String name =arr[1];
        String age = arr[2];
        String gender = arr[3];
        String classes = arr[4];
        String phone = arr[5];
        SqliteDb db = new SqliteDb();
        db.connect();
       boolean b = execute("update student set name = '" + name + "', classes = '" + classes + "',age = '" + age + "',gender = '" + gender + "',phone = '" + phone + "' where id = '" + id + "';");
       db.close();
       return true;

    }

}

