package org.example.streaminoop;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class VoiceHandle {
    public List<String> process(String message, Consumer<String> receive) {
        var result = new ArrayList<String>();
        IntStream.range(0, 5).forEach(it -> {
            try {
                Thread.sleep(1000);
                var resp = String.format("Message: %s", it);
                /**
                 * мгновенная отправка сгенерированного сообщения поштучно
                 */
                receive.accept(resp);
                /**
                 * собираем сгенерированные сообщения для постобработки
                 */
                result.add(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}
