package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelRandomGenerator;
import ru.mipt.bit.platformer.util.TileUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelRandomGeneratorTest {

    @ParameterizedTest
    @CsvSource({"5, 5", "10, 10", "10, 20"})
    void generateCheckGeneratedSize(int width, int height) {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        LevelGenerator levelGenerator = new LevelRandomGenerator(width, height, tileUtils);
        Level level = levelGenerator.generate();
        assertAll(
                () -> assertNotNull(level.getObstacles()),
                () -> assertNotNull(level.getTanks()),
                () -> assertNotNull(level.getPlayerTank())

        );
    }
}
