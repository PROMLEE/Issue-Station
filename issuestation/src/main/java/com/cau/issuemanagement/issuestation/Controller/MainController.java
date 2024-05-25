package com.cau.issuemanagement.issuestation.Controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//요청이 들어올 수 있는 입구 역할
@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping("")
    @CrossOrigin(originPatterns = "http://localhost:9231") //React에서 포트번호 9231 쓰면안됨
    public String hello() {
        return "Connection Success";
    }
}
