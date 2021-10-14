package ru.mipt.bit.platformer.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MovementProgressTest {

    @ParameterizedTest
    @ValueSource(floats = {0.0f, 0.1f, 100.0f})
    void reset(float deltaTime) {
        MovementProgress movementProgress = new MovementProgress(1.0f);
        movementProgress.update(deltaTime);
        movementProgress.reset();
        assertEquals(0.0f, movementProgress.getProgress());
    }

    @ParameterizedTest
    @CsvSource({"0.0, false", "0.1, false", "1.0, true", "10.0, true", "100.0, true"})
    void finishedMoving(float deltaTime, boolean finishedMoving) {
        MovementProgress movementProgress = new MovementProgress(1.0f);
        movementProgress.update(deltaTime);
        assertEquals(finishedMoving, movementProgress.finishedMoving());
    }

    @ParameterizedTest
    @CsvSource({"0.0, 0.0", "0.1, 0.1", "1.0, 1.0", "10.0, 1.0", "100.0, 1.0"})
    void update(float deltaTime, float progress) {
        MovementProgress movementProgress = new MovementProgress(1.0f);
        movementProgress.update(deltaTime);
        assertEquals(progress, movementProgress.getProgress(), 0.01);
    }
}
