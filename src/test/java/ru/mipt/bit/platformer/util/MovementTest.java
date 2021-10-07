package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MovementTest {

    @ParameterizedTest
    @CsvSource({"0.0, 0.0", "0.1, 0.1", "1.0, 1.0", "10.0, 1.0", "100.0, 1.0"})
    void update(float deltaTime, float progress) throws NoSuchFieldException, IllegalAccessException {
        Movement movement = new Movement(new GridPoint2(), new GridPoint2(), 1.0f);
        movement.update(deltaTime);
        Field field = movement.getClass().getDeclaredField("movementProgress");
        field.setAccessible(true);
        MovementProgress movementProgress = (MovementProgress) field.get(movement);
        assertEquals(progress, movementProgress.getProgress(), 0.01);
    }

    @ParameterizedTest
    @CsvSource({"0.0, false", "0.1, false", "1.0, true", "10.0, true", "100.0, true"})
    void finishedMovingDifferentPositions(float deltaTime, boolean finishedMoving) {
        Movement movement = new Movement(new GridPoint2(0, 0), new GridPoint2(0, 1), 1.0f);
        movement.update(deltaTime);
        assertEquals(finishedMoving, movement.finishedMoving());
    }

    @ParameterizedTest
    @CsvSource({"0.0, true", "0.1, true", "1.0, true", "10.0, true", "100.0, true"})
    void finishedMovingEqualPositions(float deltaTime, boolean finishedMoving) {
        Movement movement = new Movement(new GridPoint2(0, 0), new GridPoint2(0, 0), 1.0f);
        movement.update(deltaTime);
        assertEquals(finishedMoving, movement.finishedMoving());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 1, 0.0, 5.0, 2.5",
            "0, 0, 0, 1, 1.0, 5.0, 7.5",
            "0, 0, 0, 1, 0.5, 5.0, 5.0",
            "1, 0, 0, 1, 0.8, 6.04, 6.98",
    })
    void calculatePosition(int x1, int y1, int x2, int y2, float deltaTime, float x, float y) {
        Movement movement = new Movement(new GridPoint2(x1, y1), new GridPoint2(x2, y2), 1.0f);
        movement.update(deltaTime);
        TileUtils.setTileSize(new GridPoint2(10, 5));
        TileUtils.setInterpolation(Interpolation.smooth);
        assertThat(movement.calculatePosition()).isEqualToComparingFieldByField(new Vector2(x, y));
    }
}
