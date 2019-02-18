package com.example.study.demo.controler;


import com.example.study.demo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;

@Slf4j
@Controller
public class DemoControler {

    @GetMapping(value = "/")
    public String index(Model model){


        log.debug("打印日志");

        model.addAttribute("headerTitle","来自DemoControler 动态配置的  Demo 数据，在网页显示");

        return "/login";
    }


    @GetMapping(value = "/api")
    public @ResponseBody String api(){
        log.debug("这是 json 请求的 返回结果数据，不走html");
        return "api 数据";
    }

    @PostMapping(value = "/login")
    public @ResponseBody User login(User user){
        return user;
    }
}
