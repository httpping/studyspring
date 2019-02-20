package com.example.study.demo.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.example.study.demo.bean.UserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecordController {

    @GetMapping(value = "/record")
    public String recordIndex (Model model) {
        model.addAttribute("host", "http://blog.didispace.com");
        return "/record";
    }

    @PostMapping(value = "/commit")
    public String commit (UserInfo userInfo, Model model) {
        model.addAttribute("name", userInfo.name);
        model.addAttribute("age", userInfo.age);
        model.addAttribute("sex",userInfo.sex);
        return "/success";
    }
}
