package com.waibaoservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.waibaoservice.mapper.UserMapper;
import com.waibaoservice.pojo.User;
import com.waibaoservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DJS
 * Date create 23:01 2023/2/19
 * Modified By DJS
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    public UserServiceImpl() {}

    // 通过json字符串生成User对象
    @Override
    public User userLogin(String json_str) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json_str , User.class);
        System.out.println(user);
        // 需要先查看数据库是否又存在此人，如果存在，不需要插入
        User u = this.mapper.selectUserByOpenId(user.getOpenid());
        // 不存在此人
        if (u == null) {
            // 保存数据至数据库
            int res = this.mapper.insertUserInfo(
                    user.getOpenid(),
                    user.getSession_key(),
                    user.getUnionid());
            if (res != 1) System.out.println("登录失败, 插入数据失败");
            else System.out.println("登录成功, 插入数据成功");
        }
        else {
            System.out.println("登录成功,用户存在");
        }
        return user;
    }
}
