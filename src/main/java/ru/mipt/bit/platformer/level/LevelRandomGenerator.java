package ru.mipt.bit.platformer.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        for (int i = 0; i < 30; i++) {
            rawContent[random.nextInt(levelHeight)][random.nextInt(levelWidth)] = 'T';
        }
        rawContent[random.nextInt(levelHeight)][random.nextInt(levelWidth)] = 'X';
        List<String> content = new ArrayList<>();
        for (var line : rawContent) {
            content.add(new String(line));
        }
        return content;
    }
}
