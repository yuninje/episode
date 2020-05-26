package com.ssafy.model.entity;

public class CharacterException extends RuntimeException {
    public static String NOT_EXIST = "캐릭터가 존재하지 않습니다.";
    public CharacterException() {}
    public CharacterException(String msg) {super(msg);}
}
