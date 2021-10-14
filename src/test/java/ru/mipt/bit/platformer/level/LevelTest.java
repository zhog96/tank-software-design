package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.mipt.bit.platformer.util.TileUtils;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class LevelTest {
    @ParameterizedTest
    @CsvSource({"'X_,_T', 0, 0", "'X', 0, 0", "'_,_TX', 2, 1", "'_,_T,X', 0, 2", "'_T_T,,,,X', 0, 4"})
    void initObjectsCheckPlayerPosition(String contentString, int x, int y ) {
        TileUtils.setTileSize(new GridPoint2(1, 1));
        Level level = new Level(() -> Arrays.asList(contentString.split(",")));
        level.initObjects();
        assertThat(level.getPlayer().getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }

    @ParameterizedTest
    @CsvSource({"'X_,_T', 0, 1, 1", "'T,___TT,_X', 2, 4, 1", "'T', 0, 0, 0"})
    void initObjectsCheckObstaclePosition(String contentString, int obstacleNum, int x, int y ) {
        TileUtils.setTileSize(new GridPoint2(1, 1));
        Level level = new Level(() -> Arrays.asList(contentString.split(",")));
        level.initObjects();
        assertThat(level.getObstacles().get(obstacleNum).getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }
}
