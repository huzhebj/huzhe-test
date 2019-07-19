package com.test.java8;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Student> {
    @Override
    public Student call() throws Exception {
        Student student = new Student();
        student.setName("小明");
        student.setAge(18);
        return student;
    }
}
