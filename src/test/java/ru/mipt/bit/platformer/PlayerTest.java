package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.input.KeyboardListener;
import ru.mipt.bit.platformer.util.TileUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class PlayerTest {

    @Test
    void tryMove() {
        TileUtils.setTileSize(new GridPoint2(10, 10));
        TileUtils.setInterpolation(Interpolation.smooth);
        ColliderManager.addObstacle(new Obstacle(new GridPoint2(2, 3)));
        ColliderManager.addObstacle(new Obstacle(new GridPoint2(3, 2)));
        KeyboardListener.INPUTS[] currInput = {KeyboardListener.INPUTS.LEFT};
        Player player = new Player(new GridPoint2(3, 3), 0.2f);
        player.setKeyboardListener(() -> currInput[0]);
        player.update(0.1f);
        player.update(0.1f);
        assertThat(player.getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(3, 3));
        assertThat(player.getPosition()).isEqualToComparingFieldByField(new Vector2(35, 35));
        assertEquals(180.0f, player.getRotation());
        currInput[0] = KeyboardListener.INPUTS.UP;
        player.update(0.1f);
        assertThat(player.getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(3, 4));
        assertThat(player.getPosition()).isEqualToComparingFieldByField(new Vector2(35, 40));
        assertEquals(90.0f, player.getRotation());
        player.update(0.1f);
        assertThat(player.getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(3, 4));
        assertThat(player.getPosition()).isEqualToComparingFieldByField(new Vector2(35, 45));
        assertEquals(90.0f, player.getRotation());
        currInput[0] = null;
        player.update(0.1f);
        assertThat(player.getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(3, 4));
        assertThat(player.getPosition()).isEqualToComparingFieldByField(new Vector2(35, 45));
        assertEquals(90.0f, player.getRotation());
        player.update(0.1f);
        assertThat(player.getGridPosition()).isEqualToComparingFieldByField(new GridPoint2(3, 4));
        assertThat(player.getPosition()).isEqualToComparingFieldByField(new Vector2(35, 45));
        assertEquals(90.0f, player.getRotation());
    }
}
