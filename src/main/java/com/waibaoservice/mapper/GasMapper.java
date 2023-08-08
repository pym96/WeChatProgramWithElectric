package com.waibaoservice.mapper;


import com.waibaoservice.pojo.GasStove;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GasMapper {
    int addTimer(@Param("device_id") String deviceId,
                 @Param("openid") String openId,
                 @Param("end_time") String endTime);

    int updateTimer(@Param("device_id") String deviceId,
                    @Param("end_time") String endTime);

    int removeTimer(@Param("device_id") String deviceId);

    GasStove selectGasStoveById(@Param("device_id") String deviceId);
    GasStove selectEndTimeById(@Param("device_id") String deviceId);
    List<GasStove> selectAll();
}
