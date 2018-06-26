package com.igreedy.dao.cache;

import com.igreedy.dao.SeckillDao;
import com.igreedy.entity.Seckill;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/25 20:03
 * @Description: 测试redis以及序列化
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id = 1001;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() {
        // get and put
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill != null) {
            seckill = seckillDao.queryById(id);
            if (seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println("++++");
                System.out.println(result);
                System.out.println("++++");
                seckill = redisDao.getSeckill(id);
                System.out.println("----");
                System.out.println(seckill);
                System.out.println("----");
            }
        }
    }
}