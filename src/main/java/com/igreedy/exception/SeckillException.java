package com.igreedy.exception;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/22 14:51
 * @Description: 秒杀相关业务异常
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
