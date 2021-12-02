package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

public interface Collider {
    boolean collides(GridPoint2 targetGridPoint);
}
