package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.Observable;
import ru.mipt.bit.platformer.util.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Infrastructure
 */

public class Level implements Observable {
    private final List<Observer> observers;
    private final List<Obstacle> obstacles;
    private final List<Tank> tanks;
    private final List<Bullet> bullets;
    private final ColliderManager colliderManager;
    private final List<Bullet> bulletsToDelete;
    private Tank playerTank;

    public Level() {
        observers = new ArrayList<>();
        obstacles = new ArrayList<>();
        tanks = new ArrayList<>();
        bullets = new ArrayList<>();
        colliderManager = new ColliderManager();
        bulletsToDelete = new ArrayList<>();
    }

    public void update(float deltaTime) {
        bullets.forEach(bullet -> bullet.update(deltaTime));
        tanks.forEach(tank -> tank.update(deltaTime));
        bulletsToDelete.forEach(bullets::remove);
        bulletsToDelete.clear();
    }

    public void add(Obstacle obstacle) {
        obstacles.add(obstacle);
        colliderManager.addCollider(obstacle);
    }

    public void add(Tank tank) {
        tank.setColliderManager(colliderManager);
        tanks.add(tank);
        colliderManager.addCollider(tank);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public void setPlayerTank(Tank playerTank) {
        this.playerTank = playerTank;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        observers.forEach(observer -> observer.update("add", bullet));
    }

    public void removeBullet(Bullet bullet) {
        bulletsToDelete.add(bullet);
        observers.forEach(observer -> observer.update("remove", bullet));
    }

    public void removeTank(Tank tank) {
        tanks.remove(tank);
        colliderManager.removeCollider(tank);
        observers.forEach(observer -> observer.update("remove", tank));
    }

    public void update() {
        bulletsToDelete.forEach(bullets::remove);
        bulletsToDelete.clear();
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
        observers.forEach(obs -> obstacles.forEach(obj -> obs.update("add", obj)));
        observers.forEach(obs -> tanks.forEach(obj -> obs.update("add", obj)));
        observers.forEach(obs -> bullets.forEach(obj -> obs.update("add", obj)));
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }
}
