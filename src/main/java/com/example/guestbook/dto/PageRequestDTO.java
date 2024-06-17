package com.example.guestbook.dto;
//Controller 에서 Service 로 전달해주는 DTO

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class PageRequestDTO {

    private int page;           //요청하는 페이지 번호
    private int size;           //한 페이지에 표시될 항목 수
    private String type;
    private String keyword;

    //기본 생성자(기본적으로 1페이지를 요청, 한 페이지당 10개의 항목을 보여주도록 초기화 설정)
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }
}
