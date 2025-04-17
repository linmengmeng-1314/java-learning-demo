package com.lin.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 2. 可重入锁 同一个线程 可以多次 获取锁
 * 如果同一个线程多次获取锁，那么释放的时候 也必须释放相同的次数
 *  如果释放的次数少，相当于该线程仍持有锁
 *  如果释放的次数多，则会抛出 java.lang.IllegalMonitorStateException 异常
 * @author linmengmeng
 * @since 2025/4/17 13:46
 */

public class ReentrantLockDemo1 {

    public static int num = 0;

    public static void main(String[] args) {
        Task countTask = new Task();
        Thread thread1 = new Thread(countTask, "Thread-1");
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Task implements Runnable {

        public ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " is lock!");

            lock.lock();
            System.out.println(Thread.currentThread().getName() + " is lock!");
            try {
                System.out.println(Thread.currentThread().getName() + " is coming!");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " is unlock!");
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " is unlock!");
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " is unlock!");
            }
        }
    }

}
