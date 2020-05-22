package com.ssafy.model.entity;
public class GenreException extends RuntimeException {
    public static String NOT_EXIST = "장르가 존재하지 않습니다.";
    public GenreException() {}
    public GenreException(String msg) {super(msg);}
}

