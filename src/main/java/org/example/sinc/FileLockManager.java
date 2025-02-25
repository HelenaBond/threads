package org.example.sinc;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Один lock на запись и на чтение
 */
public class FileLockManager {
    /**
     * ReentrantReadWriteLock только для потоков внутри JVM
     */
    public final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
}
