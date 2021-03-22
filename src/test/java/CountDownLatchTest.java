import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        latch.await(5, TimeUnit.SECONDS); // 最多等待5s
//        System.out.println(123);

        // 模拟并发请求
        CountDownLatch latch2 = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    latch2.await();

                    aaa();

//                    System.out.println(LocalDateTime.now());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        latch2.countDown();

        Thread.sleep(10000);
        System.out.println(a.intValue());
    }

    private static final AtomicInteger a = new AtomicInteger(0);

    public static void aaa() {
        System.out.println(a.incrementAndGet());
    }

}
