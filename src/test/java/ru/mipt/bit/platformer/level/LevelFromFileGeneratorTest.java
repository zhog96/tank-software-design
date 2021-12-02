package ru.mipt.bit.platformer.level;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LevelFromFileGeneratorTest {

    @ParameterizedTest
    @CsvSource({"'level1.txt', 'TX,T_,TTT'", "'level2.txt', 'X'", "'level3.txt', '_X'"})
    void generate(String fileName, String expected) {
        LevelGenerator levelGenerator = new LevelFromFileGenerator(String.format("src/test/resources/%s", fileName));
        assertEquals(expected, String.join(",", levelGenerator.generate()));
    }
}
