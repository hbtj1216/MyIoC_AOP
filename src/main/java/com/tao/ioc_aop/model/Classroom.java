package com.tao.ioc_aop.model;

/**
 * Created by Michael on 2017/6/12.
 */

/**
 * 班级
 */
public class Classroom {

    private String classroomName;   //班级名称

    public Classroom() {

    }

    public Classroom(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "classroomName='" + classroomName + '\'' +
                '}';
    }
}
