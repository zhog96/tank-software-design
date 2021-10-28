package ru.mipt.bit.platformer.actors;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.input.Directions;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.Movement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AIAwesome implements Actor {
    // Интерфейс Actor может управлять и не одним объектом, как например тут
    private final AI ai;
    private final Level level;
    private final List<Orientation> orientations = Arrays.asList(Orientation.W, Orientation.N, Orientation.E, Orientation.S);

    public AIAwesome(AI ai, Level level) {
        this.ai = ai;
        this.level = level;
    }

    private static class tankBuildData {
        int x, y, destX, destY;
        Object source;
        Orientation orientation;

        private tankBuildData(int x, int y, int destX, int destY, Object source, Orientation orientation) {
            this.x = x;
            this.y = y;
            this.destX = destX;
            this.destY = destY;
            this.source = source;
            this.orientation = orientation;
        }
    }

    @Override
    public void act() {
        List<Obstacle> obstacles = new ArrayList<>();
        for (var obstacle : level.getObstacles()) {
            GridPoint2 pos = obstacle.getGridPosition();
            obstacles.add(new Obstacle(pos.x, pos.y));
        }
        List<Bot> bots = new ArrayList<>();
        for (var tank : level.getEnemyTanks()) {
            bots.add(botFromTank(tank));
        }
        Player player = playerFromTank(level.getPlayerTank());
        GameState.GameStateBuilder gameStateBuilder = getGameStateBuilder(obstacles, bots, player);
        List<Recommendation> recommendations = ai.recommend(gameStateBuilder.build());
        for (var recommendation : recommendations) {
            Tank tank = (Tank) recommendation.getActor().getSource();
            Action action = recommendation.getAction();
            Directions direction = null;
            if (action.equals(Action.MoveWest)) direction = Directions.RIGHT;
            if (action.equals(Action.MoveEast)) direction = Directions.LEFT;
            if (action.equals(Action.MoveNorth)) direction = Directions.UP;
            if (action.equals(Action.MoveSouth)) direction = Directions.DOWN;
            if (direction != null) tank.tryMove(direction.getDirection());
        }
    }

    private tankBuildData getDataFromTank(Tank tank) {
        if (tank == null) return null;
        try {
            Field field = Tank.class.getDeclaredField("movement");
            field.setAccessible(true);
            Movement movement = (Movement) field.get(tank);
            List<GridPoint2> points = movement.getTrajectoryPoints();
            return new tankBuildData(
                    points.get(0).x,
                    points.get(0).y,
                    points.get(1).x,
                    points.get(1).y,
                    tank,
                    orientations.get((((int) tank.getRotation() + 360) % 360) / 90)
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bot botFromTank(Tank tank) {
        tankBuildData tankBuildData = getDataFromTank(tank);
        if (tankBuildData == null) return null;
        Bot.BotBuilder botBuilder = new Bot.BotBuilder();
        botBuilder.source(tankBuildData.source);
        botBuilder.x(tankBuildData.x);
        botBuilder.y(tankBuildData.y);
        botBuilder.destX(tankBuildData.destX);
        botBuilder.destY(tankBuildData.destY);
        botBuilder.orientation(tankBuildData.orientation);
        return botBuilder.build();
    }

    private Player playerFromTank(Tank tank) {
        tankBuildData tankBuildData = getDataFromTank(tank);
        if (tankBuildData == null) return null;
        Player.PlayerBuilder playerBuilder = new Player.PlayerBuilder();
        playerBuilder.source(tankBuildData.source);
        playerBuilder.x(tankBuildData.x);
        playerBuilder.y(tankBuildData.y);
        playerBuilder.destX(tankBuildData.destX);
        playerBuilder.destY(tankBuildData.destY);
        playerBuilder.orientation(tankBuildData.orientation);
        return playerBuilder.build();
    }

    private GameState.GameStateBuilder getGameStateBuilder(List<Obstacle> obstacles, List<Bot> bots, Player player) {
        GameState.GameStateBuilder gameStateBuilder = new GameState.GameStateBuilder();
        gameStateBuilder.obstacles(obstacles);
        gameStateBuilder.bots(bots);
        gameStateBuilder.player(player);
        gameStateBuilder.levelWidth(Integer.MAX_VALUE);
        gameStateBuilder.levelHeight(Integer.MAX_VALUE);
        return gameStateBuilder;
    }
}
