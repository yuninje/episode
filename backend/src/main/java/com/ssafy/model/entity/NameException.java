package com.ssafy.model.entity;


public class NameException extends RuntimeException {
    public static String NOT_EXIST = "이름이 존재하지 않습니다.";
    public NameException() {}
    public NameException(String msg) {super(msg);}
}