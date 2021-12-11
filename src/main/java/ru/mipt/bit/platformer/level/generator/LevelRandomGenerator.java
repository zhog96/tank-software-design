package ru.mipt.bit.platformer.level.generator;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.guns.SimpleGun;
import ru.mipt.bit.platformer.objects.tank.LightDamagedTank;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.util.Random;

/**
 * Адаптер
 */

public class LevelRandomGenerator implements LevelGenerator {
    private final int levelWidth;
    private final int levelHeight;
    private final TileUtils tileUtils;

    public LevelRandomGenerator(
            int levelWidth,
            int levelHeight,
            TileUtils tileUtils
    ) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.tileUtils = tileUtils;
    }

    @Override
    public Level generate() {
        Level level = new Level();
        char[][] rawContent = getRawContent();
        Random random = new Random();
        for (int i = 0; i < rawContent.length; i++) {
            for (int j = 0; j < rawContent[i].length; j++) {
                switch (rawContent[i][j]) {
                    case 'T':
                        level.add(new Obstacle(new GridPoint2(i, j), tileUtils));
                        break;
                    case 'X':
                    case 'P':
                        Tank tank = new Tank(
                                new GridPoint2(i, j),
                                random.nextFloat() * 0.2f + 0.3f,
                                tileUtils,
                                new LightDamagedTank()
                        );
                        tank.addGun(new SimpleGun(
                                random.nextFloat() * 0.9f + 0.1f,
                                random.nextInt(3) + 1,
                                random.nextFloat() * 0.2f + 0.05f,
                                level,
                                tank
                        ));

                        level.add(tank);
                        if (rawContent[i][j] == 'P') {
                            level.setPlayerTank(tank);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return level;
    }

    private char[][] getRawContent() {
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
        return rawContent;
    }
}
