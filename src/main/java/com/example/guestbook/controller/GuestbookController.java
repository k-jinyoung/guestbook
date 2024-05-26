package com.example.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

    //목록 or 메인 페이지
    @GetMapping({"/", "list"})
    public String list() {
        System.out.println("list................");

        return "/guestbook/list";
    }
}
