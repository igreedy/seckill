package com.igreedy.exception;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/22 14:50
 * @Description: 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
