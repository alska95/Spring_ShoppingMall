package com.example.demo.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("B")//값넣기
public class Book extends Item {

    private String author;
    private String isbn;
}
