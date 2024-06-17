package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.example.guestbook.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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

        //검색 조건 처리
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);

        //엔티티 조회
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

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

    //방명록 수정
    @Override
    public void modify(GuestbookDTO guestbookDTO){
        //업데이트 하는 항목은 제목과 내용
        Optional<Guestbook> modifyResult = guestbookRepository.findById(guestbookDTO.getGno());
        if(modifyResult.isPresent()){
            Guestbook guestbook = modifyResult.get();
            guestbook.changeTitle(guestbookDTO.getTitle());
            guestbook.changeContent(guestbookDTO.getContent());

            guestbookRepository.save(guestbook);
        }
    }

    //방명록 삭제
    public void remove(Long gno){
        guestbookRepository.deleteById(gno);
    }

    //검색 처리
    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO){
        //Querydsl 처리
        String type = pageRequestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = pageRequestDTO.getKeyword();

        //gno>0 조건만 생성한다.
        BooleanExpression booleanExpression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(booleanExpression);

        if (type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
