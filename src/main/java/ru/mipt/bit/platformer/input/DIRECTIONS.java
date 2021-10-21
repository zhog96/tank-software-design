package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.util.Direction;

public enum DIRECTIONS {
    LEFT(new Direction(-1, 0)),
    RIGHT(new Direction(1, 0)),
    UP(new Direction(0, 1)),
    DOWN(new Direction(0, -1));

    DIRECTIONS(Direction direction) {
        this.direction = direction;
    }

    private final Direction direction;

    public Direction getDirection() {
        return direction;
    }
}
