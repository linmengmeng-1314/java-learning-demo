package com.lin.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. 可重入锁 同一个线程 可以多次 获取锁
 * @author linmengmeng
 * @since 2025/4/17 13:46
 */

public class ReentrantLockDemo {

    public static int num = 0;

    public static void main(String[] args) {
        CountTask countTask = new CountTask();
        Thread thread1 = new Thread(countTask, "Thread-1");
        Thread thread2 = new Thread(countTask, "Thread-2");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num = " + num);
    }

    static class CountTask implements Runnable {

        public ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " start!");
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                try {
                    num++;
                } finally {
                    lock.unlock();
                }
            }
            System.out.println(Thread.currentThread().getName() + " end!");
        }
    }

}
