package com.tao.ioc_aop.model;

/**
 * Created by Michael on 2017/6/12.
 */

/**
 * 学生类
 */
public class Student {

    private String name;    //姓名
    private int age;        //年龄
    private float score;    //成绩
    private Classroom classroom;    //所属的班级

    public Student() {

    }

    public Student(String name, int age, float score, Classroom classroom) {
        this.name = name;
        this.age = age;
        this.score = score;
        this.classroom = classroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", classroom=" + classroom +
                '}';
    }
}
