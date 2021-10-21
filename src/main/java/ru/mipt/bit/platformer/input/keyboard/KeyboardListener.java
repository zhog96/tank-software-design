package ru.mipt.bit.platformer.input.keyboard;

import ru.mipt.bit.platformer.input.DIRECTIONS;

public interface KeyboardListener {
    DIRECTIONS pressed();
}
