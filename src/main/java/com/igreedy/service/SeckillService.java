package com.igreedy.service;


import com.igreedy.dto.Exposer;
import com.igreedy.dto.SeckillExecution;
import com.igreedy.entity.Seckill;
import com.igreedy.exception.RepeatKillException;
import com.igreedy.exception.SeckillCloseException;
import com.igreedy.exception.SeckillException;

import java.util.List;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/22 14:18
 * @Description: 业务接口: 站在使用者的角度设计接口
 * 主要体现在三个方面：方法定义粒度(定义要明确)，参数(越简练直接越好)，返回类型(return 类型/异常)(一定要友好)
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启是输出秒杀接口地址,否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, RepeatKillException, SeckillCloseException;
}
