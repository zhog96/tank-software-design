package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final LevelGenerator levelGenerator;
    private final List<Obstacle> obstacles;
    private final List<Tank> enemyTanks;
    private Tank playerTank;

    public Level(LevelGenerator levelGenerator) {
        this.levelGenerator = levelGenerator;
        obstacles = new ArrayList<>();
        enemyTanks = new ArrayList<>();
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public void initObjects(ColliderManager colliderManager) {
        List<String> content = levelGenerator.generate();
        GridPoint2 position = new GridPoint2(0, 0);
        for (var line : content) {
            for (int idx = 0; idx < line.length(); idx++) {
                char elem = line.charAt(idx);
                float speed = 0.2f;
                switch (elem) {
                    case '_':
                        break;
                    case 'X':
                        enemyTanks.add(new Tank(position.cpy(), speed, colliderManager));
                        break;
                    case 'P':
                        playerTank = new Tank(position.cpy(), speed, colliderManager);
                        break;
                    case 'T':
                        obstacles.add(new Obstacle(position.cpy()));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown symbol in content of level");
                }
                position.x++;
            }
            position.x = 0;
            position.y++;
        }
    }
}
