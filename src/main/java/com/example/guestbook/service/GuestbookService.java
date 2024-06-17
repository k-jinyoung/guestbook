package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;

public interface GuestbookService {
    //등록
    Long register(GuestbookDTO guestbookDTO);
    //목록
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO);
    //조회
    GuestbookDTO read(Long gno);
    //수정
    void modify(GuestbookDTO guestbookDTO);
    //삭제
    void remove(Long gno);

    //dto를 Entity로 변환
    default Guestbook dtoToEntity(GuestbookDTO guestbookDTO) {
        Guestbook entity = Guestbook.builder()
                .gno(guestbookDTO.getGno())
                .title(guestbookDTO.getTitle())
                .content(guestbookDTO.getContent())
                .writer(guestbookDTO.getWriter())
                .build();
        return entity;
    }

    //Entity를 dto로 변환
    default GuestbookDTO entityToDto(Guestbook entity) {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return guestbookDTO;
    }


}
