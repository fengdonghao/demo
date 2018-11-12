package com.vissun.BackEnd_vissun.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in9:19 2018/3/17
 */
@Controller
public class MyWorkController {

    @GetMapping("/task")
    public String task(){

        return "MyWork/main";
    }

    @GetMapping("/createWork")
    public String createWork(){
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TimeString = time.format(new java.util.Date());
        System.out.println(TimeString);
        return "MyWork/createWork";
    }

    @PostMapping("/createWork")
    public String createWork1(){


        return "MyWork/createWork";
    }

}
