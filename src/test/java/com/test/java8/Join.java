package com.test.java8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Join {

    @Test
    public void join(){
        List<String> list = new ArrayList<>();
        list.add("110");
        list.add("120");
        list.add("130");
        list.add("140");
        String join = String.join(",", list);
        System.out.println(join);//110,120,130,140
    }
}
