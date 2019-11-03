package com.test.java8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestCase {

    @Test
    public void demo1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        MyCallable myCallable = new MyCallable();
        Future<Student> submit = executorService.submit(myCallable);
        executorService.shutdown();
        Student student = submit.get();
        System.out.println(student);//
    }
}
