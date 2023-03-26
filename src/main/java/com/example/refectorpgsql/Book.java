package com.example.refectorpgsql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;

@Data
@Table(name = "book")
public class Book implements Persistable {

    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("author")
    private String author;

    public Book() {
    }

    public Book(String title, String author) {

        this.title = title;
        this.author = author;
    }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}