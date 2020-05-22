package com.ssafy.model.entity;

public class HashTagException extends RuntimeException {
    public static String NOT_EXIST = "해시태그가 존재하지 않습니다.";
    public HashTagException() {}
    public HashTagException(String msg) {super(msg);}
}