package ru.mipt.bit.platformer.util;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static com.badlogic.gdx.math.MathUtils.clamp;

/**
 * Domain
 */

public class MovementProgress {
    private float progress;
    private final float speed;

    public MovementProgress(float speed) {
        progress = 0f;
        this.speed = speed;
    }

    public void reset() {
        progress = 0f;
    }

    public boolean finishedMoving() {
        return isEqual(progress, 1f);
    }

    public void update(float deltaTime) {
        progress = clamp(progress + deltaTime / speed, 0f, 1f);
    }

    public float getProgress() {
        return progress;
    }
}
