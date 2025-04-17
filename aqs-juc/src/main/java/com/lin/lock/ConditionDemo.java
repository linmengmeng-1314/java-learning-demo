package com.lin.lock;

import com.lin.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/** 6. Condition 与 重入锁的搭配
 * Condition 是一个接口，它代表一个条件变量，它与 ReentrantLock 配合使用。可以在线程调用 await() 方法后，释放锁并等待直到其他线程调用 signal() 方法来唤醒。
 * @author linmengmeng
 * @since 2025/4/17 15:37
 */

public class ConditionDemo {

    public static ReentrantLock lock = new ReentrantLock();
    // 通过 lock 接口获得 Condition 实例对象
    public static Condition condition = lock.newCondition();

    public static void main(String[] args) throws Throwable{
        new Thread(() -> {
            try {
                lock.lock();
                print("子线程调用了 lock.lock() ，获得了锁; 调用 condition.await() 方法, 释放了锁，开始等待。。。");
                // 释放当前锁，进入等待中；等待其他线程调用 signal() 方法唤醒。调用 await() 方法前 必须获得锁。
                condition.await();
                print("子线程被唤醒，继续执行。。。");
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                lock.unlock();
                print("子线程调用了 lock.unlock() ，执行了解锁操作。");
            }
        }).start();
        Thread.sleep(3000);
        print("主线程睡了 3s ");
        lock.lock();
        print("主线程调用了 lock.lock() ，获得了锁。");
        condition.signal();
        print("主线程调用了 condition.signal() ，唤醒了子线程。");
        lock.unlock();
        print("主线程调用了 lock.unlock() ，执行了解锁操作。需要注意的是必须解锁，子线程才能继续往下走");
    }

    public static void print(String str) {
        System.out.println(DateUtils.getTime() + " " + Thread.currentThread().getName() + " " +str);
    }

}
