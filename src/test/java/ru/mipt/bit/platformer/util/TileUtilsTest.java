package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class TileUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "10, 5, 0, 0, 5.0, 2.5",
            "8, 10, 1, 2, 12.0, 25.0",
            "3, 5, -10, 100, -28.5, 502.5"
    })
    void calculateTileCenter(int tileWidth, int tileHeight, int x, int y, float cx, float cy) {
        TileUtils tileUtils = new TileUtils(new GridPoint2(tileWidth, tileHeight));
        assertThat(tileUtils.calculateTileCenter(new GridPoint2(x, y)))
            .isEqualToComparingFieldByField(new Vector2(cx, cy));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 1, 0.0, 5.0, 2.5",
            "0, 0, 0, 1, 1.0, 5.0, 7.5",
            "0, 0, 0, 1, 0.5, 5.0, 5.0",
            "1, 0, 0, 1, 0.8, 6.04, 6.98",
    })
    void calculatePositionBetween(int x1, int y1, int x2, int y2, float progress, float x, float y) {
        TileUtils tileUtils = new TileUtils(new GridPoint2(10, 5));
        assertThat(tileUtils.calculatePositionBetween(new GridPoint2(x1, y1), new GridPoint2(x2, y2), progress)).isEqualToComparingFieldByField(new Vector2(x, y));
    }
}
