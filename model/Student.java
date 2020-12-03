package StudentManage.model;

import java.util.SplittableRandom;

public class Student {
    private String id;
    private String name;
    private String gender;
    private String age;
    private String classes;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Student(String id, String name, String gender, String age, String classes, String phone) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.classes = classes;
        this.phone = phone;
    }
}
