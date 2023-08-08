package com.waibaoservice.controller;

import com.waibaoservice.pojo.KitchenHood;
import com.waibaoservice.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DJS
 * Date create 3:08 2023/3/16
 * Modified By DJS
 **/

@RestController
@RequestMapping("/kitchen")
public class KitchenController {

    @Autowired
    KitchenService kitchenService;

    @PostMapping("/setTimer")
    @ResponseBody
    public boolean setTimer(@RequestBody KitchenHood kitchenHood) {
        System.out.println("kitchen serTimer");
        System.out.println(kitchenHood);
        int ret = kitchenService.addTimer(kitchenHood);
        return ret == 1;
    }

    @PostMapping("/shutdown")
    @ResponseBody
    public boolean shutdown(@RequestBody KitchenHood kitchenHood) {
        System.out.println("shutdown");
        System.out.println(kitchenHood);
        int ret = kitchenService.shutdown(kitchenHood);
        return ret == 1;
    }

    @PostMapping("/getEndTime")
    @ResponseBody
    public String getEndTime(@RequestBody KitchenHood kitchenHood) {
        System.out.println("getEndTime");
        System.out.println(kitchenHood);
        return kitchenService.getEndTime(kitchenHood);
    }
}
