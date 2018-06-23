package com.igreedy.service.impl;

import com.igreedy.dao.SeckillDao;
import com.igreedy.dao.SuccessKilledDao;
import com.igreedy.dto.Exposer;
import com.igreedy.dto.SeckillExecution;
import com.igreedy.entity.Seckill;
import com.igreedy.entity.SuccessKilled;
import com.igreedy.enums.SeckillStatEnum;
import com.igreedy.exception.RepeatKillException;
import com.igreedy.exception.SeckillCloseException;
import com.igreedy.exception.SeckillException;
import com.igreedy.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/22 14:55
 * @Description:
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入Service依赖
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    // md5验证字符串，用来混淆，不让别人能猜到
    private final String slat = "sdfal./,';'*&$#+><)(())";

    public List<Seckill> getSeckList() {

        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {

        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(),
                    startTime.getTime(), endTime.getTime());
        }
        // 转化特定字符串的过程，它最大特点就是不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     * 1、开发团队达成一致约定，明确标注事务方法的编程风格。就是统一了意见，这个方法是事务处理的方法。
     * 2、保证事务方法的执行时间尽可能短，不要穿插其他网络操作(redis/http请求)。
     *    如果还是需要网络操作，那就剥离到事务方法外部。
     * 3、知道这个是事务，是一定要优化的点。
     * 4、不是所有方法都需要事务，如只有一条修改操作，只读操作不需要事务控制。就是不需要并发修改控制。
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        // 执行秒杀逻辑：减库存，记录购买行为
        Date nowTime = new Date();
        int updateCount = seckillDao.reduceNumber(seckillId, nowTime);

        try {
            if (updateCount <= 0) {
                //没有更新到记录,秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                // 记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //唯一：seckillId, userPhone
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (SeckillException e) {
            logger.error(e.getMessage(), e);
            //所有编译期异常都转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
