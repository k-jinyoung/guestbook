package com.example.guestbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public class BaseEntity {

    //등록시간
    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    //수정시간
    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
