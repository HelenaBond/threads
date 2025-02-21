package org.example.tread;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {

    @Parameter(names = {"-f", "--file"},
            description = "Название файла",
            required = true)
    private String name;

    @Parameter(names = {"-u", "--url"},
            description = "URL для скачивания",
            required = true)
    private String url;

    @Parameter(names = {"-s", "--speed"},
            description = "Ограничение скорости",
            required = true,
            validateWith = PositiveSpeedValidator.class)
    private int speed;

    @Override
    public void run() {
        URL currUrl = validUrl(url);
        File file = new File(name);
        try (var input = new BufferedInputStream(currUrl.openStream());
             var output = new BufferedOutputStream(new FileOutputStream(file))) {

            var dataBuffer = new byte[1024];
            int bytesRead;
            long startAt = System.currentTimeMillis();
            long downloadedBytes = 0;

            while (!Thread.currentThread().isInterrupted()
                    && (bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);

                downloadedBytes += bytesRead;

                if (downloadedBytes >= speed) {
                    long elapsed = System.currentTimeMillis() - startAt;
                    if (elapsed < 1000) {
                        try {
                            Thread.sleep(1000 - elapsed);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    downloadedBytes = 0;
                    startAt = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private URL validUrl(String value) {
        try {
            URL currUrl = new URL(value);
            if (!currUrl.getProtocol().matches("https?")) {
                throw new ParameterException("URL должен начинаться с http или https");
            }
            return currUrl;
        } catch (MalformedURLException e) {
            throw new ParameterException("Некорректный URL: " + value);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Wget wgetObj = new Wget();
        JCommander.newBuilder()
                .addObject(wgetObj)
                .build()
                .parse(args);
        Thread wget = new Thread(wgetObj);
        wget.start();
        wget.join();
    }
}
