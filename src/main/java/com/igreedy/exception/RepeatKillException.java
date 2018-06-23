package com.igreedy.exception;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/22 14:47
 * @Description: 重复秒杀异常(运行期异常，spring会自动事务回滚，但如果是编译期异常，就不会帮我们做回滚)
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
