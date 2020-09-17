package com.qmw.push;

/**
 * 友盟推送相关工具类
 *
 * @author qmw
 */
public class UmengPush {

    private int a = 0;

    public synchronized void plus() throws InterruptedException {
        Thread.sleep(10);
        a++;
    }

    public static void main(String[] args) throws InterruptedException {
        UmengPush u = new UmengPush();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    u.plus();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(10000);
        System.out.println(u.a);
    }

}
