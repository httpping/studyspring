package com.example.study.demo.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class RecordController {

    @GetMapping("/record")
    public String recordIndex (Model model) {
        model.addAttribute("host", "http://blog.didispace.com");
        return "/record";
    }

    @GetMapping(value = "/commit")
    public String commit (Model model) {
        return "/success";
    }
}
