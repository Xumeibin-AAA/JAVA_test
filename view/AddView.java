package StudentManage.view;

import StudentManage.model.Student;
import StudentManage.utils.SqliteDb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

/*
添加页面
1.属性（Label,TextField）
2.确定，取消
 */
public class AddView extends JFrame {
    JTextField nameField,ageField,classesField,genderField,phoneField;
    public AddView(){
        this.add(northPanel(), BorderLayout.NORTH);
        this.add(southPanel(),BorderLayout.SOUTH);
        setTitle("添加学生");
        setBounds(650,300,500,300);
        setVisible(true);

    }

    private JPanel northPanel(){
        JPanel jPanel = new JPanel();
        GridLayout grid = new GridLayout(5,2);
        jPanel.setLayout(grid);


        JLabel nameLabel = new JLabel("姓名:");
        jPanel.add(nameLabel);
        nameField = new JTextField();
        nameField.setFont(new Font("楷体", Font.BOLD,30));
        nameLabel.setFont(new Font("楷体", Font.BOLD,30));
        jPanel.add(nameField);
        JLabel ageLabel = new JLabel("年龄:");
        jPanel.add(ageLabel);
        ageField = new JTextField();
        ageField.setFont(new Font("楷体", Font.BOLD,30));
        ageLabel.setFont(new Font("楷体", Font.BOLD,30));
        jPanel.add(ageField);
        JLabel classesLabel = new JLabel("班级:");
        jPanel.add(classesLabel);
        classesField = new JTextField();
        classesField.setFont(new Font("楷体", Font.BOLD,30));
        classesLabel.setFont(new Font("楷体", Font.BOLD,30));
        jPanel.add(classesField);
        JLabel genderLabel = new JLabel("性别:");
        jPanel.add(genderLabel);
        genderField = new JTextField();
        jPanel.add(genderField);
        genderField.setFont(new Font("楷体", Font.BOLD,30));
        genderLabel.setFont(new Font("楷体", Font.BOLD,30));
        JLabel phoneLabel = new JLabel("电话:");
        jPanel.add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setFont(new Font("楷体", Font.BOLD,30));
        phoneLabel.setFont(new Font("楷体", Font.BOLD,30));
        jPanel.add(phoneField);


        return jPanel;
    }
    private JPanel southPanel(){
        JPanel jPanel = new JPanel();
        GridLayout grid = new GridLayout(1,2);
        jPanel.setLayout(grid);
        JButton ok = new JButton("OK");
        ok.setFont(new Font("楷体",Font.BOLD,40));
        jPanel.add(ok);
        ok.addActionListener(new ok());
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("楷体",Font.BOLD,40));
        jPanel.add(cancel);
        cancel.addActionListener(new cancel());



        return jPanel;
    }
    private class ok implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //UUID universally uniqus identifier
            String id = UUID.randomUUID().toString().replaceAll("-","");
            String name = nameField.getText();
            String age = ageField.getText();
            String classes = classesField.getText();
            String gender = genderField.getText();
            String phone = phoneField.getText();



            Student stu = new Student(id,name,gender,age,classes,phone);
            boolean issuccess =  SqliteDb.addStudent(stu);//将学生信息写入数据库
            if(issuccess){
                JOptionPane.showMessageDialog(null,"添加成功");
                //刷新表格
                MainView.initTable(MainView.jTable,SqliteDb.queryStudent());
            }
        }
    }
    private class cancel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();//关闭对话框

        }
    }
}
