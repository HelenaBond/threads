package org.example.sinc;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public final class FileParser {
    private final File file;
    private final FileLockManager lockManager;

    public FileParser(File file, FileLockManager lockManager) {
        this.file = file;
        this.lockManager = lockManager;
    }

    public String getContent() throws IOException {
        return getContent(ch -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(ch -> ch < 0x80);
    }

    private String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        /**
         * Блокируем только если есть активная запись
         */
        lockManager.lock.readLock().lock();
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            int data;
            char[] buffer = new char[1024];
            while ((data = reader.read(buffer)) != -1) {
                for (int i = 0; i < data; i++) {
                    if (filter.test(buffer[i])) {
                        output.append(buffer[i]);
                    }
                }
            }
        } finally {
            lockManager.lock.readLock().unlock();
        }
        return output.toString();
    }
}
