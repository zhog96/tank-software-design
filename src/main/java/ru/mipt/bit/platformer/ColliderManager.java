package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Collider;

import java.util.ArrayList;
import java.util.List;

public class ColliderManager {
    private final List<Collider> colliders = new ArrayList<>();

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public boolean isFree(GridPoint2 targetGridPosition) {
        for (var collider : colliders) {
            if (collider.collides(targetGridPosition)) return false;
        }
        return true;
    }
}
