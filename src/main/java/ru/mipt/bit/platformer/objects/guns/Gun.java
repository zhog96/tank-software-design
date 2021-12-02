package ru.mipt.bit.platformer.objects.guns;

public interface Gun {
    void update(float deltaTime);
    void tryShoot();
}
