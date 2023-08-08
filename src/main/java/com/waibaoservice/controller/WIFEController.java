package com.waibaoservice.controller;

import com.waibaoservice.pojo.Wife;
import com.waibaoservice.service.WifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DJS
 * Date create 0:44 2023/3/16
 * Modified By DJS
 **/

@RestController
@RequestMapping("/wife")
public class WIFEController {

    @Autowired
    WifeService wifeService;

    // 获取设备wife状态, 是否配置过密码
    @PostMapping("/getWifeStatus")
    @ResponseBody
    public boolean getWifeStatus(@RequestBody Wife wife) {
        System.out.println("getWifeStatus");
        System.out.println(wife);
        return wifeService.getWifeStatus(wife.getDeviceId());
    }

    @PostMapping("/setWife")
    @ResponseBody
    public boolean setWife(@RequestBody Wife wife) {
        System.out.println("setWife");
        System.out.println(wife);
        int ret = wifeService.setWife(wife);
        return ret == 1;
    }

    @PostMapping("/modifyWife")
    @ResponseBody
    public boolean modifyWife(@RequestBody Wife wife) {
        System.out.println("modifyWife");
        System.out.println(wife);
        int ret = wifeService.modifyWife(wife);
        return ret == 1;
    }

}
