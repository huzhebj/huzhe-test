package com.test.java8;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
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

    @Test
    public void demo2() throws ExecutionException, InterruptedException {
        String s = "{\"hasEditBusinessTypeToolbarButton\":true}";
        JSONObject jsonObject = JSONObject.parseObject(s);
        jsonObject.put("name","xiaoming");

        System.out.println("--------------------------" + jsonObject.toJSONString());//
    }

    @Test
    public void demo3() throws ExecutionException, InterruptedException {
        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("hasEditBusinessTypeToolbarButton", true);
        property = jsonObject.toJSONString();
        layout.setProperty(property);*/
        Student student = new Student();
        student.setName("jack");
        student.setAge(12);
        student.setProperty("{\"boolean\":false}");
        String property = student.getProperty();
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotBlank(property)){
            jsonObject = JSONObject.parseObject(property);
        }
        jsonObject.put("boolean",true);
        property = jsonObject.toJSONString();
        student.setProperty(property);
        System.out.println(student);//
    }
    @Test
    public void demo4() throws ExecutionException, InterruptedException {
        String property = "{\"name\":\"jack\"}";
        Boolean aBoolean = JSONObject.parseObject(property).getBoolean("hasEditBusinessTypeToolbarButton");
        System.out.println(aBoolean);
        System.out.println(aBoolean == true);
        /*if (property != null && JSONObject.parseObject(property).getBoolean("hasEditBusinessTypeToolbarButton") == true) {
            return;
        }*/
    }
}
