package com.waibaoservice.service.impl;

import com.waibaoservice.mapper.DeviceMapper;
import com.waibaoservice.pojo.Device;
import com.waibaoservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author DJS
 * Date create 19:06 2023/3/14
 * Modified By DJS
 **/

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper mapper;

    @Override
    public boolean addDevice(Device device) {
        System.out.println("进入addDevice服务");
        System.out.println(device);
        final Device d = mapper.getDeviceInMap(device.getDevice_id());
        if (d == null) {
            System.out.println("小程序添加设备失败");
            return false;
        }
        int ret = mapper.addDevice(device.getDevice_id(), device.getOpenid());
        System.out.println("小程序添加设备成功");
        System.out.println("ret : " + ret);
        return ret == 1;
    }

    @Override
    public List<String> getDeviceList(String openid) {
        System.out.println("进出getDeviceList服务");
        final List<Device> deviceList = mapper.getDeviceList(openid);
        List<String> list = new LinkedList<>();
        for (Device device : deviceList) {
            list.add(device.getDevice_id());
        }
        return list;
    }

    @Override
    public boolean addDeviceToMap(String device_id) {
        System.out.println("进入addDeviceToMap服务");
        final Device device = mapper.getDeviceInMap(device_id);
        if (device == null) {
            mapper.addDeviceToMap(device_id);
            return true;
        }
        else {
            return false;
        }
    }
}
