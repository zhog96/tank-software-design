package ru.mipt.bit.platformer.objects;

public class Bullet {
    private final int damage;

    public Bullet(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
