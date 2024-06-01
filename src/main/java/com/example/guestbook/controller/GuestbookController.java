package com.example.guestbook.controller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    /*//목록 or 메인 페이지
    @GetMapping({"/", "list"})
    public String list() {
        System.out.println("list................");

        return "/guestbook/list";
    }*/

    //화면 목록 조회
    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    //방명록 목록(화면에서 요청하는 부분)
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        System.out.println("list.........." + pageRequestDTO);

        //model 에 들어가는 것은 PageResultDto 이다.
        model.addAttribute("result", guestbookService.getList(pageRequestDTO));
    }

    //방명록 등록(화면에 보이는 것)
    @GetMapping("/register")
    public void register() {
        System.out.println("register get...");
    }

    //방명록 등록 처리
    @PostMapping("/register")
    public String registerPost(GuestbookDTO guestbookDTO, RedirectAttributes redirectAttributes) {
        System.out.println("dto..." + guestbookDTO);

        //새로 추가된 엔티티 번호
        Long gno = guestbookService.register(guestbookDTO);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}
