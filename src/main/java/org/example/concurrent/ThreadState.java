package org.example.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() ->
                System.out.println("Thread finished " + Thread.currentThread().getName()));

        Thread second = new Thread(() ->
                System.out.println("Thread finished " + Thread.currentThread().getName()));

        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
            || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState() + " " + first.getName());
            System.out.println(first.getState() + " " + second.getName());
        }
        System.out.println("All threads finished!");
    }
}
