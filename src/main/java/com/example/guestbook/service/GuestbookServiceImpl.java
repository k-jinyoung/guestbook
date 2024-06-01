package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository guestbookRepository;

    //dtoToEntity
    //방명록 등록
    @Override
    public Long register(GuestbookDTO guestbookDTO) {

        System.out.println("DTO-------------------------");
        System.out.println(guestbookDTO);

        Guestbook entity = dtoToEntity(guestbookDTO);

        System.out.println(entity);

        guestbookRepository.save(entity);

        return entity.getGno();
    }

    //방명록 목록
    //entityToDto
    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
        //페이징 설정
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        //엔티티 조회
        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        //엔티티를 DTO로 변환하는 함수 정의
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        //변환된 결과를 PageResultDTO로 반환
        return new PageResultDTO<>(result, fn);
    }

    //방명록 조회
    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> readResult = guestbookRepository.findById(gno);
        return readResult.isPresent()? entityToDto(readResult.get()) : null;
    }
}
