package com.example.study.demo.controler;

import com.example.study.demo.bean.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecordController {

    static Logger logger = Logger.getLogger(RecordController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/record")
    public String recordIndex (Model model) {
        model.addAttribute("sexList", gainSexList());
        return "/record";
    }

    @GetMapping(value = "/recordResult")
    public String recordResult (UserInfo userInfo, Model model) {

        model.addAttribute("name", userInfo.name);
        model.addAttribute("age", userInfo.age);
        model.addAttribute("sex",userInfo.sex);

        String sql = "SELECT * FROM gcnoVersion";
        jdbcTemplate.execute(sql);

        System.out.println("跳转recordResult页面");
        return "/recordResult";
    }

    public List<String> gainSexList() {
        List<String> sexList = new ArrayList<String>();
        sexList.add("男");
        sexList.add("女");
        return sexList;
    }
}
