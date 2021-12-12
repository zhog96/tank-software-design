package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.mipt.bit.platformer.level.generator.LevelFromFileGenerator;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LevelFromFileGeneratorTest {
    @ParameterizedTest
    @CsvSource({"'P_,_T.txt', 0, 0", "'P.txt', 0, 0", "'_,_TP.txt', 2, 1", "'_,_T,P.txt', 0, 2", "'_T_T,,,,P.txt', 0, 4"})
    void initObjectsCheckPlayerPosition(String sourceName, int x, int y ) throws NoSuchFieldException, IllegalAccessException {
        sourceName = "src/test/resources/level/" + sourceName;
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        Level level = new LevelFromFileGenerator(sourceName, tileUtils, 1.0f, 1.0f, 1, 1.0f).generate();
        Tank tank = level.getPlayerTank();
        Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
        gridPosition.setAccessible(true);
        assertThat(gridPosition.get(tank)).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }

    @ParameterizedTest
    @CsvSource({"'P_,_T.txt', 0, 1, 1", "'T,___TT,XP.txt', 2, 4, 1", "'T.txt', 0, 0, 0"})
    void initObjectsCheckObstaclePosition(String sourceName, int obstacleNum, int x, int y ) {
        sourceName = "src/test/resources/level/" + sourceName;
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        Level level = new LevelFromFileGenerator(sourceName, tileUtils, 1.0f, 1.0f, 1, 1.0f).generate();
        assertThat(level.getObstacles().get(obstacleNum).getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }

    @ParameterizedTest
    @CsvSource({"'P_,_X.txt', 0, 1, 1", "'X,___XX,TP.txt', 2, 4, 1", "'X.txt', 0, 0, 0"})
    void initObjectsCheckEnemyTankPosition(String sourceName, int enemyTankNum, int x, int y ) throws NoSuchFieldException, IllegalAccessException {
        sourceName = "src/test/resources/level/" + sourceName;
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        Level level = new LevelFromFileGenerator(sourceName, tileUtils, 1.0f, 1.0f, 1, 1.0f).generate();
        Tank tank = level.getTanks().stream().filter(t -> t != level.getPlayerTank()).collect(Collectors.toList()).get(enemyTankNum);
        Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
        gridPosition.setAccessible(true);
        assertThat(gridPosition.get(tank)).isEqualToComparingFieldByField(new GridPoint2(x, y));
    }
}
