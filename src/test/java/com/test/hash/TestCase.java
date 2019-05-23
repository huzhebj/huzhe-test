package com.test.hash;

import com.test.util.HashUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestCase {

    @Test
    public void getHashVaue(){
        Long hashVaue = HashUtil.getHashVaue("1953967");
        System.out.println(hashVaue);//7090879707543944829
        System.out.println(hashVaue % 10);//9
    }
}
