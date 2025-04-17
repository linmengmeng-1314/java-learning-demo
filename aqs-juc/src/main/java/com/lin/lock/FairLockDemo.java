package com.lin.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 5. ReentrantLock 公平锁与非公平锁
 * 大多数情况下锁的申请都是非公平锁，系统会在这个锁的等待线程中随机选择一个。
 * 当入参为true时 为公平锁，锁会按照先进先出的顺序获取锁。系统维护了一个有序队列，因此实现成本高，性能相对低下；
 * 非公平锁则反之，系统会直接获取锁，因此实现成本低，性能相对较高。
 * 默认情况下锁是非公平的，如果没有特殊的需求，也不需要使用公平锁；
 * @author linmengmeng
 * @since 2025/4/17 15:22
 */

public class FairLockDemo {

    public static void main(String[] args) {
        FairLockTask fairLockTask = new FairLockTask();
        new Thread(fairLockTask, "Thread-1").start();
        new Thread(fairLockTask, "Thread-2").start();
        new Thread(fairLockTask, "Thread-3").start();
        new Thread(fairLockTask, "Thread-4").start();
        new Thread(fairLockTask, "Thread-5").start();
        new Thread(fairLockTask, "Thread-6").start();
    }

    static class FairLockTask implements Runnable {

        // 公平锁 按获取顺序获取锁
//        ReentrantLock lock = new ReentrantLock(true);

        // 非公平锁 乱序获取锁
        ReentrantLock lock = new ReentrantLock(false);

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " is lock!");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }
}
