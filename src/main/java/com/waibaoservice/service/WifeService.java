package com.waibaoservice.service;

import com.waibaoservice.pojo.Wife;

public interface WifeService {
    boolean getWifeStatus(String deviceId);
    int setWife(Wife wife);
    int modifyWife(Wife wife);
}
