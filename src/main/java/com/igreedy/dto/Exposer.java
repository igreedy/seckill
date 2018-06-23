package com.igreedy.dto;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/22 14:29
 * @Description: 暴露秒杀地址DTO
 */
public class Exposer {

    //是否开启秒杀
    private boolean exposed;

    //一种加密措施
    private String md5;

    private long seckkillId;

    //系统当前时间(毫秒)
    private long now;

    //开启时间
    private long start;

    //结束时间
    private long end;

    public Exposer(boolean exposed, String md5, long seckkillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckkillId = seckkillId;
    }

    public Exposer(boolean exposed, long seckkillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckkillId = seckkillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckkillId) {
        this.exposed = exposed;
        this.seckkillId = seckkillId;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckkillId=" + seckkillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckkillId() {
        return seckkillId;
    }

    public void setSeckkillId(long seckkillId) {
        this.seckkillId = seckkillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
