package com.example.mds.controller;

import com.example.mds.annotation.Controller;
import com.example.mds.annotation.RequestMapping;
import com.example.mds.annotation.ResponseBody;

/*
현재 코드는 에러가 발생.
UserController, TeamController 모두 같은 요청 url이 있기 때문인데,
이를 위해 Controller에도 요청 url을 추가해줘야 합니다.
 */
@Controller
@RequestMapping("/team")
public class TeamController {

    @RequestMapping("/join")
    @ResponseBody
    public String join() {
        System.out.println("TeamController-join");
        return "team-join";
    }

    @RequestMapping("/login")
    public String login() {
        System.out.println("TeamController-login");
        return "team-login";
    }

    @RequestMapping("/user")
    public String user() {
        System.out.println("TeamController-user");
        return "team-user";
    }
}
