package com.gsralex.gflow.common;

/**
 * @author gsralex
 * @date 2020/3/7
 */
public class CommonException extends RuntimeException {

    public CommonException() {
        super();
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
