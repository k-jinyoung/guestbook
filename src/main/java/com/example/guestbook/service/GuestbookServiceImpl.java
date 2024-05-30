package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO guestbookDTO) {

        System.out.println("DTO-------------------------");
        System.out.println(guestbookDTO);

        Guestbook entity = dtoToEntity(guestbookDTO);

        System.out.println(entity);

        guestbookRepository.save(entity);

        return entity.getGno();
    }
}
