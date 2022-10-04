package com.example.mds.controller;

import com.example.mds.annotation.Controller;
import com.example.mds.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/join")
    public void join() {
        System.out.println("UserController-join");
    }

    @RequestMapping("/login")
    public void login() {
        System.out.println("UserController-login");
    }

    @RequestMapping("/user")
    public void user() {
        System.out.println("UserController-user");
    }

}
