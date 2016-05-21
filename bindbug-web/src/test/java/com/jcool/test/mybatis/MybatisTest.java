package com.jcool.test.mybatis;

import com.bindbug.dao.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yan on 16/1/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml"})
public class MybatisTest {

    @Autowired
    AdminMapper adminMapper;

    @Test
    public void test(){
        System.out.println(adminMapper.selectByPrimaryKey(1));
    }

}
