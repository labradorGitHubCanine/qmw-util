package com.qmw.util;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

public class ForkJoinUtil {

    static class MyRecursiveAction<T> extends RecursiveAction {

        private final int maxSize;
        private final List<T> list;
        private final Consumer<List<T>> consumer;

        public MyRecursiveAction(int maxSize, List<T> list, Consumer<List<T>> consumer) {
            this.maxSize = maxSize;
            this.list = list;
            this.consumer = consumer;
        }

        @Override
        protected void compute() {
            if (list.size() > maxSize) {
                MyRecursiveAction<T> left = new MyRecursiveAction<>(maxSize, list.subList(0, list.size() / 2), consumer);
                MyRecursiveAction<T> right = new MyRecursiveAction<>(maxSize, list.subList(list.size() / 2, list.size()), consumer);
                invokeAll(left, right); // 同步执行
            } else {
                consumer.accept(list);
            }
        }
    }

    public static <T> void doAction(int maxSize, List<T> list, Consumer<List<T>> consumer) {
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MyRecursiveAction<>(maxSize, list, consumer)); // 同步执行
        pool.shutdown();
    }

}
