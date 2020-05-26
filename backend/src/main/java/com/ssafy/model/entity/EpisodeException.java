package com.ssafy.model.entity;

public class EpisodeException extends RuntimeException {
    public static String NOT_EXIST = "에피소드가 존재하지 않습니다.";
    public EpisodeException() {}
    public EpisodeException(String msg) {super(msg);}
}
