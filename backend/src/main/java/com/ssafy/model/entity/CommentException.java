package com.ssafy.model.entity;

public class CommentException extends RuntimeException {
    public static String NOT_EXIST = "댓글이 존재하지 않습니다.";
    public CommentException() {}
    public CommentException(String msg) {super(msg);}
}
