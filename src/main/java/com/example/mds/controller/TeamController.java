package com.example.mds.controller;

import com.example.mds.annotation.Controller;
import com.example.mds.annotation.RequestMapping;
import com.example.mds.annotation.ResponseBody;
import com.example.mds.entity.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/team")
public class TeamController {

    @RequestMapping("/join")
    @ResponseBody
    public String join() {
        System.out.println("TeamController-join");
        return "RestController - /team/join";
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        System.out.println("TeamController-login");
        return new ModelAndView("/team/login", Map.of("id", "1111", "name", "team"));
    }

    @RequestMapping("/user")
    public String user() {
        System.out.println("TeamController-user");
        return "/team/user";
    }
}
