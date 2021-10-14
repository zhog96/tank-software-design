package ru.mipt.bit.platformer.level;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelRandomGeneratorTest {

    @ParameterizedTest
    @CsvSource({"1, 2", "1, 10", "100, 100"})
    void generateCheckGeneratedSize(int width, int height) {
        LevelGenerator levelGenerator = new LevelRandomGenerator(width, height);
        List<String> content = levelGenerator.generate();
        assertAll(content.stream().map((line) -> () -> assertEquals(width, line.length())));
        assertEquals(height, content.size());
    }
}
