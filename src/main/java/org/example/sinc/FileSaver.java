package org.example.sinc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileSaver {
    private final File file;
    private final FileLockManager lockManager;

    public FileSaver(File file, FileLockManager lockManager) {
        this.file = file;
        this.lockManager = lockManager;
    }

    public void saveContent(String content) throws IOException {
        /**
         * Блокируем запись и чтение
         */
        lockManager.lock.writeLock().lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8, true))) {
            writer.write(content);
            writer.newLine();
        } finally {
            lockManager.lock.writeLock().unlock();
        }
    }
}
