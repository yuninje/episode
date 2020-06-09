package com.ssafy.model.entity;

public class RelationException extends RuntimeException {
    public static String NOT_EXIST = "인물 관계가 존재하지 않습니다.";
    public RelationException() {}
    public RelationException(String msg) {super(msg);}
}
