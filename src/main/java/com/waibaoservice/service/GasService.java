package com.waibaoservice.service;

import com.waibaoservice.pojo.GasStove;
import org.apache.ibatis.annotations.Param;

public interface GasService {
    int addTimer(GasStove gasStove);
    int shutdown(GasStove gasStove);
    String getEndTime(GasStove gasStove);
}
