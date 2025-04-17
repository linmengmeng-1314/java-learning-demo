package com.lin.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 4. 锁等待 tryLock(long time, TimeUnit unit)
 * 除了等待外部通知之外，避免死锁 还可以使用限时等待，如果等待时间结束还没有获取到锁，则直接返回 false。
 * 如果不加参数，则立即返回获取锁的结果 true false，这种模式不会引起线程等待，也不会引起死锁
 * @author linmengmeng
 * @since 2025/4/17 15:09
 */

public class TryLockDemo {

    public static void main(String[] args) {
        Task task = new Task();
        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();
    }

    static class Task implements Runnable {

        public static ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            try {
                // 如果 1s 内没有获取到锁 则返回 false
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println(System.currentTimeMillis() + " " + threadName + " is lock success!");
                    Thread.sleep(3000);
                } else {
                    System.out.println(System.currentTimeMillis() + " " + threadName + " is lock fail!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    System.out.println(threadName + " is unlock success!");
                    lock.unlock();
                }
            }
        }
    }

}
