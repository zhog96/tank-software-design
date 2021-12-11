package ru.mipt.bit.platformer.level.generator;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.guns.SimpleGun;
import ru.mipt.bit.platformer.objects.tank.LightDamagedTank;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Адаптер
 * Вообще можно полностью конфигурировать танки и все прочее наример из джсона
 * но мы договорились о таком формате, так что передадим в конструкторе остальное
 */

public class LevelFromFileGenerator implements LevelGenerator {
    private List<String> content;
    private final TileUtils tileUtils;
    private final float tankSpeed;
    private final float bulletSpeed;
    private final int bulletDamage;
    private final float gunCoolDown;

    public LevelFromFileGenerator(
            String path,
            TileUtils tileUtils,
            float tankSpeed,
            float bulletSpeed,
            int bulletDamage,
            float gunCoolDown
    ) {
        this.tileUtils = tileUtils;
        try {
            content = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tankSpeed = tankSpeed;
        this.bulletSpeed = bulletSpeed;
        this.bulletDamage = bulletDamage;
        this.gunCoolDown = gunCoolDown;
    }

    @Override
    public Level generate() {
        Level level = new Level();
        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                switch (content.get(i).charAt(j)) {
                    case 'T':
                        level.add(new Obstacle(new GridPoint2(i, j), tileUtils));
                        break;
                    case 'X':
                    case 'P':
                        Tank tank = new Tank(
                                new GridPoint2(i, j),
                                tankSpeed,
                                tileUtils,
                                new LightDamagedTank()
                        );
                        tank.addGun(new SimpleGun(
                                gunCoolDown,
                                bulletDamage,
                                bulletSpeed,
                                level,
                                tank
                        ));
                        level.add(tank);
                        level.setPlayerTank(tank);
                        break;
                    default:
                        break;
                }
            }
        }
        return level;
    }
}
