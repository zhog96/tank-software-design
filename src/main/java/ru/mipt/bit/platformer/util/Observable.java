package ru.mipt.bit.platformer.util;

public interface Observable {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
}
