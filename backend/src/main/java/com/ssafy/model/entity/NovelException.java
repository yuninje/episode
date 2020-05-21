package com.ssafy.model.entity;

public class NovelException extends RuntimeException {
    public static String NOT_EXIST = "소설이 존재하지 않습니다.";
    public NovelException() {}
    public NovelException(String msg) {super(msg);}

}