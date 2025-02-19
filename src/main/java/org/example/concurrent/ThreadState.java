package org.example.concurrent;

import java.util.concurrent.CountDownLatch;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        /**
         * Ожидаем завершения 2 потоков
         */
        CountDownLatch latch = new CountDownLatch(2);

        Thread first = new Thread(() -> {
            System.out.println("First thread finished");
            /**
             * Уменьшаем счетчик
             */
            latch.countDown();
        });

        Thread second = new Thread(() -> {
            System.out.println("Second thread finished");
            /**
             * Уменьшаем счетчик
             */
            latch.countDown();
        });

        first.start();
        second.start();
        /**
         * Ждем, пока счетчик не станет 0
         */
        latch.await();
        System.out.println("All threads finished!");
    }
}
