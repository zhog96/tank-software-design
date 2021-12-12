package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.controllers.commands.ActionCommand;
import ru.mipt.bit.platformer.controllers.commands.MoveCommand;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.Movement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * По задумке тут должен быть и чуть позже появиться Адаптер
 */

public class AIAwesome implements Controller {
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
    public List<ActionCommand> getCommands(float deltaTime) {
        List<Obstacle> obstacles = new ArrayList<>();
        for (var obstacle : level.getObstacles()) {
            GridPoint2 pos = obstacle.getGridPosition();
            obstacles.add(new Obstacle(pos.x, pos.y));
        }
        List<Bot> bots = new ArrayList<>();
        Tank playerTank = level.getPlayerTank();
        List<Tank> enemyTanks = level.getTanks().stream().filter(tank -> tank != playerTank).collect(Collectors.toList());
        for (var tank : enemyTanks) {
            bots.add(botFromTank(tank));
        }
        Player player = playerFromTank(level.getPlayerTank());
        GameState.GameStateBuilder gameStateBuilder = getGameStateBuilder(obstacles, bots, player);
        List<Recommendation> recommendations = ai.recommend(gameStateBuilder.build());
        List<ActionCommand> commands = new ArrayList<>();
        for (var recommendation : recommendations) {
            Tank tank = (Tank) recommendation.getActor().getSource();
            Action action = recommendation.getAction();
            Directions direction = null;
            if (action.equals(Action.MoveWest)) direction = Directions.RIGHT;
            if (action.equals(Action.MoveEast)) direction = Directions.LEFT;
            if (action.equals(Action.MoveNorth)) direction = Directions.UP;
            if (action.equals(Action.MoveSouth)) direction = Directions.DOWN;
            if (direction != null) commands.add(new MoveCommand(tank, direction.getDirection()));
        }
        return commands;
    }

    private Obstacle getObstacleFromObstacle(ru.mipt.bit.platformer.objects.Obstacle obstacle) {
        GridPoint2 pos = obstacle.getGridPosition();
        return new Obstacle(pos.x, pos.y);
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
        return Bot.builder()
                .source(tankBuildData.source)
                .x(tankBuildData.x)
                .y(tankBuildData.y)
                .destX(tankBuildData.destX)
                .destY(tankBuildData.destY)
                .orientation(tankBuildData.orientation)
                .build();
    }

    private Player playerFromTank(Tank tank) {
        tankBuildData tankBuildData = getDataFromTank(tank);
        if (tankBuildData == null) return null;
        return Player.builder()
                .source(tankBuildData.source)
                .x(tankBuildData.x)
                .y(tankBuildData.y)
                .destX(tankBuildData.destX)
                .destY(tankBuildData.destY)
                .orientation(tankBuildData.orientation)
                .build();
    }

    private GameState.GameStateBuilder getGameStateBuilder(List<Obstacle> obstacles, List<Bot> bots, Player player) {
        return GameState.builder()
                .obstacles(obstacles)
                .bots(bots)
                .player(player)
                .levelWidth(Integer.MAX_VALUE)
                .levelHeight(Integer.MAX_VALUE);
                // Мир может быть разной формы, это же просто объекты в сетке, поэтому размеры мира не определены
    }
}
