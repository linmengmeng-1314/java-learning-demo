package com.lin.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3. 中断响应 lockInterruptibly() 可以很好的解决死锁的问题
 * 如果使用 sysnchronized 锁，要么获得锁，要么保持等待，
 * 如果使用 ReentrantLock 锁，则提供了另一种方式，那就是线程可以被中断，也就是在等待的过程中，程序可以根据需要取消对锁的请求
 * @author linmengmeng
 * @since 2025/4/17 14:46
 */

public class LockInterruptiblyDemo {

    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // 可中断锁  入参相反 可以导致死锁
        Thread thread1 = new Thread(new ReentrantLockThread(lock1, lock2), "Thread-1");
        Thread thread2 = new Thread(new ReentrantLockThread(lock2, lock1), "Thread-2");
        thread1.start();
        thread2.start();
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("main thread begin sleep first 1s");
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("主线程线程，在 " + thread1.getName() + " 上开始执行 Interrupt()");
        // 线程1中断锁
        thread1.interrupt();
    }

    /**
     * ReentrantLock 的 lockInterruptibly() 实现死锁
     */
    static class ReentrantLockThread implements Runnable {

        private ReentrantLock lock1, lock2;

        public ReentrantLockThread(ReentrantLock lock1, ReentrantLock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            try {
                // 获取 lock1 的可中断锁
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + " 加锁成功 1-2 !");
                // 等待 lock1 lock2 分别被两个线程获取 产生死锁现象
                TimeUnit.MILLISECONDS.sleep(100);

                // 获取 lock2 的可中断锁
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + " 加锁成功 2-2 !");

            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 发生异常!");
                e.printStackTrace();
            } finally {
                // 如果持有锁 则进行解锁
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
                System.out.println(Thread.currentThread().getName() + " is unlock success!");
            }
        }
    }

}
