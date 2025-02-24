package org.example.tread;

/**
 * Потому что создание объекта - не атомарная операция. Один поток может начать инициализацию
 * и создать создать ссылку при этом свойства объекта могут быть ещё не до конца проинициализированы
 * и второй поток может получить ссылку с не до конца собранным объектом.
 * При использовании volatile изменения в объекте станут видны другим потокам до того,
 * как они получат ссылку на него.
 */
public final class DCLSingleton {

    private static volatile DCLSingleton instance;

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    private DCLSingleton() {
    }

}
