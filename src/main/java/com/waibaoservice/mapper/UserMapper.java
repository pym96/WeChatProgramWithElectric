package com.waibaoservice.mapper;

import com.waibaoservice.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int insertUserInfo(@Param("openid")String openid,
                       @Param("session_key")String session_key,
                       @Param("unionid")String unionId);
    User selectUserByOpenId(@Param("openid")String openid);
}
