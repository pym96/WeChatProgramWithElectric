package com.waibaoservice.controller;

import com.waibaoservice.pojo.Device;
import com.waibaoservice.service.DeviceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author DJS
 * Date create 19:03 2023/3/14
 * Modified By DJS
 **/

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @PostMapping("/getDeviceList")
    @ResponseBody
    public List<String> getDeviceList(@RequestBody Device device) {
        System.out.println("进入getDeviceList, device : " + device);
        return deviceService.getDeviceList(device.getOpenid());
    }

    @GetMapping("/addDeviceToMap")
    @ResponseBody
    public boolean addDeviceToMap(@RequestParam("device_id") String deviceId) {
        System.out.println("进入addDeviceToMap deviceId : " + deviceId);
        return deviceService.addDeviceToMap(deviceId);
    }

    @PostMapping("/addDevice")
    @ResponseBody
    public boolean addDevice(@RequestBody Device device) {
        System.out.println("进入addDevice device : " + device);
        return deviceService.addDevice(device);
    }
}
