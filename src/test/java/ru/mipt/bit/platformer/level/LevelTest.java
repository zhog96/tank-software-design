package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class LevelTest {
    @ParameterizedTest
    @CsvSource({"'P_,_T', 0, 0", "'P', 0, 0", "'_,_TP', 2, 1", "'_,_T,P', 0, 2", "'_T_T,,,,P', 0, 4"})
    void initObjectsCheckPlayerPosition(String contentString, int x, int y ) throws NoSuchFieldException, IllegalAccessException {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        Level level = new Level(() -> Arrays.asList(contentString.split(",")));
        level.initObjects(tileUtils);
        Tank tank = level.getPlayerTank();
        Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
        gridPosition.setAccessible(true);
        assertThat(gridPosition.get(tank)).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }

    @ParameterizedTest
    @CsvSource({"'P_,_T', 0, 1, 1", "'T,___TT,XP', 2, 4, 1", "'T', 0, 0, 0"})
    void initObjectsCheckObstaclePosition(String contentString, int obstacleNum, int x, int y ) {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        Level level = new Level(() -> Arrays.asList(contentString.split(",")));
        level.initObjects(tileUtils);
        assertThat(level.getObstacles().get(obstacleNum).getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }

    @ParameterizedTest
    @CsvSource({"'P_,_X', 0, 1, 1", "'X,___XX,TP', 2, 4, 1", "'X', 0, 0, 0"})
    void initObjectsCheckEnemyTankPosition(String contentString, int enemyTankNum, int x, int y ) throws NoSuchFieldException, IllegalAccessException {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        Level level = new Level(() -> Arrays.asList(contentString.split(",")));
        level.initObjects(tileUtils);
        Tank tank = level.getEnemyTanks().get(enemyTankNum);
        Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
        gridPosition.setAccessible(true);
        assertThat(gridPosition.get(tank)).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }
}
