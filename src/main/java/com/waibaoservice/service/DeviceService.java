package com.waibaoservice.service;

import com.waibaoservice.pojo.Device;

import java.util.List;

public interface DeviceService {
    boolean addDevice(Device device);
    List<String> getDeviceList(String openid);
    boolean addDeviceToMap(String device_id);
}
