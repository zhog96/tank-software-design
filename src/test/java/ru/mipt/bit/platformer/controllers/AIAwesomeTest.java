package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.controllers.commands.ActionCommand;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.generator.LevelFromFileGenerator;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AIAwesomeTest {

    @Test
    void act() {
        TileUtils tileUtils = new TileUtils(new GridPoint2(10, 10));
        Level level = new LevelFromFileGenerator(
                "src/test/resources/level_ai_awesome.txt",
                tileUtils, 1.0f, 1.0f, 1, 1.0f
        ).generate();
        AIAwesome actor = new AIAwesome(
                gameState -> gameState.getBots().stream().map((var bot) -> new Recommendation(bot, Action.MoveEast)).collect(Collectors.toList()),
                level
        );
        actor.getCommands(1.0f).forEach(ActionCommand::act);
        List<Tank> enemyTanks = level.getTanks().stream().filter(tank -> tank != level.getPlayerTank()).collect(Collectors.toList());
        enemyTanks.forEach(tank -> tank.update(1));
        assertEquals(
                Arrays.asList(new GridPoint2(2, 0), new GridPoint2(1, 1), new GridPoint2(2, 2)),
                enemyTanks.stream().map((var tank) -> {
                    try {
                        Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
                        gridPosition.setAccessible(true);
                        return gridPosition.get(tank);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList())
        );
    }
}
