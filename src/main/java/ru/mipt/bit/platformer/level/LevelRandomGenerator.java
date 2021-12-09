package ru.mipt.bit.platformer.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Адаптер
 */

public class LevelRandomGenerator implements LevelGenerator {
    private final int levelWidth;
    private final int levelHeight;

    public LevelRandomGenerator(int levelWidth, int levelHeight) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    @Override
    public List<String> generate() {
        char[][] rawContent = new char[levelHeight][levelWidth];
        for (int i = 0; i < levelHeight; i++) {
            for (int j = 0; j < levelWidth; j++) {
                rawContent[i][j] = '_';
            }
        }
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            rawContent[random.nextInt(levelHeight)][random.nextInt(levelWidth)] = 'T';
        }
        for (int i = 0; i < 5; i++) {
            rawContent[random.nextInt(levelHeight - 2) + 1][random.nextInt(levelWidth - 2) + 1] = 'X';
        }
        rawContent[random.nextInt(levelHeight - 2) + 1][random.nextInt(levelWidth - 2) + 1] = 'P';
        for (int i = 0; i < levelWidth; i++) {
            rawContent[0][i] = 'T';
            rawContent[levelHeight - 1][i] = 'T';
        }
        for (int i = 0; i < levelHeight; i++) {
            rawContent[i][0] = 'T';
            rawContent[i][levelWidth - 1] = 'T';
        }
        List<String> content = new ArrayList<>();
        for (var line : rawContent) {
            content.add(new String(line));
        }
        return content;
    }
}
