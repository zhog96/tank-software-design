package ru.mipt.bit.platformer.actors;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.actors.commands.ActionCommand;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.objects.tank.LightDamagedTank;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @ParameterizedTest
    @CsvSource({"0, 0, 'UP', 0, 1", "10, 10, 'LEFT', 9, 10", "-10, 10, 'RIGHT', -9, 10", "5, 6, 'DOWN', 5, 5"})
    void act(int x0, int y0, String directionCode, int x1, int y1) throws NoSuchFieldException, IllegalAccessException {
        TileUtils tileUtils = new TileUtils(new GridPoint2(10, 10));
        ColliderManager colliderManager = new ColliderManager();
        Tank tank = new Tank(new GridPoint2(x0, y0), 0.1f, tileUtils, new LightDamagedTank());
        tank.setColliderManager(colliderManager);
        Directions[] currDirection = {Directions.valueOf(directionCode)};
        Player actor = new Player(tank, () -> currDirection[0], () -> false);
        actor.getCommands(1.0f).forEach(ActionCommand::act);
        Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
        gridPosition.setAccessible(true);
        assertEquals(gridPosition.get(tank), new GridPoint2(x1, y1));
    }
}
