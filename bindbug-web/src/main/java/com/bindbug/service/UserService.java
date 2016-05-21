package com.bindbug.service;

import com.bindbug.dao.UserMapper;
import com.bindbug.model.User;
import com.bindbug.model.UserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yan on 16/1/25.
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    public User login(String loginName, String passowrd){
        User user = null;
        try{
            UserExample userExample = new UserExample();
            UserExample.Criteria userExampleCriteria = userExample.createCriteria();
            userExampleCriteria.andLoginNameEqualTo(loginName).andPasswordEqualTo(passowrd);
            List<User> userList = userMapper.selectByExample(userExample);
            if(userList.size() > 0){
                user = userList.get(0);
            }
        }catch (Exception e){
            logger.error("登录查询用户失败....", e);
            e.printStackTrace();
        }
        return user;
    }

    public User findUserById(Integer id){
        User user = null;
        try{
            user = userMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("根据用户ID查询用户失败id:{}, exception:{}...", id, e);
        }
        return user;
    }
}
