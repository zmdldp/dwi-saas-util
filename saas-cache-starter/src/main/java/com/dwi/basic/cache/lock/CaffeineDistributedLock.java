package com.dwi.basic.cache.lock;

import com.dwi.basic.lock.DistributedLock;

/**
 * 分布式锁 只能用redis实现
 * 写这个类的目的，只是为了防止代码启动报错
 *
 * @author dwi
 * @date 2020/08/07
 */
public class CaffeineDistributedLock implements DistributedLock {
    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        return true;
    }

    @Override
    public boolean releaseLock(String key) {
        return true;
    }
}
