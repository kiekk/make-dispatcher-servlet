package com.example.mds.controller;

import com.example.mds.annotation.Controller;
import com.example.mds.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/join")
    public String join() {
        System.out.println("UserController-join");
        return "/user/join";
    }

    @RequestMapping("/login")
    public String login() {
        System.out.println("UserController-login");
        return "/user/login";
    }

    @RequestMapping("/user")
    public String user() {
        System.out.println("UserController-user");
        return "/user/user";
    }

    @RequestMapping("/team")
    public String team() {
        System.out.println("UserController-team");
        return "redirect:/team/user";
    }

}
