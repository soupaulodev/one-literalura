package br.com.alura.literalura.ConsumerAPI;

import java.util.List;

import br.com.alura.literalura.Model.DataBook;
import lombok.Data;

@Data
public class ResponseAPI {
    private int count;
    private String next;
    private String previous;
    private List<DataBook> results;

    @Override
    public String toString() {
        return "ResponseAPI{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}
