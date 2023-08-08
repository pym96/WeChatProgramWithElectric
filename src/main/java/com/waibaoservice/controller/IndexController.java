package com.waibaoservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DJS
 * Date create 19:37 2023/4/15
 * Modified By DJS
 **/

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        System.out.println("访问index");
        return "index.html";
    }

}
