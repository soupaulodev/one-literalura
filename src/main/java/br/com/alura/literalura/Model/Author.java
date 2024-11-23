package br.com.alura.literalura.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="authors")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private int yearBirth;
    private int yearDeath;

    @OneToMany(mappedBy = "author" , cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
