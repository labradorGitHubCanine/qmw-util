import java.io.File;
import java.util.concurrent.*;

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
        private final String fileType;

        public FilePrinter(File directory, String fileType) {
            this.directory = directory;
            this.fileType = fileType;
        }

        @Override
        protected void compute() {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {

                    FilePrinter printer = new FilePrinter(file, fileType);
                    printer.fork();

                } else {
                    if (file.getName().endsWith(fileType))
                        System.out.println(file.getAbsolutePath());
                }
            }
        }

    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ForkJoinPool pool = new ForkJoinPool();
////        ForkJoinTask<Integer> taskFuture = pool.submit(new SumUp(1, 1000));
////        System.out.println(taskFuture.get());
//
//        pool.submit(new FilePrinter(new File("E://"), "xlsx"));
//        pool.awaitTermination(10, TimeUnit.SECONDS);
//        pool.shutdown();

        System.out.println(test1());
        System.out.println(test2());
    }

    private static int test1() {

        int i = 1;
        try {
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            i = 0;
        }
        return i;
    }

    private static int test2() {
        int i = 1;
        try {
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            i = 0;
            return i;
        }
    }

}
