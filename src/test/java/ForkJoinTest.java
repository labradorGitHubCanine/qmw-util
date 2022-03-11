import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {


    // 数字求和
    static class SumUp extends RecursiveTask<Integer> {

        private static final Integer MAX = 100;
        private final Integer start;
        private final Integer end;

        public SumUp(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start < MAX) {
                int total = 0;
                for (int i = start; i <= end; i++)
                    total += i;
                return total;
            } else {
                SumUp sumUp1 = new SumUp(start, (start + end) / 2);
                sumUp1.fork();
                SumUp sumUp2 = new SumUp((start + end) / 2 + 1, end);
                sumUp2.fork();
                return sumUp1.join() + sumUp2.join();
            }
        }
    }

    static class FilePrinter extends RecursiveAction {

        private final File directory;

        public FilePrinter(File directory) {
            this.directory = directory;
        }

        @Override
        protected void compute() {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    FilePrinter printer = new FilePrinter(file);
//                    invokeAll(printer);
                    printer.fork();
                } else {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }

    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long t1 = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
//        ForkJoinTask<Integer> taskFuture = pool.submit(new SumUp(1, 1000));
//        System.out.println(taskFuture.get());

        FilePrinter filePrinter = new FilePrinter(new File("E://"));
        pool.execute(filePrinter);
        filePrinter.join();
        pool.shutdown();
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1 + "ms");
//        pool.awaitTermination(10, TimeUnit.SECONDS);

    }

}
