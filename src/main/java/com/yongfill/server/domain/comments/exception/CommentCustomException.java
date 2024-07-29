package com.yongfill.server.domain.comments.exception;

import com.yongfill.server.global.common.response.error.ErrorCode;
import lombok.Getter;

@Getter
public class CommentCustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Throwable cause;

    public CommentCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.cause = null;
    }

    public CommentCustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.cause = cause;
    }

}