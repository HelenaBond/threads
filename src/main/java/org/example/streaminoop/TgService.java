package org.example.streaminoop;

import java.util.concurrent.CompletableFuture;

public class TgService extends LongPoll {
    private final VoiceHandle voiceHandle;

    public TgService(VoiceHandle voiceHandle) {
        this.voiceHandle = voiceHandle;
    }

    @Override
    void receive(String message) {
        var messages = voiceHandle.process(message, this::asyncSent);
    }

    public void asyncSent(String message) {
        /**
         * реализация мгновенной отправки
         */
        CompletableFuture.runAsync(() -> sent(message));
    }

    public static void main(String[] args) {
        new TgService(new VoiceHandle()).receive("Hello");
    }
}
