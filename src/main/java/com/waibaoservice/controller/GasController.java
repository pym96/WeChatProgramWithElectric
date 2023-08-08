package com.waibaoservice.controller;

import com.waibaoservice.pojo.GasStove;
import com.waibaoservice.service.GasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DJS
 * Date create 3:08 2023/3/16
 * Modified By DJS
 **/

@RestController
@RequestMapping("/gas")
public class GasController {

    @Autowired
    GasService gasService;

    @PostMapping("/setTimer")
    @ResponseBody
    public boolean setTimer(@RequestBody GasStove gasStove) {
        System.out.println("gas setTimer");
        System.out.println(gasStove);
        int ret = gasService.addTimer(gasStove);
        return ret == 1;
    }

    @PostMapping("/shutdown")
    @ResponseBody
    public boolean shutdown(@RequestBody GasStove gasStove) {
        System.out.println("shutdown");
        System.out.println(gasStove);
        int ret = gasService.shutdown(gasStove);
        return ret == 1;
    }

    @PostMapping("/getEndTime")
    @ResponseBody
    public String getEndTime(@RequestBody GasStove gasStove) {
        System.out.println("getEndTime");
        System.out.println(gasStove);
        return gasService.getEndTime(gasStove);
    }
}
