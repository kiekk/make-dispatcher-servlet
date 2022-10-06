package com.example.mds.controller;

import com.example.mds.annotation.Controller;
import com.example.mds.annotation.RequestMapping;
import com.example.mds.annotation.ResponseBody;

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
    public String login() {
        System.out.println("TeamController-login");
        return "/team/login.jsp";
    }

    @RequestMapping("/user")
    public String user() {
        System.out.println("TeamController-user");
        return "/team/user.jsp";
    }
}
