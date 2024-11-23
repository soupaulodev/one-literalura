package br.com.alura.literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook (

    @JsonAlias("title") String title,

    @JsonAlias("authors") List<DataAuthor> authors,

    @JsonAlias("languages") List<String> languages,

    @JsonAlias("download_count") int downloads

) {
    @Override
    public String toString() {
        String authorsName = authors.stream()
                .map(DataAuthor::name)
                .collect(Collectors.joining(", "));
        return  "--------------------- LIVRO ---------------------\n" +
                "Title              : " + title + "\n" +
                "Author               : " +  authorsName +"\n" +
                "Languages              : " +String.join(", ", languages) +"\n" +
                "Number of downloads : " + downloads +"\n" +
                "--------------------------------------------------";

    }

    public List<String> getLanguagesByName() {
        Map<String, String> languagesMap = Map.of(
            "pt", "Portuguese",
            "en", "English",
            "es", "Spanish"
        );

        return languages.stream()
                .map(languagesMap::get)
                .toList();
    }
}