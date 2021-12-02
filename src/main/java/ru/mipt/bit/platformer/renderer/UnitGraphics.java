package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.math.Rectangle;

/**
 * Порт
 * UseCase
 */

public interface UnitGraphics extends Graphics {
    Rectangle getUnitBounding();
    float getHealth();
}
