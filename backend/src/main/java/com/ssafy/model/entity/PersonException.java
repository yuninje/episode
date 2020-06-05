package com.ssafy.model.entity;

public class PersonException extends RuntimeException {
    public static String NOT_EXIST = "인물이 존재하지 않습니다.";
    public PersonException() {}
    public PersonException(String msg) {super(msg);}
}