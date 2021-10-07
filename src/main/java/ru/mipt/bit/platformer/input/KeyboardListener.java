package ru.mipt.bit.platformer.input;

public interface KeyboardListener {
    enum INPUTS {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    INPUTS pressed();
}
