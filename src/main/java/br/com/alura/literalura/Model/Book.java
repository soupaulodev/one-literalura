package br.com.alura.literalura.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String title;

    private int downloads;
    private String language;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @Override
    public String toString() {
        return "--------------------- LIVRO ---------------------\n" +
                "Title              : " + title +  "\n" +
                "Author               : " + author +  "\n"  +
                "Language            : " + language +  "\n" +
                "Number of downloads : " + downloads +"\n"+
                "--------------------------------------------------\n" ;
    }
}
