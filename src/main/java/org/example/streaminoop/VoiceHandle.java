package org.example.streaminoop;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VoiceHandle {
    public Stream<String> process(String message) {
        return IntStream.range(0, 5)
                /**
                 * Если важен порядок обработки (0, 1, 2, 3, 4), то НЕ нужно делать параллельно.
                 * Если скорость важнее порядка, можно добавить .parallel(),
                 * чтобы все 5 сообщений обрабатывались одновременно.
                 */
                .parallel()
                .mapToObj(it -> {
                    try {
                        Thread.sleep(1000);
                        return String.format("Message: %s", it);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                });
    }
}
