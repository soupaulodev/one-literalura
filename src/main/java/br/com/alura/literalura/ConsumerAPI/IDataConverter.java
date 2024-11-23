package br.com.alura.literalura.ConsumerAPI;

public interface IDataConverter {
    <T> T  getData(String json, Class<T> classT);
}
