package com.waibaoservice.mapper;


import com.waibaoservice.pojo.Wife;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WifeMapper {
    Wife selectWifeById(@Param("deviceId") String deviceId);
    int addWife(@Param("deviceId")String deviceId, @Param("account") String account, @Param("psd")String psd);
    int modifyWife(@Param("deviceId")String deviceId, @Param("account") String account, @Param("psd")String psd);
}
