package com.ssafy.model.entity;

public class NovelSettingException  extends RuntimeException {
    public static String NOT_EXIST = "소설 설정이 존재하지 않습니다.";
    public NovelSettingException() {}
    public NovelSettingException(String msg) {super(msg);}
}