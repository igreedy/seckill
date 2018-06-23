package com.igreedy.service;

import com.igreedy.dto.Exposer;
import com.igreedy.dto.SeckillExecution;
import com.igreedy.entity.Seckill;
import com.igreedy.exception.RepeatKillException;
import com.igreedy.exception.SeckillCloseException;
import com.igreedy.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/22 17:36
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
/**
 * spring-service.xml 依赖于 dao.xml的数据源dataSource,所以加载两个
 */
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckList() {
        List<Seckill> list = seckillService.getSeckList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() {
        long id = 1004;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() {
        long id = 1004;
        long phone = 13878781515L;
        String md5 = "4ddc010740b2278204e90480fc0515c2";
        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", execution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage(), e);
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage(), e);
        } catch (SeckillException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void exportSeckillLogic() {
        long id = 1004;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            long phone = 13878781521L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", execution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage(), e);
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage(), e);
            } catch (SeckillException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
