package com.ssafy.model.entity;

public class MemberException extends RuntimeException {
    public static String NOT_EXIST = "회원이 존재하지 않습니다.";
    public MemberException() {}
    public MemberException(String msg) {super(msg);}
}