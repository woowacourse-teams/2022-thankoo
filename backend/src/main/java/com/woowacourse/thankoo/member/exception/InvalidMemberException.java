package com.woowacourse.thankoo.member.exception;

public class InvalidMemberException extends RuntimeException {

    public InvalidMemberException() {
        super("존재하지 않는 회원입니다.");
    }
}
