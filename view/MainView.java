package StudentManage.view;

import StudentManage.model.Student;
import StudentManage.utils.SqliteDb;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
主页面
1.按钮： 增删改查
2.学生表格（全部学生信息
 */

//顶层容器，任何组件必须放在顶层容器内
public class MainView extends JFrame {
    /*
    构造方法里面绘制页面
     */
    public static List<String> l;
    JTextField searchText;
    public static JTable jTable;

    public MainView(){
        this.add(northPanel(), BorderLayout.NORTH);
        this.add(centerPanel(),BorderLayout.CENTER);

        setTitle("学生管理");//标题
        //左上角 0，0
        setBounds(400,200,1200,600);//Frame位置大小
        setVisible(true);//设置窗口可见


    }
    /*
    北边的Panel，存放增删改查的按钮
     */
    private  JPanel northPanel(){
        JPanel north = new JPanel();
        //一行五列 GridLayout是网格布局管理器
        GridLayout grid = new GridLayout(1,5);
        north.setLayout(grid); //设置Panel的布局管理器

        //组件 JButton 是按钮
        JButton jButtonAdd = new JButton("添加");
        jButtonAdd.addActionListener(new AddAction());
        north.add(jButtonAdd);
        JButton jButtonModify = new JButton("修改");
        jButtonModify.addActionListener(new ModifyAction());
        north.add(jButtonModify);
        JButton jButtonDelete = new JButton("删除");
        jButtonDelete.addActionListener(new DeletedAction());
        north.add(jButtonDelete);
        //JTextField 单行输入
        searchText= new JTextField();//查找输入框
        north.add(searchText);
        JButton jButtonFind = new JButton("查找");
        jButtonFind.addActionListener(new FindAction());//按钮的监听事件
        north.add(jButtonFind);



        return north;
    }
    /*
    中间的Panel,存放学生信息的表格
     */
    private  JPanel centerPanel(){
        JPanel center = new JPanel();
        GridLayout gl = new GridLayout(1,1);
        center.setLayout(gl);
        jTable = new JTable();//创建表格
        //单元格属性
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);//设置单元格里面的数据居中对齐
        //默认单元格渲染器
        jTable.setDefaultRenderer(Object.class,cr);

        //初始化表格
        initTable(jTable,SqliteDb.queryStudent());
        //center.add(jTable);//直接把table加到Panel不显示表头
        //滚动条（水平，垂直）
        JScrollPane jScrollPane = new JScrollPane(jTable);
        center.add(jScrollPane);
        jTable.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {



                int[] count = jTable.getSelectedRows();
                l = new ArrayList<>();
                List<String> str = new ArrayList<>();
                for(int i:count){

                            str.add((String) jTable.getValueAt(i,0));
                            str.add((String) jTable.getValueAt(i,1));
                            str.add((String) jTable.getValueAt(i,2));
                            str.add((String) jTable.getValueAt(i,3));
                            str.add((String) jTable.getValueAt(i,4));
                            str.add((String) jTable.getValueAt(i,5));
                }
                l.addAll(str);



            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {




            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



        return center;
    }
    //
    public static void initTable(JTable jTable,java.util.List<Student> student){
        String[][] res = SqliteDb.list2Arrays(student);
        String[] columns = {"编号","姓名","性别","年龄","班级","电话"};
        //表的数据模型，通过一个Vector存储数据
        TableModel tableModel = jTable.getModel();

        ((DefaultTableModel)tableModel).setDataVector(res,columns);
        jTable.setRowHeight(50);//设置表格的高度

    }
    /*
    点击查找按钮，实现的功能
     */


    private class FindAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String param = searchText.getText(); //获取文本框输入
            java.util.List<Student> students = new ArrayList<>();//定义一个列表获取查询结果
            if(param == null || param.equals("")){
                students = SqliteDb.queryStudent();//查询所有数据



            }else{
                students = SqliteDb.queryStudent(param);//根据名字查询数据库

            }
            initTable(jTable,students);


            }

        }
    private static class AddAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            new AddView();

        }

    }
    private static class DeletedAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            new DeleteView();
        }

    }
    private static class ModifyAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            update();

        }

    }




    public static void update(){
        int counts =0;
        for(int i = 0;i<l.size();i=i + 6){
            String[] arr ={l.get(i),l.get(i+1),l.get(i+2),l.get(i+3),l.get(i+4),l.get(i+5)};
            boolean b = SqliteDb.updateStudent(arr);
            counts = counts + 1;
            if(b){
                if(counts >= l.size()/6) {
                    JOptionPane.showMessageDialog(null, "更新完成");
                }

            }else {
                JOptionPane.showMessageDialog(null,"更新失败");
            }










        }

            }

    }



