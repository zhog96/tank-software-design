package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Player;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final LevelGenerator levelGenerator;
    private final ArrayList<Obstacle> obstacles;
    private Player player;

    public Level(LevelGenerator levelGenerator) {
        this.levelGenerator = levelGenerator;
        obstacles = new ArrayList<>();
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public Player getPlayer() {
        return player;
    }

    public void initObjects() {
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
                        player = new Player(position.cpy(), speed);
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
