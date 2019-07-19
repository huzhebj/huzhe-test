package com.test.hash;

import com.test.util.HashUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestCase {

    @Test
    public void getHashVaue(){
        //1.整数的加法
        int a = 10;
        int b = 20;
        System.out.println(a + b);
        System.out.println("------------------");
        //2.字符参与加法操作
        char c = '0';
        char c2 = 'a';
        System.out.println(a + c);
        System.out.println(a + c2);
        System.out.println("------------------");
        //3.字符串参与加法操作
        System.out.println("hello" + a);
        System.out.println("hello" + a + b); //"hello"+10="hello10",然后再和b进行拼接
        System.out.println(a + b + "hello");//从左到右依次计算,先求和,在连接

    }

    @Test
    public void demo() throws UnsupportedEncodingException {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int i1 = random.nextInt(10);
            System.out.println(i1);
        }


    }

    @Test
    public void test() throws UnsupportedEncodingException {
        String res = null;
        Map<String, Object> response = new HashMap<>();
        res = response.get("res").toString();


    }
}
