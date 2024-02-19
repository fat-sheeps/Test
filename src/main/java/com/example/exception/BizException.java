package com.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ErrorCode errorCode;

    public BizException(final String message) {
        super(message);
        this.errorCode = BizCode.DEFAULT;
    }

    public BizException(final ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public BizException(final ErrorCode errorCode, final String message) {
        super(errorCode.getErrorMessage() + message);
        this.errorCode = errorCode;
    }
}
