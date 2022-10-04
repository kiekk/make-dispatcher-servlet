package com.example.mds.controller;

import com.example.mds.annotation.Controller;
import com.example.mds.annotation.RequestMapping;

/*
현재 코드는 에러가 발생.
UserController, TeamController 모두 같은 요청 url이 있기 때문인데,
이를 위해 Controller에도 요청 url을 추가해줘야 합니다.
 */
@Controller
@RequestMapping("/team")
public class TeamController {

    @RequestMapping("/join")
    public void join() {
        System.out.println("TeamController-join");
    }

    @RequestMapping("/login")
    public void login() {
        System.out.println("TeamController-login");
    }

    @RequestMapping("/user")
    public void user() {
        System.out.println("TeamController-user");
    }
}
