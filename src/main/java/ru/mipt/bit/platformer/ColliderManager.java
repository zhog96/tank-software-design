package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

public class ColliderManager {
    private static final ArrayList<Obstacle> obstacles = new ArrayList<>();

    public static void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public static boolean canMove(Player player, GridPoint2 move) {
        GridPoint2 estimatedPos = player.getGridPosition().cpy().add(move);
        for (var obstacle : obstacles) {
            if (obstacle.getGridPosition().equals(estimatedPos)) {
                return false;
            }
        }
        return true;
    }
}
