package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class DirectionTest {

    @ParameterizedTest
    @CsvSource({"0, 0, 0.00", "0, 1, 90.00", "1, 0, 0.00", "0, -1, -90.00", "-1, 0, 180.00"})
    void getAngle(int x, int y, float angle) {
        Direction direction = new Direction(x, y);
        assertEquals(angle, direction.getAngle(), 0.01f);
    }

    @ParameterizedTest
    @CsvSource({"0, 0", "0, 1", "1, 0", "0, -1", "-1, 0"})
    void getDeltaCoordinate(int x, int y) {
        Direction direction = new Direction(x, y);
        assertThat(direction.getDeltaCoordinate()).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }
}
