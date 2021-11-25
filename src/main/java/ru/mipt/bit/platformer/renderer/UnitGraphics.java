package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.math.Rectangle;

public interface UnitGraphics extends Graphics {
    Rectangle getUnitBounding();
    float getHealth();
}
