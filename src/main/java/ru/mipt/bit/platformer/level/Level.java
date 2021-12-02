package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.guns.SimpleGun;
import ru.mipt.bit.platformer.objects.tank.Light;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.Observable;
import ru.mipt.bit.platformer.util.Observer;
import ru.mipt.bit.platformer.util.TileUtils;

import java.util.ArrayList;
import java.util.List;

public class Level implements Observable {
    private final LevelGenerator levelGenerator;
    private final List<Obstacle> obstacles;
    private final List<Tank> enemyTanks;
    private final List<Bullet> bullets;
    private Tank playerTank;
    private final List<Observer> observers;
    private final ColliderManager colliderManager;
    private List<Bullet> bulletsToDelete;

    public Level(LevelGenerator levelGenerator) {
        this.levelGenerator = levelGenerator;
        obstacles = new ArrayList<>();
        enemyTanks = new ArrayList<>();
        bullets = new ArrayList<>();
        observers = new ArrayList<>();
        colliderManager = new ColliderManager();
        bulletsToDelete = new ArrayList<>();
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public void update(float deltaTime) {
        for (var bullet : bullets) {
            bullet.update(deltaTime);
        }
        for (var tank : enemyTanks) {
            tank.update(deltaTime);
        }
        if (playerTank != null) {
            playerTank.update(deltaTime);
        }
        bulletsToDelete.forEach(bullets::remove);
        bulletsToDelete = new ArrayList<>();
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
        observers.forEach(observer -> observer.update("remove", tank));
        if (tank == playerTank) {
            playerTank = null;
        } else {
            enemyTanks.remove(tank);
        }
        colliderManager.removeCollider(tank);
    }

    public void initObjects(TileUtils tileUtils) {
        List<String> content = levelGenerator.generate();
        GridPoint2 position = new GridPoint2(0, 0);
        for (var line : content) {
            for (int idx = 0; idx < line.length(); idx++) {
                char elem = line.charAt(idx);
                float speed = 0.2f;
                float coolDown = 0.5f;
                float bulletSpeed = 1.0f;
                int damage = 1;
                switch (elem) {
                    case '_':
                        break;
                    case 'X':
                        enemyTanks.add(new Tank(position.cpy(), speed, colliderManager, tileUtils, new Light()));
                        enemyTanks.forEach(tank -> tank.addGun(new SimpleGun(coolDown, damage, bulletSpeed, this, tank)));
                        observers.forEach(observer -> enemyTanks.forEach(enemyTank -> observer.update("add", enemyTank)));
                        break;
                    case 'P':
                        playerTank = new Tank(position.cpy(), speed, colliderManager, tileUtils, new Light());
                        playerTank.addGun(new SimpleGun(coolDown, damage, bulletSpeed, this, playerTank));
                        observers.forEach(observer -> observer.update("add", playerTank));
                        break;
                    case 'T':
                        obstacles.add(new Obstacle(position.cpy(), tileUtils));
                        observers.forEach(observer -> obstacles.forEach(obstacle -> observer.update("add", obstacle)));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown symbol in content of level");
                }
                position.x++;
            }
            position.x = 0;
            position.y++;
        }
    }

    public void initColliders() {
        if (playerTank != null) {
            colliderManager.addCollider(playerTank);
        }
        for (var tank : enemyTanks) {
            colliderManager.addCollider(tank);
        }
        for (var obstacle : obstacles) {
            colliderManager.addCollider(obstacle);
        }
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }
}
