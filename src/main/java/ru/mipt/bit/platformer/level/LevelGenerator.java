package ru.mipt.bit.platformer.level;

import java.util.List;

/**
 * Порт
 */

public interface LevelGenerator {
    List<String> generate();
}
