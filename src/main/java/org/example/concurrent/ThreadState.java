package org.example.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() ->
                System.out.println("First thread finished"));

        Thread second = new Thread(() ->
                System.out.println("Second thread finished"));

        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
        }
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
        }
        System.out.println("All threads finished!");
    }
}
