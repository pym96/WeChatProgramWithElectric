package com.waibaoservice.mapper;

import com.waibaoservice.pojo.KitchenHood;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenMapper {
    int addTimer(@Param("device_id") String deviceId,
                 @Param("openid") String openId,
                 @Param("end_time") String endTime);

    int updateTimer(@Param("device_id") String deviceId,
                    @Param("end_time") String endTime);

    int removeTimer(@Param("device_id") String deviceId);

    KitchenHood selectKitChenHoodById(@Param("device_id") String deviceId);
    KitchenHood selectEndTimeById(@Param("device_id") String deviceId);
    List<KitchenHood> selectAll();
}
